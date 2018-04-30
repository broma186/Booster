package project.matthew.booster.UI;

import android.content.Intent;
import android.content.res.Resources;
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
        Resources res = getResources();
        Answer[] questionOneAnswers = {new Answer(1, 1, res.getString(R.string.question_one_answer_one)),
                new Answer(2, 3, res.getString(R.string.question_one_answer_two)),
                new Answer(3, 5, res.getString(R.string.question_one_answer_three)),
                new Answer(4, 7, res.getString(R.string.question_one_answer_four)),
                new Answer(5, 10, res.getString(R.string.question_one_answer_five))};

       Question questionOne = new Question(1, res.getString(R.string.question_one), questionOneAnswers);

        Answer[] questionTwoAnswers = {new Answer(6, 1, res.getString(R.string.question_two_answer_one)),
                new Answer(7, 3, res.getString(R.string.question_two_answer_two)),
                new Answer(8, 5, res.getString(R.string.question_two_answer_three)),
                new Answer(9, 7, res.getString(R.string.question_two_answer_four)),
                new Answer(10, 10, res.getString(R.string.question_two_answer_five))};

        Question questionTwo = new Question(2, res.getString(R.string.question_two), questionTwoAnswers);


        Answer[] questionThreeAnswers = {new Answer(11, 1, res.getString(R.string.question_three_answer_one)),
                new Answer(12, 3, res.getString(R.string.question_three_answer_two)),
                new Answer(13, 5, res.getString(R.string.question_three_answer_three)),
                new Answer(14, 7, res.getString(R.string.question_three_answer_four)),
                new Answer(15, 10, res.getString(R.string.question_three_answer_five))};

        Question questionThree = new Question(3, res.getString(R.string.question_three), questionThreeAnswers);

        Answer[] questionFourAnswers = {new Answer(16, 1, res.getString(R.string.question_four_answer_one)),
                new Answer(17, 3, res.getString(R.string.question_four_answer_two)),
                new Answer(18, 5, res.getString(R.string.question_four_answer_three)),
                new Answer(19, 7, res.getString(R.string.question_four_answer_four)),
                new Answer(20, 10, res.getString(R.string.question_four_answer_five))};

        Question questionFour = new Question(4, res.getString(R.string.question_four), questionFourAnswers);

        Answer[] questionFiveAnswers = {new Answer(21, 1, res.getString(R.string.question_five_answer_one)),
                new Answer(22, 3, res.getString(R.string.question_five_answer_two)),
                new Answer(23, 5, res.getString(R.string.question_five_answer_three)),
                new Answer(24, 7, res.getString(R.string.question_five_answer_four)),
                new Answer(25, 10, res.getString(R.string.question_five_answer_five))};

        Question questionFive = new Question(5, res.getString(R.string.question_five), questionFiveAnswers);



    }
}
