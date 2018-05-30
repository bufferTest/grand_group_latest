package com.grandgroup.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.grandgroup.R;
import com.grandgroup.model.UserProfileBean;
import com.grandgroup.utills.AppConstant;
import com.grandgroup.utills.AppPrefrence;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.CommonUtils;
import com.grandgroup.utills.SnackbarUtil;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cl_username_pswd)
    ConstraintLayout clUserNamePswd;
    @BindView(R.id.cb_remember_me)
    CheckBox cbRememberMe;
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_pswd)
    EditText etPswd;
    private GradientDrawable bgShape;
    private AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;
        ButterKnife.bind(mContext);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // set backgroud color to drawable
        bgShape = (GradientDrawable) btnLogin.getBackground();
        bgShape.setColor(getResources().getColor(R.color.colorButton));

        bgShape = (GradientDrawable) clUserNamePswd.getBackground();
        bgShape.setColor(getResources().getColor(R.color.colorWhite));
        setIntialData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (manager != null)
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                buildAlertMessageNoGps();
            }
        checkForPermission();

    }

    @OnClick({R.id.btn_login, R.id.cb_remember_me, R.id.forgot_password})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                hideKeyboard();
                String email = etUserName.getText().toString();
                String password = etPswd.getText().toString();
                if (CommonUtils.getInstance().isEmpty(email))
                    SnackbarUtil.showWarningShortSnackbar(mContext, "Please enter email");
                else if (CommonUtils.getInstance().isEmpty(password))
                    SnackbarUtil.showWarningShortSnackbar(mContext, "Please enter Password");
                else {
                    if (cbRememberMe.isChecked()) {
                        AppPrefrence.init(mContext).putBoolean(AppConstant.IS_REMEMBERED, true);
                        AppPrefrence.init(mContext).putString(AppConstant.USER_NAME, etUserName.getText().toString());
                        AppPrefrence.init(mContext).putString(AppConstant.USER_PSWD, etPswd.getText().toString());
                    }
                    loginUser();
                }

                break;

            case R.id.cb_remember_me:
                if (!cbRememberMe.isChecked()) {
                    AppPrefrence.init(mContext).putBoolean(AppConstant.IS_REMEMBERED, true);
                    AppPrefrence.init(mContext).putString(AppConstant.USER_NAME, "");
                    AppPrefrence.init(mContext).putString(AppConstant.USER_PSWD, "");
                }
                break;

            case R.id.forgot_password:
                openForgotDialog();
                break;
        }
    }

    private void openForgotDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        final EditText forgotEmail = dialog.findViewById(R.id.et_forgot_email);
        forgotEmail.requestFocus();
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btncancel = dialog.findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgotEmail.getText().toString().trim();
                if (CommonUtils.getInstance().isEmpty(email)) {
                    SnackbarUtil.showWarningShortSnackbarTopDialog(dialog, getResources().getString(R.string.fields_error_message));
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(forgotEmail.getText().toString().trim()).matches()) {
                    SnackbarUtil.showWarningShortSnackbarTopDialog(dialog, getResources().getString(R.string.forgot_email_empty_text));
                } else {
                    CallProgressWheel.showLoadingDialog(mContext);
                    ParseUser.requestPasswordResetInBackground(email,
                            new RequestPasswordResetCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        CallProgressWheel.dismissLoadingDialog();
                                        Toast.makeText(mContext, "Reset password link has been sent to your email.", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } else {
                                        CallProgressWheel.dismissLoadingDialog();
                                        SnackbarUtil.showWarningShortSnackbarTopDialog(dialog, "Something went wrong.");
                                    }
                                }
                            });

                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loginUser() {
        CallProgressWheel.showLoadingDialog(mContext);
        ParseUser.logInInBackground(etUserName.getText().toString(), etPswd.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (user != null) {
                    CallProgressWheel.dismissLoadingDialog();
                    UserProfileBean userProfileBean = new UserProfileBean();
                    ParseUser parseUser = ParseUser.getCurrentUser();
                    AppPrefrence.init(mContext).putBoolean(AppConstant.IS_LOGGED_IN, true);
                    userProfileBean.setUserFirstName(parseUser.getString(getString(R.string.userFirstName)));
                    userProfileBean.setUserLastName(parseUser.getString(getString(R.string.userLastName)));
                    userProfileBean.setUserEmail(parseUser.getString(getString(R.string.userEmail)));
                    userProfileBean.setUserPassword(parseUser.getString(getString(R.string.userPassword)));
                    userProfileBean.setUserName(parseUser.getString(getString(R.string.userName)));
                    userProfileBean.setAdmin(parseUser.getBoolean(getString(R.string.isAdmin)));
                    ParseFile postImage = parseUser.getParseFile(getString(R.string.profilePic));
                    if (postImage != null)
                        userProfileBean.setUserProfilePicUrl(postImage.getUrl());
                    else
                        userProfileBean.setUserProfilePicUrl("");

                    Gson gson = new Gson();
                    String json = gson.toJson(userProfileBean);
                    AppPrefrence.init(mContext).putString(AppConstant.USER_PROFILE, json);
                    startActivity(new Intent(mContext, DashBoardActivity.class));
                    mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();

                } else {
                    CallProgressWheel.dismissLoadingDialog();
                    Toast.makeText(getApplicationContext(), "Invalid email or password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setIntialData() {
        if (AppPrefrence.init(mContext).getBoolean(AppConstant.IS_REMEMBERED)) {
            cbRememberMe.setChecked(true);
            etUserName.setText(AppPrefrence.init(mContext).getString(AppConstant.USER_NAME));
            etPswd.setText(AppPrefrence.init(mContext).getString(AppConstant.USER_PSWD));
        }

    }

    private void buildAlertMessageNoGps() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.gps_check_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no_text), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
}

