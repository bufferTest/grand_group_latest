package com.grandgroup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grandgroup.R;
import com.grandgroup.database.SQLiteQueries;
import com.grandgroup.model.EventsModel;
import com.grandgroup.utills.GrandGroupHelper;
import com.grandgroup.views.CustomDateDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTaskActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_event_date)
    TextView tvEventDate;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.my_toolbar)
    RelativeLayout myToolbar;
    @BindView(R.id.tv_event_date_title)
    TextView tvEventDateTitle;
    @BindView(R.id.tv_event_name_title)
    TextView tvEventNameTitle;
    @BindView(R.id.et_event_name)
    EditText etEventName;
    @BindView(R.id.tv_event_desc_title)
    TextView tvEventDescTitle;
    @BindView(R.id.et_event_desc)
    EditText etEventDesc;
    @BindView(R.id.btn_save)
    Button btnSave;
    private AppCompatActivity mContext;
    private EventsModel eventsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setInitialData();
        eventsModel = (EventsModel) getIntent().getSerializableExtra("value");
        tvEventDate.setText(eventsModel.getEvent_date());
        etEventName.setText(eventsModel.getEvent_title());
        etEventDesc.setText(eventsModel.getEvent_desc());
    }

    private void setInitialData() {
        mContext = EditTaskActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Edit Task");
    }

    @OnClick({R.id.btn_back, R.id.tv_event_date, R.id.btn_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
            case R.id.tv_event_date:
                CustomDateDialog.getInstance().DatePicker(mContext, new CustomDateDialog.DateDialogListener() {
                    @Override
                    public void onOkayClick(int date, int month, int year) {
                        String formattedDate = String.valueOf(new StringBuilder().append(GrandGroupHelper.getMonth(month)).append(" ").append(date).append(", ").append(year));
                        tvEventDate.setText(formattedDate);
                    }
                });
                break;
            case R.id.btn_save:
                eventsModel.setEvent_title(etEventName.getText().toString());
                eventsModel.setEvent_date(tvEventDate.getText().toString());
                eventsModel.setEvent_desc(etEventDesc.getText().toString());
                if (eventsModel.getEvent_date().trim().length() == 0)
                    Toast.makeText(mContext, "Please select Date", Toast.LENGTH_SHORT).show();
                else if (eventsModel.getEvent_desc().trim().length() == 0)
                    Toast.makeText(mContext, "Please enter Title", Toast.LENGTH_SHORT).show();
                else if (eventsModel.getEvent_title().trim().length() == 0)
                    Toast.makeText(mContext, "Please enter description", Toast.LENGTH_SHORT).show();
                else{
                    SQLiteQueries.getInstance(mContext).updateEvent(eventsModel);
                    finish();
                }
                break;
        }
    }
}
