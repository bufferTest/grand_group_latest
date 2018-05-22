package com.grandgroup.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.grandgroup.R;
import com.grandgroup.utills.AppConstant;
import com.grandgroup.utills.AppPrefrence;

public class SplashActivity extends AppCompatActivity {
    private AppCompatActivity mActivity;

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mActivity = SplashActivity.this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setSplashTime();
    }

    private void setSplashTime() {

        runnable = new Runnable() {

            @Override
            public void run() {
                if(AppPrefrence.init(mActivity).getBoolean(AppConstant.IS_LOGGED_IN)) {
                    Intent intent = new Intent(mActivity, DashBoardActivity.class);
                    startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                }
                else {
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                }
            }
        };

        handler = new Handler();
        int SPLASH_TIME_DURATION = 2500;
        handler.postDelayed(runnable, SPLASH_TIME_DURATION);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

}