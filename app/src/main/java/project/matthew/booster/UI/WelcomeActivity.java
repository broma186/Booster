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

    private void createAnswer(int id, int value, String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Answer answer = realm.createObject(Answer.class, id);
        answer.setName(name);
        answer.setValue(value);
        realm.commitTransaction();
        realm.close();
    }

    private void createQuestion(int id, String title, int start, int end) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Answer> answersFromRealm = realm.where(Answer.class).findAll();

        RealmList<Answer> answersToAddToQuestion = new RealmList<Answer>();

        for (Answer answer : answersFromRealm) {
            if (answer.getId() >= start && answer.getId() <= end) {
                answersToAddToQuestion.add(answer);
            }
        }
        realm.beginTransaction();
        Question question = realm.createObject(Question.class, id);
        question.setTitle(title);
        question.setAnswers(answersToAddToQuestion);
        realm.commitTransaction();
        realm.close();
    }

    private static final String TAG = "WelcomeActivity";
    @Override
    public void loadQuestionsAndAnswers() {
        Resources res = getResources();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();

        if (questionsFromRealm == null || questionsFromRealm.size() == 0) {
            Log.d(TAG, "loadQuestionsAndAnswers: no questions");
            createAnswer(1, 1, res.getString(R.string.question_one_answer_one));
            createAnswer(2, 3, res.getString(R.string.question_one_answer_two));
            createAnswer(3, 5, res.getString(R.string.question_one_answer_three));
            createAnswer(4, 7, res.getString(R.string.question_one_answer_four));
            createAnswer(5, 10, res.getString(R.string.question_one_answer_five));

            createAnswer(6, 1, res.getString(R.string.question_two_answer_one));
            createAnswer(7, 3, res.getString(R.string.question_two_answer_two));
            createAnswer(8, 5, res.getString(R.string.question_two_answer_three));
            createAnswer(9, 7, res.getString(R.string.question_two_answer_four));
            createAnswer(10, 10, res.getString(R.string.question_two_answer_five));

            createAnswer(11, 1, res.getString(R.string.question_three_answer_one));
            createAnswer(12, 3, res.getString(R.string.question_three_answer_two));
            createAnswer(13, 5, res.getString(R.string.question_three_answer_three));
            createAnswer(14, 7, res.getString(R.string.question_three_answer_four));
            createAnswer(15, 10, res.getString(R.string.question_three_answer_five));

            createAnswer(16, 1, res.getString(R.string.question_four_answer_one));
            createAnswer(17, 3, res.getString(R.string.question_four_answer_two));
            createAnswer(18, 5, res.getString(R.string.question_four_answer_three));
            createAnswer(19, 7, res.getString(R.string.question_four_answer_four));
            createAnswer(20, 10, res.getString(R.string.question_four_answer_five));

            createAnswer(21, 1, res.getString(R.string.question_five_answer_one));
            createAnswer(22, 3, res.getString(R.string.question_five_answer_two));
            createAnswer(23, 5, res.getString(R.string.question_five_answer_three));
            createAnswer(24, 7, res.getString(R.string.question_five_answer_four));
            createAnswer(25, 10, res.getString(R.string.question_five_answer_five));

            createQuestion(1, res.getString(R.string.question_one), 1, 5);
            createQuestion(2, res.getString(R.string.question_two), 6, 10);
            createQuestion(3, res.getString(R.string.question_three), 11, 15);
            createQuestion(4, res.getString(R.string.question_four), 16, 20);
            createQuestion(5, res.getString(R.string.question_five), 21, 25);
        } else {
            Log.d(TAG, "loadQuestionsAndAnswers: already questions");
        }
    }

}