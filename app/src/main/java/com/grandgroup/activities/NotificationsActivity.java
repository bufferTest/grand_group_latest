package com.grandgroup.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.adapter.NotificationsAdapter;
import com.grandgroup.model.NotificationsModel;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.GrandGroupHelper;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_no_events)
    TextView tv_no_events;
    @BindView(R.id.rv_notifications)
    RecyclerView rv_notifications;
    private AppCompatActivity mContext;
    private ArrayList<NotificationsModel> notificationsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setInitialData();
    }

    private void setInitialData() {
        ButterKnife.bind(this);
        mContext = NotificationsActivity.this;
        tvTitle.setText("Notifications");
        getNotifications();
    }

    @OnClick({R.id.btn_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
        }
    }

    public void getNotifications() {
        if (GrandGroupHelper.grandGroupHelper(mContext).CheckIsConnectedToInternet()) {
            CallProgressWheel.showLoadingDialog(mContext);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Notifications");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> notifications, ParseException e) {
                    if (e == null) {
                        CallProgressWheel.dismissLoadingDialog();
                        if (notifications.size() > 0) {
                            for (int i = 0; i < notifications.size(); i++) {
                                NotificationsModel notificationsModel = new NotificationsModel();
                                notificationsModel.setTime(notifications.get(i).getString(getString(R.string.time)));
                                notificationsModel.setMessage(notifications.get(i).getString(getString(R.string.message)));
                                notificationsList.add(notificationsModel);
                            }
                        }

                        setAdapter();
                    } else {
                        CallProgressWheel.dismissLoadingDialog();

                    }
                }

            });
        } else {
            CallProgressWheel.dismissLoadingDialog();
        }
    }

    private void setAdapter() {
        try {
            if (notificationsList.size() > 0) {
                NotificationsAdapter adapter = new NotificationsAdapter(mContext, notificationsList);
                rv_notifications.setHasFixedSize(true);
                rv_notifications.setAdapter(adapter);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rv_notifications.setLayoutManager(llm);
            } else {
                tv_no_events.setVisibility(View.VISIBLE);
                rv_notifications.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

