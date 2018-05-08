package project.matthew.booster.Helper;

import android.content.res.Resources;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import project.matthew.booster.R;
import project.matthew.booster.Models.Answer;
import project.matthew.booster.Models.Question;

/**
 * Created by Matthew on 8/05/2018.
 */

public class RealmHelper {

    /**
     * Creates all the answers and questions needed for this applicatino.
     * @param res The resources object used to pull question/answer strings from.
     */
    public static void initQuestionsAndAnswers(Resources res) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();

        if (questionsFromRealm == null || questionsFromRealm.size() == 0) {
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
        }
    }

    /**
     * Create an answer object using the given id, value and name.
     * @param id The id of the Answer to write to Realm.
     * @param value The value to use when adding up the questionnaire scores.
     * @param name The actual answer String.
     */
    private static void createAnswer(int id, int value, String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Answer answer = realm.createObject(Answer.class, id);
        answer.setName(name);
        answer.setValue(value);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Adds new question to realm with answers extracted using an ID randge.
     *
     * @param id    The id of the question to write to Realm
     * @param title What the question says.
     * @param start Answers need to be create with ids starting with this inclusive...
     * @param end   And ending with this inclusive
     */
    private static void createQuestion(int id, String title, int start, int end) {
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

    /**
     * Deletes all questions and answers.
     */
    public static void clearAll() {
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
}
