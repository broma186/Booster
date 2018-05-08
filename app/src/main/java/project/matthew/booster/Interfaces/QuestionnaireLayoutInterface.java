package project.matthew.booster.Interfaces;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import project.matthew.booster.Models.Answer;
import project.matthew.booster.Models.Question;

/**
 * Created by Matthew on 8/05/2018.
 */

public interface QuestionnaireLayoutInterface {

    public LinearLayout getOuterQuestionLayout();
    public LinearLayout getOuterAnswerLayout();
    public RadioGroup getAnswerGroup(final Question question);
    public RadioButton getAnswerOption(Answer answer);
    public TextView getQuestionView(Question question);
    public void setResultsVisibleIfDone();
}
