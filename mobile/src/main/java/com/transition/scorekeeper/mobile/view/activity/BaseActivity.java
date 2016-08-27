package com.transition.scorekeeper.mobile.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.App;
import com.transition.scorekeeper.mobile.internal.di.components.ApplicationComponent;
import com.transition.scorekeeper.mobile.internal.di.modules.ActivityModule;
import com.transition.scorekeeper.mobile.navigation.Navigator;
import com.transition.scorekeeper.mobile.utils.ViewUtils;
import com.transition.scorekeeper.mobile.view.activity.interfaces.IBaseActivity;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.IOnBackPressed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;
    protected DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        this.getApplicationComponent().inject(this);
        ButterKnife.bind(this);
        loadFragment();
        initializeActionBar();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private void initializeActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        initializeDrawer(toolbar);
    }

    private void initializeDrawer(Toolbar toolbar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!toggle.isDrawerIndicatorEnabled()) {
                    onBackPressed();
                }
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected abstract void loadFragment();

    protected int getLayoutResID() {
        return R.layout.activity_matches;
    }

    private void doBack() {
        super.onBackPressed();
        if (hasBackStack()) {
            setBackStackState();
        } else {
            setHomeState();
        }
    }

    protected boolean hasBackStack() {
        return getSupportFragmentManager().getBackStackEntryCount() >= 1;
    }

    protected void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ViewUtils.hideKeyboard(currentFocus.getWindowToken(), getApplicationContext());
        }
    }

    protected void loadFragment(Fragment fragment, int container, String tag, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected abstract int getFragmentResIDContainer();

    @Override
    public void setBackStackState() {
        if (toggle != null && drawer != null) {
            toggle.setDrawerIndicatorEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void setHomeState() {
        if (toggle != null && drawer != null) {
            toggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_download:
                //TODO: re implement the functionality
                copyDbToExternal();
                break;
        }
        closeDrawer();
        return true;
    }

    private void closeDrawer() {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            IOnBackPressed fragment = (IOnBackPressed) getSupportFragmentManager().findFragmentById(getFragmentResIDContainer());
            if (fragment != null && fragment.canDoBack()) {
                doBack();
            }
        }
    }

    protected Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentResIDContainer());
    }

    private void copyDbToExternal() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            exportDb();
        }
    }

    private void exportDb() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String DB_NAME = "scorekeeper_db";
                String currentDBPath = "//data//" + getApplicationContext().getPackageName() + "//databases//"
                        + DB_NAME;

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, DB_NAME);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportDb();
                }
            }
        }
    }

}
