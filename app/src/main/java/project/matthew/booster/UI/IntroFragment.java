package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.matthew.booster.R;

/**
 * Created by Matthew on 29/04/2018.
 */

public class IntroFragment extends Fragment {

    private View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = getLayoutInflater().inflate(R.layout.intro_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
