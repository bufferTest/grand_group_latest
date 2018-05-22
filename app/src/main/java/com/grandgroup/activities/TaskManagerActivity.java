package com.grandgroup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.adapter.CalenderAdapter;
import com.grandgroup.adapter.EventsAdapter;
import com.grandgroup.adapter.header_Adapter;
import com.grandgroup.database.SQLiteQueries;
import com.grandgroup.model.EventsModel;
import com.grandgroup.model.calenderModel;
import com.grandgroup.utills.GrandGroupHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskManagerActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.calenderRecyclerView)
    RecyclerView calenderRecyclerView;
    @BindView(R.id.headerRecyclerView)
    RecyclerView headerRecyclerView;
    @BindView(R.id.month)
    TextView month_name;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.rv_events)
    RecyclerView rv_events;
    @BindView(R.id.tv_no_events)
    TextView tv_no_events;

    private AppCompatActivity mContext;
    private int mFirstDay;
    private int year;
    private int month;
    private int date;
    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        setInitialData();

        cal.setTimeInMillis(System.currentTimeMillis());
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DAY_OF_MONTH);
        setUpWeekNames();
        setupcalender();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventsAdapter(month, date, year);
    }

    private void setInitialData() {
        mContext = TaskManagerActivity.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Task Manager");
        btnAdd.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_back, R.id.iv_previous, R.id.iv_forward, R.id.btn_add})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                mContext.overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
                break;
            case R.id.iv_previous:
                if (month == 0) {
                    year = year - 1;
                    month = 11;
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.YEAR, year);
                } else {

                    month = month - 1;
                    cal.set(Calendar.MONTH, month);
                }
                setupcalender();
                break;

            case R.id.iv_forward:
                if (month == 11) {
                    month = 0;
                    year = year + 1;
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.YEAR, year);
                } else {
                    month = month + 1;
                    cal.set(Calendar.MONTH, month);
                }
                setupcalender();
                break;

            case R.id.btn_add:
                Intent intent = new Intent(mContext, AddTaskActivity.class);
                startActivity(intent);
                mContext.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
        }
    }

    public void setupcalender() {
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                mFirstDay = 0;
                break;
            case Calendar.MONDAY:
                mFirstDay = 1;
                break;
            case Calendar.TUESDAY:
                mFirstDay = 2;
                break;
            case Calendar.WEDNESDAY:
                mFirstDay = 3;
                break;
            case Calendar.THURSDAY:
                mFirstDay = 4;
                break;
            case Calendar.FRIDAY:
                mFirstDay = 5;
                break;
            case Calendar.SATURDAY:
                mFirstDay = 6;
                break;
            default:
                break;
        }

        SimpleDateFormat month = new SimpleDateFormat("MMMM", Locale.US);
        SimpleDateFormat year = new SimpleDateFormat("YYYY", Locale.US);
        String month_names = month.format(cal.getTime());
        String year_name = year.format(cal.getTime());
        StringBuilder stringBuilder = new StringBuilder(month_names);
        stringBuilder.append(", ");
        stringBuilder.append(year_name);
        month_name.setText(stringBuilder.toString());

        setUpAdapterData(daysInMonth, mFirstDay);
    }

    private void setUpWeekNames() {
        ArrayList<String> daysList = new ArrayList<>();
        daysList.add("SUN");
        daysList.add("MON");
        daysList.add("TUE");
        daysList.add("WED");
        daysList.add("THU");
        daysList.add("FRI");
        daysList.add("SAT");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 7);
        headerRecyclerView.setLayoutManager(gridLayoutManager);
        header_Adapter headerAdapter = new header_Adapter(mContext, daysList);
        headerRecyclerView.setAdapter(headerAdapter);
    }

    private void setUpAdapterData(int daysInMonth, int mFirstDay) {
        ArrayList<calenderModel> arrayList = new ArrayList<>();
        for (int j = 0; j < mFirstDay; j++) {
            arrayList.add(null);
        }
        for (int i = 1; i <= daysInMonth; i++) {
            calenderModel calendarModel = new calenderModel();
            calendarModel.setSelected(false);
            calendarModel.setValue(i);
            arrayList.add(calendarModel);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 7);
        calenderRecyclerView.setLayoutManager(gridLayoutManager);
        CalenderAdapter calenderAdpter = new CalenderAdapter(mContext, arrayList, cal, new CalenderAdapter.onDayClick() {
            @Override
            public void onDayClick(Integer position) {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                date = position;
                setEventsAdapter(month, date, year);
            }
        });
        calenderRecyclerView.setAdapter(calenderAdpter);
        calenderAdpter.notifyDataSetChanged();
    }


    private void setEventsAdapter(int month, int date, int year) {
        String formattedDate = String.valueOf(new StringBuilder().append(GrandGroupHelper.getMonth(month)).append(" ").append(date).append(", ").append(year));
        Log.e("formatteddate",formattedDate);
        ArrayList<EventsModel> eventsList = new ArrayList<>(SQLiteQueries.getInstance(mContext).getEvents(formattedDate));
        if (eventsList.size() > 0) {
            tv_no_events.setVisibility(View.GONE);
            rv_events.setVisibility(View.VISIBLE);
            EventsAdapter adapter = new EventsAdapter(eventsList);
            rv_events.setHasFixedSize(true);
            rv_events.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv_events.setLayoutManager(llm);
        } else {
            tv_no_events.setVisibility(View.VISIBLE);
            rv_events.setVisibility(View.GONE);
        }
    }
}
