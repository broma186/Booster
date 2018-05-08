package project.matthew.booster;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import project.matthew.booster.Helper.RealmHelper;
import project.matthew.booster.Interfaces.QuestionnaireLoadInterface;

public class WelcomeActivity extends AppCompatActivity implements QuestionnaireLoadInterface {

    /**
     * Begin app flow.
     * @param view
     */
    @OnClick(R.id.welcome_button)
    public void openApp(View view) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        loadQuestionsAndAnswers();

    }

    /**
     * Initially load questions and answers before using app.
     */
    @Override
    public void loadQuestionsAndAnswers() {
        Resources res = getResources();
        RealmHelper.initQuestionsAndAnswers(res);
    }

}