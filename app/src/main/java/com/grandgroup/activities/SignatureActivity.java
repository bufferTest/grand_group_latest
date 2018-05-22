package com.grandgroup.activities;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.grandgroup.R;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignatureActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.signaturePad)
    GestureOverlayView signature_pad;
    private AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        setInitialData();
    }

    private void setInitialData() {
        mContext = SignatureActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Signature");
    }

    @OnClick({R.id.btn_done,R.id.tv_cancle, R.id.btn_clear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                signature_pad.setDrawingCacheEnabled(true);
                       Bitmap signBitmap = Bitmap.createBitmap(signature_pad.getDrawingCache());
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        signBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                byte[] byteArray = out.toByteArray();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("byteArray", byteArray);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                        break;
            case R.id.tv_cancle:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
            break;
            case R.id.btn_clear:
                signature_pad.cancelClearAnimation();
                signature_pad.clear(true);
                break;
        }
    }
}

