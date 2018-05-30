package com.grandgroup.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.grandgroup.interfaces.CalenderDayClick;
import com.grandgroup.R;
import com.grandgroup.adapter.ShiftCalenderAdapter;
import com.grandgroup.adapter.ShiftStructureAdapter;
import com.grandgroup.adapter.header_Adapter;
import com.grandgroup.model.ShiftDetailModel;
import com.grandgroup.model.calenderModel;
import com.grandgroup.utills.CallProgressWheel;
import com.grandgroup.utills.GrandGroupHelper;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShiftStructure extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.calenderRecyclerView)
    RecyclerView calenderRecyclerView;
    @BindView(R.id.headerRecyclerView)
    RecyclerView headerRecyclerView;
    @BindView(R.id.month)
    TextView month_name;
    @BindView(R.id.rv_shifts)
    RecyclerView rv_shifts;
    @BindView(R.id.tv_no_events)
    TextView tv_no_events;

    private AppCompatActivity mContext;
    private int mFirstDay, year, month, date;
    private Calendar cal = Calendar.getInstance();
    private ArrayList<ShiftDetailModel> allShiftsList = new ArrayList<>();
    private ArrayList<ShiftDetailModel> selectedDateShiftsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_structure);
        setInitialData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpWeekNames();
        setupcalender();
        fetchShifts();
    }

    private void setInitialData() {
        mContext = ShiftStructure.this;
        ButterKnife.bind(mContext);
        tvTitle.setText("Shift Structure");

        cal.setTimeInMillis(System.currentTimeMillis());
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        date = cal.get(Calendar.DAY_OF_MONTH);
    }

    @OnClick({R.id.btn_back, R.id.iv_previous, R.id.iv_forward})
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
        if(selectedDateShiftsList != null)
            setAdapter();
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
        ShiftCalenderAdapter calenderAdpter = new ShiftCalenderAdapter(mContext, arrayList, cal, new CalenderDayClick() {
            @Override
            public void onDayClick(Integer position) {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                date = position;
                setAdapter();
            }
        });
        calenderRecyclerView.setAdapter(calenderAdpter);
        calenderAdpter.notifyDataSetChanged();
    }

    public void fetchShifts() {
        if (GrandGroupHelper.grandGroupHelper(mContext).CheckIsConnectedToInternet()) {
            CallProgressWheel.showLoadingDialog(mContext);
            ParseUser user = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query = new ParseQuery("Shifts");
            query.setLimit(100000);
            query.whereEqualTo("shift_user_id", user.getObjectId());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> userList, ParseException e) {
                    if (e == null) {
                        CallProgressWheel.dismissLoadingDialog();
                        if (userList.size() > 0) {
                            for (int i = 0; i < userList.size(); i++) {
                                ShiftDetailModel shiftDetailModel = new ShiftDetailModel();
                                ParseObject p = userList.get(i);
                                shiftDetailModel.setShift_assigned_by_id(p.getString(getString(R.string.shiftAssign)));
                                shiftDetailModel.setShift_details(p.getString(getString(R.string.shiftDetail)));
                                shiftDetailModel.setShift_end_date_str(p.getString(getString(R.string.shiftEndDateStr)));
                                shiftDetailModel.setShift_start_date_str(p.getString(getString(R.string.shiftStartDate)));
                                shiftDetailModel.setShift_name(p.getString(getString(R.string.shiftName)));
                                allShiftsList.add(shiftDetailModel);
                            }

                            setAdapter();
                        }

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
            selectedDateShiftsList = new ArrayList<>();
            DateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
            String currentDate = String.valueOf(new StringBuilder().append(GrandGroupHelper.getMonth(month)).append(" ").append(date).append(", ").append(year));
            Date date2 = targetFormat.parse(currentDate);
            for(int i = 0; i< allShiftsList.size(); i++){
                Date date = originalFormat.parse(allShiftsList.get(i).getShift_start_date_str());
                String formatedDate = targetFormat.format(date);
                Date date1 = targetFormat.parse(formatedDate);
                if(date1.equals(date2)){
                    selectedDateShiftsList.add(allShiftsList.get(i));
                }
            }

            if (selectedDateShiftsList.size() > 0) {
                tv_no_events.setVisibility(View.GONE);
                rv_shifts.setVisibility(View.VISIBLE);
                ShiftStructureAdapter adapter = new ShiftStructureAdapter(mContext, selectedDateShiftsList);
                rv_shifts.setHasFixedSize(true);
                rv_shifts.setAdapter(adapter);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rv_shifts.setLayoutManager(llm);
            } else {
                tv_no_events.setVisibility(View.VISIBLE);
                rv_shifts.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}