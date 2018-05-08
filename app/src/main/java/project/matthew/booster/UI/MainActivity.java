package project.matthew.booster.UI;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.transitions.everywhere.TransitionManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import project.matthew.booster.R;
import project.matthew.booster.UI.Adapters.NavigationDrawerListAdapter;
import project.matthew.booster.UI.Helper.Constants;
import project.matthew.booster.UI.Helper.RealmHelper;
import project.matthew.booster.UI.Interfaces.QuestionnaireCompletionInterface;
import project.matthew.booster.UI.Interfaces.ActionBarSetupInterface;
import project.matthew.booster.UI.Interfaces.NavigationSetupInterface;
import project.matthew.booster.UI.Interfaces.QuestionnaireLoadInterface;
import project.matthew.booster.UI.Models.Answer;
import project.matthew.booster.UI.Models.Question;

import static android.view.View.GONE;

/**
 * Created by Matthew on 27/04/2018.
 */

public class MainActivity extends AppCompatActivity implements QuestionnaireLoadInterface, ActionBarSetupInterface, NavigationSetupInterface, QuestionnaireCompletionInterface {

    @BindView(R.id.nav_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_info_text)
    TextView mainInfoText;

    private int score;
    private String investorType;
    private String fund;
    private NavigationDrawerListAdapter mNavDrawerAdapter;
    private ListView mNavigationDrawerList;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Object mCurrentFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadQuestionsAndAnswers();

        hideActionBarTitle();
        setupNavigationDrawer();

        checkDone();
    }

    /**
     * Creates menu layout with icon and menu icon by assigning a custom view to the action bar.
     * @param menu Not used except to call super.
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.booster_action_bar_layout, null);
        actionBar.setCustomView(cView);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Open or close drawer toggle.
     * @param view
     */
    public void openDrawer(View view) {
        if (!mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.openDrawer(Gravity.START);
        } else {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
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
    public void hideActionBarTitle() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * Init method of nav drawer and assigning the adapter to the list view. Also sets
     * on item select listener.
     */
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

                TextView navItemTextView = (TextView) view.findViewById(R.id.nav_title);
                if (navItemTextView != null) {
                    SpannedString navItemTitle = (SpannedString) navItemTextView.getText();
                    String itemTitle = navItemTitle.toString();
                    switch (itemTitle) {
                        case "Investor Types":
                            // Do nothing
                            break;
                        case "Questionnaire":
                            hideMainInfoText(adapterView);
                            showFragment(position, new QuestionnaireFragment(), itemTitle);
                            break;
                        case "Submit":
                            if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(Constants.QUESTIONNAIRE_COMPLETE, false)) {
                                hideMainInfoText(adapterView);
                                showFragment(position, new SubmissionFragment(), itemTitle);
                            }
                            break;
                        default:
                            hideMainInfoText(adapterView);
                            showFragment(position, new InvestorTypeFragment(), itemTitle);
                    }
                }
            }

        });
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }

    /**
     * Hides the initial app info with transition object.
     * @param adapterView
     */
    private void hideMainInfoText(AdapterView<?> adapterView) {
        TransitionManager.beginDelayedTransition((ViewGroup) adapterView.getRootView());
        mainInfoText.setVisibility(GONE); // Hide the main app info text.
    }


    public void showFragment(int position, Object fragment, String fragmentTag) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) { // Close drawer if needed.
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
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

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setInvestorType(String investorType) {
        this.investorType = investorType;
    }

    @Override
    public String getInvestorType() {
        return investorType;
    }

    @Override
    public void setFund(String fund) {
        this.fund = fund;
    }

    @Override
    public String getFund() {
        return fund;
    }

    @Override
    public boolean checkDone() {
        Realm realm = Realm.getDefaultInstance();

        setQuestionnaireScore(realm); // Sum up scores from answered questions.

        RealmResults<Question> questionsFromRealm = realm.where(Question.class).findAll();
        RealmResults<Question> answeredQuestions = realm.where(Question.class).equalTo("isAnswered", true).findAll();
        realm.close();

        // If all questions have been answered, return 'yes the questionnaire is done'
        if (questionsFromRealm.size() == answeredQuestions.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add up the total score by getting the sum of each answer radio button value if it selected.
     *
     * @param realm
     */
    private void setQuestionnaireScore(Realm realm) {
        RealmResults<Answer> answersFromRealm = realm.where(Answer.class).findAll();
        final List<Answer> answers = realm.copyFromRealm(answersFromRealm);
        int tempScore = 0;
        for (Answer answer : answers) {
            if (answer.isSelected()) {
                tempScore += answer.getValue();
            }
        }
        setScore(tempScore);
    }

    /**
     * Used when the user has reloaded the app after sending questinnaire results.
     */
    @Override
    public void loadQuestionsAndAnswers() {
        Resources res = getResources();
        RealmHelper.initQuestionsAndAnswers(res);
    }
}
