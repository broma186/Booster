package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transitions.everywhere.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.matthew.booster.R;
import project.matthew.booster.UI.Adapters.NavigationDrawerListAdapter;
import project.matthew.booster.UI.Interfaces.RadioButtonStorageInterface;
import project.matthew.booster.UI.Interfaces.ToolbarSetupInterface;
import project.matthew.booster.UI.Interfaces.NavigationSetupInterface;

import static android.view.View.GONE;

/**
 * Created by Matthew on 27/04/2018.
 */

public class MainActivity extends AppCompatActivity implements ToolbarSetupInterface, NavigationSetupInterface, RadioButtonStorageInterface {
    private static final String TAG = "MainActivity";

    @BindView(R.id.nav_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_info_text)
    TextView mainInfoText;



    private Toolbar mToolbar;

    private NavigationDrawerListAdapter mNavDrawerAdapter;
    private ListView mNavigationDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private int mSelectedNavDrawerPosition;
    private Object mCurrentFragment;

    private ArrayList<RadioGroup> answerOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

       // initToolbar();
        hideToolbarTitle();
        setupNavigationDrawer();
        //showIntroFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.menu_title))) {
            if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.openDrawer(Gravity.START);
            } else {
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        }
        return true;
    }

    @Override
    public void initToolbar() {
       // setSupportActionBar(mToolbar);
    }

    @Override
    public void hideToolbarTitle() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void setupNavigationDrawer() {
        mNavDrawerAdapter = new NavigationDrawerListAdapter(this);
        mNavigationDrawerList = (ListView) findViewById(R.id.nav_drawer_list);
        mNavigationDrawerList.setAdapter(mNavDrawerAdapter);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                null, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mNavigationDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TransitionManager.beginDelayedTransition((ViewGroup) adapterView.getRootView());
                mainInfoText.setVisibility(GONE); // Hide the main app info text.
                TextView navItemTextView = (TextView)view.findViewById(R.id.nav_title);
                if (navItemTextView != null) {
                    String navItemTitle = (String) navItemTextView.getText();
                    switch (navItemTitle) {
                        case "Investor Types":
                           // Do nothing
                            break;
                        case "Questionnaire":
                            showFragment(position, new QuestionnaireFragment(), navItemTitle);
                            break;
                        case "Submit":
                            showFragment(position, new SubmissionFragment(), navItemTitle);
                            break;
                            default:
                                showFragment(position, new InvestorTypeFragment(), navItemTitle);
                    }
                }
            }
            private void showFragment(int position, Object fragment, String fragmentTag) {
                mSelectedNavDrawerPosition = position;
                mDrawerLayout.closeDrawer(GravityCompat.START);

                // Remove the current fragment
                if (mCurrentFragment != null) {
                    if (mCurrentFragment instanceof Fragment) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .remove((Fragment) mCurrentFragment)
                                .commitAllowingStateLoss();
                    }
                }
                mNavDrawerAdapter.setSelectedItem(position); // Update new Fragment position.

                // Replace the new Fragment.
                if (!MainActivity.this.isDestroyed()) {
                    if (fragment instanceof Fragment) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, (Fragment) fragment, fragmentTag)
                                .commitAllowingStateLoss();
                    }
                    mCurrentFragment = fragment;
                }
            }
        });
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }


    @Override
    public void setAnswerOptions(ArrayList<RadioGroup> answerOptions) {
        this.answerOptions = answerOptions;

        for (RadioGroup answerOption : answerOptions) {
            //TODO: Iterate through all answers. for each that is selected, get the radio button id using the answer id (same)
            //TODO: and mark that the current radiogroup has a selected item.
        }
    }

    @Override
    public ArrayList<RadioGroup> getAnswerOptions() {
        return answerOptions;
    }
}
