package com.grandgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.model.calenderModel;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CustomHolder> {
    private AppCompatActivity mContext;
    private ArrayList<calenderModel> arrayList;
    private onDayClick ondayclick;
    private Calendar currentcalendar;
    private Calendar calendar;
    private Integer selectedPosition;
    private boolean firsttym = true;


    public CalenderAdapter(AppCompatActivity mContext, ArrayList<calenderModel> arrayList, Calendar calendar, onDayClick ondayclick) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.calendar = calendar;
        this.ondayclick = ondayclick;
        currentcalendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_model_layout, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        if (arrayList.get(position) != null) {
            holder.material_calendar_day.setText(arrayList.get(position).getValue().toString());
//            holder.saved_event_imageView.setVisibility(View.VISIBLE);
            if (arrayList.get(position).getSelected()) {
                selectedPosition = position;
                holder.lay_day.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector));
            } else {
                holder.lay_day.setBackground(null);
            }

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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface onDayClick {
        void onDayClick(Integer position);
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
