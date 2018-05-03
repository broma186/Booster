package project.matthew.booster.UI;

import android.content.res.Resources;
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
import project.matthew.booster.UI.Interfaces.ShowScoreInterface;

/**
 * Created by Matthew on 3/05/2018.
 */

public class ResultFragment extends Fragment implements ShowScoreInterface {

    private View rootView;
    private MainActivity mainActivity;

    @BindView(R.id.total_score)
    TextView totalScore;

    @BindView(R.id.investment_result)
    TextView investmentResult;

    private int score;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_fragment, container, false);
        ButterKnife.bind(this, rootView);

        mainActivity = ((MainActivity) getActivity());

        totalScore.setText(mainActivity.getScore());
        score = mainActivity.getScore();



        return rootView;
    }

    @Override
    public void showScore() {
        Resources res = getActivity().getResources();
        if (score >= 5 && score <= 12) {
            investmentResult.setText(res.getString(R.string.result_defensive));
        } else if (score >= 13 && score <= 20) {
            investmentResult.setText(res.getString(R.string.result_conservative));
        } else if (score >= 21 && score <= 29) {
            investmentResult.setText(res.getString(R.string.result_balanced));
        } else if (score >= 30 && score <= 37) {
            investmentResult.setText(res.getString(R.string.result_balanced_growth));
        } else if (score >= 38 && score <= 44) {
            investmentResult.setText(res.getString(R.string.result_growth));
        } else if (score >= 45 && score <= 50) {
            investmentResult.setText(res.getString(R.string.result_aggressive_growth));
        }
    }
}
