package project.matthew.booster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.MessagingException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import project.matthew.booster.Helper.Constants;
import project.matthew.booster.Helper.Emailer;
import project.matthew.booster.Helper.FormHelper;
import project.matthew.booster.Helper.RealmHelper;
import project.matthew.booster.Interfaces.SubmissionInterface;

/**
 * Created by Matthew on 29/04/2018.
 */

public class SubmissionFragment extends Fragment implements SubmissionInterface {

    private View rootView;
    String name, email, phone;
    private ProgressDialog pd;

    private String host, port, username, password, toAddress, subject;

    /**
     * Send the results to an email address on new thread if form valid.
     * @param view The submit button.
     */
    @OnClick(R.id.submit_button)
    public void validateAndSubmitQuestionnaire(View view) {
        String validMessage = isFormValid();
        if (validMessage == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendEmail();
                }
            }).start();
        } else {
            Toast.makeText(getContext(), validMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.submission_fragment, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    /**
     * Show progress dialog on the main thread.
     */
    private void showProgressDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Sending Questionnaire Results");
                pd.setCanceledOnTouchOutside(false);
                pd.setCancelable(false);
                pd.show();
            }
        });
    }

    /**
     * Dismiss progress dialog on the main thread.
     */
    private void hideProgressDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    /**
     * Gets needed parameters and sends the results to the email addres specified in the form by the
     * user.
     */
    private void sendEmail() {
        host = getString(R.string.host);
        port = getString(R.string.port);
        username = getString(R.string.username);
        password = getString(R.string.password);
        toAddress = email;
        subject = getString(R.string.subject);

        String message = "Results: " + ((MainActivity) getActivity()).getScore() + "\nName: " + name +
                "\nEmail: " + email + "\nPhone: " + phone;

        boolean success = true;
        showProgressDialog();
        try {
            Emailer.sendEmailWithAttachments(host, port, username, password, toAddress, subject,
                    message, null);
        } catch (MessagingException e) {
            hideProgressDialog();
            success = false;
            e.printStackTrace();
        }
        if (success) {
            showSuccessDialog();
        } else {
            Toast.makeText(getContext(), "Failed to send results", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Validate the submission form, enforcing correct email regex.
     * @return String. A possible error message is something wrong with form input.
     */
    @Override
    public String isFormValid() {
        name = ((EditText) rootView.findViewById(R.id.name)).getText().toString();
        email = ((EditText) rootView.findViewById(R.id.email)).getText().toString();
        phone = ((EditText) rootView.findViewById(R.id.phone)).getText().toString();

        if (name.length() == 0) {
            return getResources().getString(R.string.error_no_name);
        }
        if (email.length() == 0 || !FormHelper.isValidEmail(email)) {
            return getResources().getString(R.string.error_invalid_email);
        }
        if (phone.length() == 0) {
            return getResources().getString(R.string.error_no_phone);
        }
        return null;
    }

    /**
     * Displays a dialog indicating email send success, on the main thread.
     */
    @Override
    public void showSuccessDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
                AlertDialog.Builder bl = new AlertDialog.Builder(getActivity());
                bl.setTitle("Results Sent");
                bl.setMessage("Successfully emailed results to: " + toAddress);
                bl.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearResultsAndStartAgain();
                    }
                });
                bl.setNegativeButton("Ok", null);
                bl.create().show();
            }
        });
    }

    /**
     * Calls methods to lower questionnaire completion flag, delete all Q/A from Realm and restart
     * the app using an intent.
     */
    @Override
    public void clearResultsAndStartAgain() {
        clearQuestionnaireCompleteFlag();
        clearQuestionsAndAnswers();
        startAppAgain();
    }

    /**
     * Removes the questionnaire completion flag from default SharedPreferences.
     */
    @Override
    public void clearQuestionnaireCompleteFlag() {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove(Constants.QUESTIONNAIRE_COMPLETE).commit();
    }

    /**
     * Deletes all questions and answers stored in Realm.
     */
    @Override
    public void clearQuestionsAndAnswers() {
        // Delete all questions and answers from realm.
        RealmHelper.clearAll();
    }

    /**
     * Starts the MainActivity again using the clear top flag.
     */
    @Override
    public void startAppAgain() {
        // Start the main activity again.
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
