package com.grandgroup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.model.NotificationsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.CustomHolder> {

    private ArrayList<NotificationsModel> dayList;
    private Context context;

    public NotificationsAdapter(Context context, ArrayList<NotificationsModel> daysList) {
        this.dayList = daysList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationsAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
        return new NotificationsAdapter.CustomHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.CustomHolder holder, int position) {
        holder.tvTime.setVisibility(View.VISIBLE);
        holder.tvText.setText(dayList.get(position).getMessage());
        holder.tvTime.setText(dayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_time)
        TextView tvTime;



        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
