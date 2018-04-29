package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    @BindView(R.id.investor_type_description)
    TextView investorTypeDescription;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.investor_type_fragment, container, false);
        ButterKnife.bind(rootView);
        mInvestorType = getTag();

        setPieGraphAndText();

        return rootView;
    }

    @Override
    public void setPieGraphAndText() {
        switch(mInvestorType) {
            case "Defensive":
                // TODO: Assign appropriately.
               // investorTypePieGraphView.setImageResource(R.drawable.booster_logo);
               // investorTypeDescription.setText();
                break;
            case "Conservative":
                break;
            case "Balanced":
                break;
            case "Balanced Growth":
                break;
            case "Growth":
                break;
            case "Aggressive Growth":
                break;
        }
    }
}


