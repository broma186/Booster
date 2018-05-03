package project.matthew.booster.UI;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
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
    private ArrayList<RadioGroup> answerSelections;

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

        answerSelections = new ArrayList<RadioGroup>();

        mFont = Typeface.createFromAsset(getActivity().getAssets(),"circular_book.ttf");


        Realm realm = Realm.getDefaultInstance();
        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();
        questions = realm.copyFromRealm(questionsFromRealm);

        if (questions != null & questions.size() > 0) {
            for (final Question question : questions) {
                TextView questionView = new TextView(getActivity());
                questionView.setText(question.getTitle());
                LinearLayout.LayoutParams vglp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                vglp.setMargins(10, 20, 10, 20);
                questionView.setLayoutParams(vglp);
                questionView.setTypeface(mFont);
                questionView.setTextSize(18);
                questionView.setTextColor(getResources().getColor(R.color.white));
                questionView.setGravity(Gravity.CENTER_HORIZONTAL);
                questionnaireLayout.addView(questionView);

                RealmResults<Answer> answersFromRealm = realm.where(Answer.class).findAll();
                final List<Answer> answers = realm.copyFromRealm(answersFromRealm);

                if (answers != null && answers.size() > 0) {
                    final RadioGroup answerGroup = new RadioGroup(getActivity());
                    answerGroup.setId(question.getId());
                    LinearLayout.LayoutParams aglp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    aglp.setMargins(10, 20, 10, 20);
                    answerGroup.setLayoutParams(aglp);
                    answerGroup.setGravity(Gravity.LEFT);

                    answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                            Realm realm = Realm.getDefaultInstance();
                            for (Answer answer : answers) {
                                if (answer.getId() == checkedId) {
                                    Log.d("MainActivity", "onCheckedChanged: updating answer with id: " + answer.getId());
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
                            realm.beginTransaction();
                            Log.d("MainActivity", "onCheckedChanged: updating question with id: " + question.getId());
                            question.setAnswered(new Boolean(true));
                            realm.insertOrUpdate(question);
                            realm.commitTransaction();

                            realm.close();
                            Log.d("MainActivity", "onCreateView: check done on radio select");
                            ((MainActivity) getActivity()).checkDone();
                        }
                    });
                    for (final Answer answer : answers) {
                        final RadioButton answerOption = new RadioButton(getActivity());
                        answerOption.setId(answer.getId());
                        answerOption.setText(answer.getName());
                        answerOption.setTypeface(mFont);
                        questionView.setTextSize(15);
                        LinearLayout.LayoutParams rblp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        rblp.setMargins(20, 10, 20, 10);
                        answerOption.setLayoutParams(rblp);
                        answerOption.setTextColor(getResources().getColor(R.color.white));
                        answerOption.setContentDescription(String.valueOf(answer.getValue()));
                        answerOption.setChecked(answer.isSelected());

                        answerGroup.addView(answerOption);
                    }
                    questionnaireLayout.addView(answerGroup);
                    answerSelections.add(answerGroup);
                }
            }
            Log.d("MainActivity", "onCreateView: check done on layout");
            ((MainActivity) getActivity()).checkDone();
        }
        return rootView;
    }

}
