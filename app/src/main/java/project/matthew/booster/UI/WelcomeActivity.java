package project.matthew.booster.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import project.matthew.booster.R;
import project.matthew.booster.UI.Interfaces.QuestionnaireLoadInterface;
import project.matthew.booster.UI.Models.Answer;
import project.matthew.booster.UI.Models.Question;

public class WelcomeActivity extends AppCompatActivity implements QuestionnaireLoadInterface {

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

    }

    @Override
    public void loadQuestionsAndAnswers() {
        Answer questionOneAnswerOne = new Answer(1, 1, )
        Question question = new Question(1, getResources().getString(R.string.question_one))
    }
}
