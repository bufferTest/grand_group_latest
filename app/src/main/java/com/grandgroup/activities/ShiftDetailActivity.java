package com.grandgroup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.grandgroup.R;
import com.grandgroup.model.ShiftDetailModel;
import com.grandgroup.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShiftDetailActivity extends AppCompatActivity {
    private AppCompatActivity mContext;
    @BindView(R.id.tv_title)
    CustomTextView tvTitle;
    @BindView(R.id.tv_shift_name)
    CustomTextView tvShiftName;
    @BindView(R.id.shift_start_date)
    CustomTextView shiftStartDate;
    @BindView(R.id.shift_end_date)
    CustomTextView shiftEndDate;
    @BindView(R.id.shift_detail)
    CustomTextView shiftDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_detail);
        setInitialData();
    }
    private void setInitialData() {
        mContext = ShiftDetailActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Shift Detail");
        if (getIntent().getSerializableExtra("shiftObj") != null) {
            ShiftDetailModel shiftDetailModel = (ShiftDetailModel) getIntent().getSerializableExtra("shiftObj");
            tvShiftName.setText(shiftDetailModel.getShift_name());
            shiftStartDate.setText(shiftDetailModel.getShift_start_date_str());
            shiftEndDate.setText(shiftDetailModel.getShift_end_date_str());
            shiftDetail.setText(shiftDetailModel.getShift_details());
        }
    }
    @OnClick({R.id.btn_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
        }
    }
}
