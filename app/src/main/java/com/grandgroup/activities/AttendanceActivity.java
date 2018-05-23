package com.grandgroup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.grandgroup.R;
import com.grandgroup.model.UserProfileBean;
import com.grandgroup.utills.AppConstant;
import com.grandgroup.utills.AppPrefrence;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.GrandGroupHelper;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttendanceActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private AppCompatActivity mContext;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setInitialData();
    }

    private void setInitialData() {
        mContext = AttendanceActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Attendance");
        qrScan = new IntentIntegrator(mContext);
       // qrScan.setOrientation(1);
    }

    @OnClick({R.id.btn_back, R.id.btn_scan})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
            case R.id.btn_scan:
                qrScan.initiateScan();
                break;
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            /*if qrcode has nothing in it*/
            if (result.getContents() == null) {
                Toast.makeText(mContext, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                /*if qr contains data*/
                try {
                    /*converting the data to json*/
                    JSONObject obj = new JSONObject(result.getContents());

                } catch (JSONException e) {
                    e.printStackTrace();
                    uploadDataOnParse(result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void uploadDataOnParse(String contents) {
        CallProgressWheel.showLoadingDialog(mContext);
        ParseUser user = ParseUser.getCurrentUser();
        String parseUserId = user.getObjectId();
        Gson gson = new Gson();
        String json = AppPrefrence.init(mContext).getString(AppConstant.USER_PROFILE);
        UserProfileBean userProfileObj = gson.fromJson(json, UserProfileBean.class);
        ParseObject attendenceObject = new ParseObject("Attendence");
        attendenceObject.put("location_code", contents);
        attendenceObject.put("submitted_date", getCurrentDate());
        attendenceObject.put("submitted_day", getCurrentDate());
        attendenceObject.put("submitted_by", ParseObject.createWithoutData("_User",parseUserId));
        attendenceObject.put("submitted_month", getCurrentDate());
       // attendenceObject.put("submitted_site_name", etControls.getText().toString());
        attendenceObject.put("user_name", userProfileObj.getUserName());
        attendenceObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                CallProgressWheel.dismissLoadingDialog();
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getApplicationContext(), "Please, Try Again", Toast.LENGTH_LONG).show();

            }
        });
    }

    private String getCurrentDate(){
         Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return String.valueOf(new StringBuilder().append(GrandGroupHelper.getMonth(cal.get(Calendar.MONTH))).append(" ").append(cal.get(Calendar.DAY_OF_MONTH)).append(", ").append(cal.get(Calendar.YEAR)));
    }
}
