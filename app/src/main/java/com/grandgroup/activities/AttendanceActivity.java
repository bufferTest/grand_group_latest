package com.grandgroup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.grandgroup.R;

import org.json.JSONException;
import org.json.JSONObject;

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
        qrScan.setOrientation(1);
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
                   /* if control comes here
                    that means the encoded format not matches
                    in this case you can display whatever data is available on the qrcode
                    to a toast*/
                    Toast.makeText(mContext, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
