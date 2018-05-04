package project.matthew.booster.UI;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.matthew.booster.R;
import project.matthew.booster.UI.Helper.Constants;
import project.matthew.booster.UI.Interfaces.ShowScoreInterface;

/**
 * Created by Matthew on 3/05/2018.
 */

public class ResultFragment extends Fragment implements ShowScoreInterface {

    private View rootView;
    private MainActivity mainActivity;
    private int navPosForInvestorTypeShow;
    private String investorTypeTitle;
    private FragmentManager mFragmentManager;

    @BindView(R.id.total_score)
    TextView totalScore;

    @BindView(R.id.show_button)
    TextView showButton;

    @BindView(R.id.investment_result)
    TextView investmentResult;


    @OnClick(R.id.show_button)
    public void goToInvestorType(View view) {
        mFragmentManager.beginTransaction()
                .remove((Fragment) this)
                .commitAllowingStateLoss();

        ((MainActivity) getActivity()).showFragment(navPosForInvestorTypeShow, new InvestorTypeFragment(), investorTypeTitle);
    }

    private int score;

    private static final String TAG = "ResultFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_fragment, container, false);

        ButterKnife.bind(this, rootView);

        mainActivity = ((MainActivity) getActivity());
        mFragmentManager = mainActivity.getSupportFragmentManager();

        showScore();

        return rootView;
    }

    @Override
    public void showScore() {
        Resources res = mainActivity.getResources();
        score = mainActivity.getScore(); // Get score from main activity.
        totalScore.setText(String.valueOf(score)); // Display score value.
        if (score >= 5 && score <= 12) {
            Log.d(TAG, "showScore: sel defensive");
            investmentResult.setText(res.getString(R.string.result_defensive));
            investorTypeTitle = res.getString(R.string.defensive_type_title);
            navPosForInvestorTypeShow = Constants.DEFENSIVE_TYPE_POS;
        } else if (score >= 13 && score <= 20) {
            Log.d(TAG, "showScore: sel conservative");
            investmentResult.setText(res.getString(R.string.result_conservative));
            investorTypeTitle = res.getString(R.string.conservative_type_title);
            navPosForInvestorTypeShow = Constants.CONSERVATIVE_TYPE_POS;
        } else if (score >= 21 && score <= 29) {
            Log.d(TAG, "showScore: sel balanced");
            investmentResult.setText(res.getString(R.string.result_balanced));
            investorTypeTitle = res.getString(R.string.balanced_type_title);
            navPosForInvestorTypeShow = Constants.BALANCED_TYPE_POS;
        } else if (score >= 30 && score <= 37) {
            Log.d(TAG, "showScore: sel bal_growth");
            investmentResult.setText(res.getString(R.string.result_balanced_growth));
            investorTypeTitle = res.getString(R.string.balanced_growth_title);
            navPosForInvestorTypeShow = Constants.BALANCED_GROWTH_TYPE_POS;
        } else if (score >= 38 && score <= 44) {
            Log.d(TAG, "showScore: sel growth");
            investmentResult.setText(res.getString(R.string.result_growth));
            investorTypeTitle = res.getString(R.string.growth_title);
            navPosForInvestorTypeShow = Constants.GROWTH_TYPE_POS;
        } else if (score >= 45 && score <= 50) {
            Log.d(TAG, "showScore: sel aggress growth");
            investmentResult.setText(res.getString(R.string.result_aggressive_growth));
            investorTypeTitle = res.getString(R.string.aggressive_growth_title);
            navPosForInvestorTypeShow = Constants.AGGRESSIVE_GROWTH_TYPE_POS;
        }
    }
}

/*
  <string name="defensive_type_title">Defensive</string>
    <string name="conservative_type_title">Conservative</string>
    <string name="balanced_type_title">Balanced</string>
    <string name="balanced_growth_title">Balanced Growth</string>
    <string name="growth_title">Growth</string>
    <string name="aggressive_growth_title">Aggressive Growth</string>

 */
