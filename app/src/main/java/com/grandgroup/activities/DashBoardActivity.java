package com.grandgroup.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.grandgroup.R;
import com.grandgroup.utills.AppConstant;
import com.grandgroup.utills.AppPrefrence;
import com.grandgroup.utills.CallProgressWheel;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardActivity extends AppCompatActivity {
    private AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dash_board);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        mContext = DashBoardActivity.this;
    }

    @OnClick({R.id.iv_notifications, R.id.iv_settings, R.id.iv_task_manager, R.id.iv_attendance, R.id.iv_shift_structure, R.id.iv_report, R.id.iv_my_profile, R.id.iv_logout})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_notifications:
                goToActivity(NotificationsActivity.class);
                break;

            case R.id.iv_settings:
                Toast.makeText(mContext, "Under Development", Toast.LENGTH_LONG).show();
                break;

            case R.id.iv_task_manager:
                goToActivity(TaskManagerActivity.class);
                break;

            case R.id.iv_attendance:
                goToActivity(AttendanceActivity.class);
                break;

            case R.id.iv_shift_structure:
                goToActivity(ShiftStructure.class);
                break;

            case R.id.iv_report:
                goToActivity(ReportActivity.class);
                break;

            case R.id.iv_my_profile:
                goToActivity(UserProfileActivity.class);
                break;

            case R.id.iv_logout:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("Are you sure you want Logout?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        CallProgressWheel.showLoadingDialog(mContext);
                        ParseUser.logOutInBackground(new LogOutCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    CallProgressWheel.dismissLoadingDialog();
                                    AppPrefrence.init(mContext).putBoolean(AppConstant.IS_LOGGED_IN, false);
                                    dialog.cancel();
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    startActivity(intent);
                                    mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                                    finish();
                                } else {
                                    CallProgressWheel.dismissLoadingDialog();
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    private void goToActivity(Class<?> classname) {
        startActivity(new Intent(mContext, classname));
        mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}

