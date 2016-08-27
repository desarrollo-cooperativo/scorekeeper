package com.transition.scorekeeper.mobile.view.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.navigation.Navigator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000;
    private boolean canOpenMainActivity = true;
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            callActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setProgressBarStyle();
        new Timer().schedule(task, SPLASH_DELAY);
    }

    private void setProgressBarStyle() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.splash_progress);
        if (progressBar != null) {
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        canOpenMainActivity = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        canOpenMainActivity = true;
    }

    private void callActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (canOpenMainActivity) {
                    Navigator.navigateToMatchList(getApplicationContext());
                    finish();
                }
            }
        });
    }
}
