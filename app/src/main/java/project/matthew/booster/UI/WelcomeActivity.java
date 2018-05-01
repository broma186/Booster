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

        loadQuestionsAndAnswers();

    }

    @Override
    public void loadQuestionsAndAnswers() {
        Resources res = getResources();



        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();

        if (questionsFromRealm == null || questionsFromRealm.size() == 0) {

            Answer[] questionOneAnswers = {new Answer(1, 1, res.getString(R.string.question_one_answer_one)),
                    new Answer(2, 3, res.getString(R.string.question_one_answer_two)),
                    new Answer(3, 5, res.getString(R.string.question_one_answer_three)),
                    new Answer(4, 7, res.getString(R.string.question_one_answer_four)),
                    new Answer(5, 10, res.getString(R.string.question_one_answer_five))};

            RealmList<Answer> q1Answers = new RealmList<Answer>();
            for (Answer answer : questionOneAnswers) {
                q1Answers.add(answer);
            }
            Question questionOne = new Question(1, res.getString(R.string.question_one), q1Answers);

            Answer[] questionTwoAnswers = {new Answer(6, 1, res.getString(R.string.question_two_answer_one)),
                    new Answer(7, 3, res.getString(R.string.question_two_answer_two)),
                    new Answer(8, 5, res.getString(R.string.question_two_answer_three)),
                    new Answer(9, 7, res.getString(R.string.question_two_answer_four)),
                    new Answer(10, 10, res.getString(R.string.question_two_answer_five))};

            RealmList<Answer> q2Answers = new RealmList<Answer>();
            for (Answer answer : questionTwoAnswers) {
                q2Answers.add(answer);
            }
            Question questionTwo = new Question(2, res.getString(R.string.question_two), q2Answers);


            Answer[] questionThreeAnswers = {new Answer(11, 1, res.getString(R.string.question_three_answer_one)),
                    new Answer(12, 3, res.getString(R.string.question_three_answer_two)),
                    new Answer(13, 5, res.getString(R.string.question_three_answer_three)),
                    new Answer(14, 7, res.getString(R.string.question_three_answer_four)),
                    new Answer(15, 10, res.getString(R.string.question_three_answer_five))};

            RealmList<Answer> q3Answers = new RealmList<Answer>();
            for (Answer answer : questionThreeAnswers) {
                q3Answers.add(answer);
            }
            Question questionThree = new Question(3, res.getString(R.string.question_three), q3Answers);

            Answer[] questionFourAnswers = {new Answer(16, 1, res.getString(R.string.question_four_answer_one)),
                    new Answer(17, 3, res.getString(R.string.question_four_answer_two)),
                    new Answer(18, 5, res.getString(R.string.question_four_answer_three)),
                    new Answer(19, 7, res.getString(R.string.question_four_answer_four)),
                    new Answer(20, 10, res.getString(R.string.question_four_answer_five))};

            RealmList<Answer> q4Answers = new RealmList<Answer>();
            for (Answer answer : questionFourAnswers) {
                q4Answers.add(answer);
            }
            Question questionFour = new Question(4, res.getString(R.string.question_four), q4Answers);

            Answer[] questionFiveAnswers = {new Answer(21, 1, res.getString(R.string.question_five_answer_one)),
                    new Answer(22, 3, res.getString(R.string.question_five_answer_two)),
                    new Answer(23, 5, res.getString(R.string.question_five_answer_three)),
                    new Answer(24, 7, res.getString(R.string.question_five_answer_four)),
                    new Answer(25, 10, res.getString(R.string.question_five_answer_five))};

            RealmList<Answer> q5Answers = new RealmList<Answer>();
            for (Answer answer : questionFiveAnswers) {
                q5Answers.add(answer);
            }
            Question questionFive = new Question(5, res.getString(R.string.question_five), q5Answers);

            Question[] questionsArr = {questionOne, questionTwo, questionThree, questionFour, questionFive};
            for (Question question : questionsArr) {
                copyToRealm(question);
            }
        }
    }

    private static final String TAG = "WelcomeActivity";
    private void copyToRealm(Question question) {
        Realm realm = Realm.getDefaultInstance();
        if (!question.getAnswers().isManaged()) {
            realm.beginTransaction();
            realm.copyToRealm(question.getAnswers());
            realm.commitTransaction();
        }
        realm.beginTransaction();
        Question q1 = realm.createObject(Question.class, question.getId());
        q1.setTitle(question.getTitle());
        q1.setAnswers(question.getAnswers());
        realm.commitTransaction();
        realm.close();

    }
}
