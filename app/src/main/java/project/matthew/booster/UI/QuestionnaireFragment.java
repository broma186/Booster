package project.matthew.booster.UI;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import io.realm.Realm;
import io.realm.RealmResults;
import project.matthew.booster.R;
import project.matthew.booster.UI.Interfaces.QuestionnaireLoadInterface;
import project.matthew.booster.UI.Models.Answer;
import project.matthew.booster.UI.Models.Question;

/**
 * Created by Matthew on 30/04/2018.
 */

public class QuestionnaireFragment extends Fragment {

    private View rootView;

    @BindView(R.id.questionnaire_layout)
    LinearLayout questionnaireLayout;

    private Typeface mFont;

    List<Question> questions;
    private static final String TAG = "QuestionnaireFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.questionnaire_fragment, container, false);
        ButterKnife.bind(this, rootView);


        mFont = Typeface.createFromAsset(getActivity().getAssets(),"circular_book.ttf");


        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();
        questions = realm.copyFromRealm(questionsFromRealm);
        Log.d(TAG, "onCreateView: size of questions: " + questions.size());

        if (questions != null & questions.size() > 0) {
            for (Question question : questions) {
                TextView questionView = new TextView(getActivity());
                questionView.setText(question.getTitle());
                questionView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                questionView.setTypeface(mFont);
                questionView.setGravity(Gravity.CENTER_HORIZONTAL);
                questionnaireLayout.addView(questionView);

                if (question.getAnswers() != null && question.getAnswers().size() > 0) {
                    RadioGroup answerGroup = new RadioGroup(getActivity());
                    answerGroup.setId(question.getId());
                    answerGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    answerGroup.setGravity(Gravity.CENTER_HORIZONTAL);
                    for (Answer answer : question.getAnswers()) {
                        RadioButton answerOption = new RadioButton(getActivity());
                        answerOption.setId(answer.getId());
                        answerOption.setText(answer.getName());
                        questionView.setTypeface(mFont);
                        answerOption.setContentDescription(String.valueOf(answer.getValue()));
                        answerGroup.addView(answerOption);
                    }
                }


            }
        }
        return rootView;
    }

}
