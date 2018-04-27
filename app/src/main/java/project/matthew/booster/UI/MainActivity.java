package project.matthew.booster.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.matthew.booster.R;
import project.matthew.booster.UI.Interfaces.MainActivitySetupInterface;

/**
 * Created by Matthew on 27/04/2018.
 */

public class MainActivity extends AppCompatActivity implements MainActivitySetupInterface {

    @BindView(R.id.nav_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_items)
    NavigationView mNavView;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar mToolbar;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        initNavItemSelectListener();

    }

    @Override
    public void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void initNavItemSelectListener() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
