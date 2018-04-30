package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.matthew.booster.R;
import project.matthew.booster.UI.Interfaces.InvestorTypeInterface;

/**
 * Created by Matthew on 29/04/2018.
 */

public class InvestorTypeFragment extends Fragment implements InvestorTypeInterface{

    private View rootView;
    private String mInvestorType;

    @BindView(R.id.investor_type_pie_graph)
    ImageView investorTypePieGraphView;

    @BindView(R.id.investor_type_frag_title)
    TextView investorTypeFragTitle;

    @BindView(R.id.investor_type_description)
    TextView investorTypeDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.investor_type_fragment, container, false);
        ButterKnife.bind(this, rootView);
        mInvestorType = getTag();

        investorTypeFragTitle.setText(mInvestorType);

        setPieGraphAndText();

        return rootView;
    }

    @Override
    public void setPieGraphAndText() {
        switch(mInvestorType) {
            case "Defensive":
                investorTypePieGraphView.setImageResource(R.drawable.capital_guaranteed_fund_pie_graph);
                investorTypeDescription.setText(getResources().getString(R.string.capital_guarantee_fund));
                break;
            case "Conservative":
                investorTypePieGraphView.setImageResource(R.drawable.default_saver_fund_pie_graph);
                investorTypeDescription.setText(getResources().getString(R.string.default_saver_fund));
                break;
            case "Balanced":
                investorTypePieGraphView.setImageResource(R.drawable.balanced_fund_pie_graph);
                investorTypeDescription.setText(getResources().getString(R.string.balanced_fund));
                break;
            case "Balanced Growth":
                investorTypePieGraphView.setImageResource(R.drawable.balanced_growth_fund_pie_graph);
                investorTypeDescription.setText(getResources().getString(R.string.balanced_growth_fund));
                break;
            case "Growth":
                investorTypePieGraphView.setImageResource(R.drawable.high_growth_fund_pie_graph);
                investorTypeDescription.setText(getResources().getString(R.string.high_growth_fund));
                break;
            case "Aggressive Growth":
                investorTypePieGraphView.setImageResource(R.drawable.high_growth_fund_pie_graph);
                investorTypeDescription.setText(getResources().getString(R.string.high_growth_fund));
                break;
        }
    }
}



