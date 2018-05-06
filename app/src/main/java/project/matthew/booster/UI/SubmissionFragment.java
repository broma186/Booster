package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.MessagingException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import project.matthew.booster.R;
import project.matthew.booster.UI.Helper.Emailer;
import project.matthew.booster.UI.Helper.FormHelper;
import project.matthew.booster.UI.Interfaces.SubmissionValidationInterface;
import rx.observers.TestObserver;

/**
 * Created by Matthew on 29/04/2018.
 */

public class SubmissionFragment extends Fragment implements SubmissionValidationInterface{

    private View rootView;
    String name, email, phone;

    private static final String TAG = "SubmissionFragment";

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
            Log.d(TAG, "validateAndSubmitQuestionnaire: not valid");
            Toast.makeText(getContext(), validMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        Log.d(TAG, "sendEmail: ");
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "matthewboostertest@gmail.com";
        String password = "matthiasb";
        final String toAddress = "mattbr@hotmail.co.nz";
        String subject = "Booster Investment Questionnaire";
        Log.d(TAG, "sendEmail: score is in submission: " + ((MainActivity) getActivity()).getScore());

        String message = "Results: " + ((MainActivity) getActivity()).getScore() + "\nName: " + name +
                "\nEmail:" + email + "\nPhone: " + phone;
        boolean success = true;
        try {
            Emailer.sendEmailWithAttachments(host, port, username, password, toAddress, subject,
                    message, null);
        } catch (MessagingException e) {
            success = false;
            Log.d(TAG, "sendEmail: sending message failed");
            e.printStackTrace();
        }
        if (success) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "Successfully emailed results to: " + toAddress, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.submission_fragment, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public String isFormValid() {
        name = ((EditText)rootView.findViewById(R.id.name)).getText().toString();
        email = ((EditText)rootView.findViewById(R.id.email)).getText().toString();
        phone = ((EditText)rootView.findViewById(R.id.phone)).getText().toString();

        if (name.length() == 0){
            return getResources().getString(R.string.error_no_name);
        }
        if(email.length() == 0 || !FormHelper.isValidEmail(email)){
            return getResources().getString(R.string.error_invalid_email);
        }
        if (phone.length() == 0){
            return getResources().getString(R.string.error_no_phone);
        }
        return null;
    }
}
