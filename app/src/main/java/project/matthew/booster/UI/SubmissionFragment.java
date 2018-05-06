package project.matthew.booster.UI;

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
import io.realm.Realm;
import io.realm.RealmResults;
import project.matthew.booster.R;
import project.matthew.booster.UI.Helper.Constants;
import project.matthew.booster.UI.Helper.Emailer;
import project.matthew.booster.UI.Helper.FormHelper;
import project.matthew.booster.UI.Interfaces.SubmissionInterface;
import project.matthew.booster.UI.Models.Answer;
import project.matthew.booster.UI.Models.Question;

/**
 * Created by Matthew on 29/04/2018.
 */

public class SubmissionFragment extends Fragment implements SubmissionInterface {

    private View rootView;
    String name, email, phone;
    private ProgressDialog pd;

    private String host, port, username, password, toAddress, subject;

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

    private void hideProgressDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }

    private void sendEmail() {
        host = "smtp.gmail.com";
        port = "587";
        username = "matthewboostertest@gmail.com";
        password = "matthiasb";
        toAddress = email;
        subject = username + " Booster Investment Questionnaire";

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
        }
    }


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

    @Override
    public void showSuccessDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
                Toast.makeText(getContext(), "Successfully emailed results to: " + toAddress, Toast.LENGTH_SHORT).show();
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

    @Override
    public void clearResultsAndStartAgain() {
        clearQuestionnaireCompleteFlag();
        clearQuestionsAndAnswers();
        startAppAgain();
    }

    @Override
    public void clearQuestionnaireCompleteFlag() {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove(Constants.QUESTIONNAIRE_COMPLETE).commit();
    }

    @Override
    public void clearQuestionsAndAnswers() {
        // Delete all questions and answers from realm.
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questions = realm.where(Question.class).findAll();
        RealmResults<Answer> answers = realm.where(Answer.class).findAll();
        realm.beginTransaction();
        questions.deleteAllFromRealm();
        realm.commitTransaction();
        realm.beginTransaction();
        answers.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void startAppAgain() {
        // Start the main activity again.
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
