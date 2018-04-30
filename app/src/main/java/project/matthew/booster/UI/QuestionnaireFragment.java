package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import project.matthew.booster.R;
import project.matthew.booster.UI.Interfaces.QuestionnaireLoadInterface;

/**
 * Created by Matthew on 30/04/2018.
 */

public class QuestionnaireFragment extends Fragment {

    private View rootView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.questionnaire_fragment, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

}
