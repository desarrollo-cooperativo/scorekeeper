package com.transition.scorekeeper.mobile.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.transition.scorekeeper.BuildConfig;
import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.internal.di.HasComponent;
import com.transition.scorekeeper.mobile.internal.di.components.DaggerMatchComponent;
import com.transition.scorekeeper.mobile.internal.di.components.MatchComponent;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.view.activity.interfaces.IFilterMatches;
import com.transition.scorekeeper.mobile.view.fragment.MatchesFragment;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.MatchesView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, HasComponent<MatchComponent>,
        MatchesFragment.MatchesFragmentListener {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    @Bind(R.id.matches_tabs)
    protected TabLayout tabs;

    private MatchComponent matchComponent;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, MatchesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        initializeNavigationView();
        initializeTabs();
        checkPlayServices();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void initializeNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        if (navigationView != null) {
            if (BuildConfig.DEBUG) {
                navigationView.getMenu().setGroupVisible(R.id.navigation_group_testing, false);
            }
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    protected void loadFragment() {
        loadFragment(new MatchesFragment(), getFragmentResIDContainer(), ViewConstants.FragmentTag.MATCHES, false);
    }

    @Override
    protected int getFragmentResIDContainer() {
        return R.id.matches_container;
    }


    private void initializeInjector() {
        this.matchComponent = DaggerMatchComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public MatchComponent getComponent() {
        return matchComponent;
    }

    @Override
    public void onMatchClicked(MatchModel matchModel) {
    }

    @Override
    public int getSelectTab() {
        return tabs.getSelectedTabPosition();
    }

    private void initializeTabs() {
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = getFragment();
                if (fragment instanceof IFilterMatches) {
                    ((IFilterMatches) fragment).filterBy(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick(R.id.matches_fab)
    protected void onFabClick() {
        Fragment fragment = getFragment();
        if (fragment instanceof MatchesView) {
            ((MatchesView) fragment).onFabClick();
        }
    }
}
