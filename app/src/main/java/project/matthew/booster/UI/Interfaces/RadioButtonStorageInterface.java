package project.matthew.booster.UI.Interfaces;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/05/2018.
 */

public interface RadioButtonStorageInterface {
    public void setAnswerOptions(ArrayList<RadioGroup> answerOptions);
    public ArrayList<RadioGroup> getAnswerOptions();
}
