package project.matthew.booster.UI.Interfaces;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/05/2018.
 */

public interface QuestionnaireCompletionInterface {
    public boolean checkDone();
    public void setScore(int score);
    public int getScore();
}
