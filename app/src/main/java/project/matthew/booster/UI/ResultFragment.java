package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.matthew.booster.R;

/**
 * Created by Matthew on 3/05/2018.
 */

public class ResultFragment extends Fragment{

    private View rootView;
    private MainActivity mainActivity;

    @BindView(R.id.total_score)
    TextView totalScore;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_fragment, container, false);
        ButterKnife.bind(this, rootView);

        mainActivity = ((MainActivity) getActivity());

        totalScore.setText(mainActivity.getScore());

        

        return rootView;
    }



}
