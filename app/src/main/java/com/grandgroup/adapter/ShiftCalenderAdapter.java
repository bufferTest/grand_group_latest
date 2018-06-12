package com.grandgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grandgroup.database.SQLiteQueries;
import com.grandgroup.interfaces.CalenderDayClick;
import com.grandgroup.R;
import com.grandgroup.model.EventsModel;
import com.grandgroup.model.ShiftDetailModel;
import com.grandgroup.model.calenderModel;
import com.grandgroup.utills.GrandGroupHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShiftCalenderAdapter extends RecyclerView.Adapter<ShiftCalenderAdapter.CustomHolder> {
    private AppCompatActivity mContext;
    private ArrayList<calenderModel> arrayList;
    private CalenderDayClick ondayclick;
    private Calendar currentcalendar;
    private Calendar calendar;
    private Integer selectedPosition;
    private boolean firsttym = true;
    private ArrayList<ShiftDetailModel> allShiftsList;


    public ShiftCalenderAdapter(AppCompatActivity mContext, ArrayList<calenderModel> arrayList, Calendar calendar, ArrayList<ShiftDetailModel> allShiftsList, CalenderDayClick ondayclick) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.calendar = calendar;
        this.ondayclick = ondayclick;
        this.allShiftsList = allShiftsList;
        currentcalendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public ShiftCalenderAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_model_layout, parent, false);
        return new ShiftCalenderAdapter.CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftCalenderAdapter.CustomHolder holder, int position) {
        if (arrayList.get(position) != null) {
            holder.material_calendar_day.setText(arrayList.get(position).getValue().toString());
            if (arrayList.get(position).getSelected()) {
                selectedPosition = position;
                holder.lay_day.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector));
            } else {
                holder.lay_day.setBackground(null);
            }

            getEventsList(position, holder.saved_event_imageView);
            if (firsttym)
                if (currentcalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
                    if (currentcalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH))
                        if (arrayList.get(position).getValue() == currentcalendar.get(Calendar.DAY_OF_MONTH)) {
                            selectedPosition = position;
                            holder.lay_day.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector));
                            firsttym = false;
                        }
        }
    }
    private void getEventsList(int position, ImageView ivIndicator) {
        String formattedDate = String.valueOf(new StringBuilder().append(GrandGroupHelper.getMonth(calendar.get(Calendar.MONTH))).append(" ")
                .append(arrayList.get(position).getValue().toString())
                .append(", ").append(calendar.get(Calendar.YEAR)));
        DateFormat originalFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        try {
        Date date2 = targetFormat.parse(formattedDate);
        for(int i = 0; i< allShiftsList.size(); i++){
            Date date = originalFormat.parse(allShiftsList.get(i).getShift_start_date_str());
            Date date1 = originalFormat.parse(allShiftsList.get(i).getShift_end_date_str());
            String formatedDate = targetFormat.format(date);
            String formatedDate1 = targetFormat.format(addDay(date1));
            Date dateStart = targetFormat.parse(formatedDate);
            Date dateEnd = targetFormat.parse(formatedDate1);
            if(dateStart.equals(date2) || date2.after(dateStart)&&date2.before(dateEnd))
                ivIndicator.setVisibility(View.VISIBLE);
            /*else
                ivIndicator.setVisibility(View.INVISIBLE);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  Date addDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.material_calendar_day)
        TextView material_calendar_day;
        @BindView(R.id.saved_event_imageView)
        ImageView saved_event_imageView;
        @BindView(R.id.lay_day)
        RelativeLayout lay_day;


        CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.lay_day)
        public void onDayClick() {
            if (arrayList.get(getLayoutPosition()) != null) {
                if (selectedPosition != null) {
                    arrayList.get(selectedPosition).setSelected(false);
                    notifyItemChanged(selectedPosition);
                }

                arrayList.get(getLayoutPosition()).setSelected(true);
                notifyItemChanged(getLayoutPosition());
                ondayclick.onDayClick(arrayList.get(getLayoutPosition()).getValue());
            }

        }
    }
}
