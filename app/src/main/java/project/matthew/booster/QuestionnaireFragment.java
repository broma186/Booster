package project.matthew.booster;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import project.matthew.booster.Interfaces.QuestionnaireLayoutInterface;
import project.matthew.booster.Models.Answer;
import project.matthew.booster.Models.Question;

/**
 * Created by Matthew on 30/04/2018.
 */

public class QuestionnaireFragment extends Fragment implements QuestionnaireLayoutInterface{

    private View rootView;
    private Typeface mFont;
    List<Question> questions;

    @BindView(R.id.questionnaire_layout)
    LinearLayout questionnaireLayout;

    @BindView(R.id.results_button)
    TextView resultsButton;

    @OnClick(R.id.results_button)
    public void goToResultScreen(View view) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        // Replace with questionnaire fragment.
        ResultFragment resFrag = new ResultFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, (Fragment) resFrag, "RESULT")
                .commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.questionnaire_fragment, container, false);
        ButterKnife.bind(this, rootView);

        mFont = Typeface.createFromAsset(getActivity().getAssets(),"circular_book.ttf");

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();
        questions = realm.copyFromRealm(questionsFromRealm);

        if (questions != null & questions.size() > 0) {
            for (final Question question : questions) {

                // Layout created for margin purposes.
                LinearLayout outerQuestion = getOuterQuestionLayout();
                questionnaireLayout.addView(outerQuestion);

                // TextView holding question.
                TextView questionView = getQuestionView(question);
                outerQuestion.addView(questionView);

                if (question.getAnswers() != null && question.getAnswers().size() > 0) {
                    // Layout created for margin purposes.
                    LinearLayout outerGroup = getOuterAnswerLayout();
                    questionnaireLayout.addView(outerGroup);

                    // Create an answer group for each question with 5 answers each.
                    final RadioGroup answerGroup = getAnswerGroup(question);
                    for (final Answer answer : question.getAnswers()) {
                        final RadioButton answerOption = getAnswerOption(answer);
                        answerGroup.addView(answerOption); // Add each radio button 'answer' to it's respective radio group.
                    }
                    outerGroup.addView(answerGroup); // Add radio button group to outer yellow layout.
                }
            }
            setResultsVisibleIfDone(); // Every time UI loads, check if questionnaire complete.
        }
        return rootView;
    }

    /**
     * Get outer layout for margin purposes. This layout is the outer layout of each question.
     * @return The linear layout that the respective question textview will be added to.
     */
    @Override
    public LinearLayout getOuterQuestionLayout() {
        LinearLayout outerQuestion = new LinearLayout(getActivity());
        LinearLayout.LayoutParams outerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        outerQuestion.setLayoutParams(outerParams);
        outerQuestion.setBackgroundColor(getResources().getColor(R.color.colorBooster));
        return  outerQuestion;
    }

    /**
     * Prepares the textview that holds the question for each question in the list.
     * @param question The question to create the textview for.
     * @return The final textView object.
     */
    @Override
    public TextView getQuestionView(Question question) {
        TextView questionView = new TextView(getActivity());
        questionView.setBackgroundColor(getResources().getColor(R.color.colorBooster));
        questionView.setText(question.getTitle());
        LinearLayout.LayoutParams vglp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vglp.setMargins(42, 42, 42, 42);
        questionView.setLayoutParams(vglp);
        questionView.setTypeface(mFont);
        questionView.setTextSize(20);
        questionView.setTextColor(getResources().getColor(R.color.white));
        questionView.setGravity(Gravity.CENTER_HORIZONTAL);
        return questionView;
    }

    /**
     * Gets the answer layout for the Answer radiogroup. Used to margin layout purposes.
     * @return The layout.
     */
    @Override
    public LinearLayout getOuterAnswerLayout() {
        LinearLayout outerGroup = new LinearLayout(getActivity());
        LinearLayout.LayoutParams outerGroupParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        outerGroup.setLayoutParams(outerGroupParams);
        outerGroup.setBackgroundColor(getResources().getColor(R.color.boosterGreen));
        return outerGroup;
    }

    /**
     * Create a radiogroup for a group of 5 answers for each respective question.
     * @param question The question the answer group is created for.
     * @return The group of radio buttons to hold the answer options.
     */
    @Override
    public RadioGroup getAnswerGroup(final Question question) {
        final RadioGroup answerGroup = new RadioGroup(getActivity());
        answerGroup.setId(question.getId());
        LinearLayout.LayoutParams aglp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        aglp.setMargins(42, 42, 42, 42);
        answerGroup.setLayoutParams(aglp);
        answerGroup.setGravity(Gravity.LEFT);
        answerGroup.setBackgroundColor(getResources().getColor(R.color.boosterGreen));
        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                // Every time a radio button is selected, update it to selected in realm,
                // otherwise undo selection.
                Realm realm = Realm.getDefaultInstance();
                for (Answer answer : question.getAnswers()) {
                    if (answer.getId() == checkedId) {
                        realm.beginTransaction();
                        answer.setSelected(true);
                        realm.insertOrUpdate(answer);
                        realm.commitTransaction();
                    } else {
                        realm.beginTransaction();
                        answer.setSelected(false);
                        realm.insertOrUpdate(answer);
                        realm.commitTransaction();
                    }
                }
                // Set the question an answered when there is a value entered.
                realm.beginTransaction();
                question.setAnswered(new Boolean(true));
                realm.insertOrUpdate(question);
                realm.commitTransaction();
                realm.close();

                setResultsVisibleIfDone(); // Check if questionnaire complete every radio button select change.
            }
        });
        return answerGroup;
    }

    /**
     * Get a radio button that represents an answer to one of the questions.
     * @param answer The answer the radiobutton is being created for.
     * @return The button option.
     */
    @Override
    public RadioButton getAnswerOption(Answer answer) {
        final RadioButton answerOption = new RadioButton(getActivity());
        answerOption.setId(answer.getId());
        answerOption.setText(answer.getName());
        answerOption.setTypeface(mFont);
        LinearLayout.LayoutParams rblp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rblp.setMargins(42, 100, 42, 100);
        answerOption.setLayoutParams(rblp);
        answerOption.setTextColor(getResources().getColor(R.color.colorBooster));
        answerOption.setContentDescription(String.valueOf(answer.getValue()));
        answerOption.setChecked(answer.isSelected());
        return answerOption;
    }

    /**
     * If the questionnaire is done, show the results button.
     */
    @Override
    public void setResultsVisibleIfDone() {
        if (((MainActivity) getActivity()).checkDone() && resultsButton.getVisibility() != View.VISIBLE) {
            resultsButton.setVisibility(View.VISIBLE);
        }
    }
}
