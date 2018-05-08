package project.matthew.booster.UI;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import project.matthew.booster.R;
import project.matthew.booster.UI.Helper.RealmHelper;
import project.matthew.booster.UI.Interfaces.QuestionnaireLoadInterface;
import project.matthew.booster.UI.Models.Answer;
import project.matthew.booster.UI.Models.Question;

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