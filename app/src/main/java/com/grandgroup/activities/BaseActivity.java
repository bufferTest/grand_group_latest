package com.grandgroup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grandgroup.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }
}
