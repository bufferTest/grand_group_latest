package com.grandgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.model.EventsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.CustomHolder> {

    private ArrayList<EventsModel> eventsList;

    public EventsAdapter(ArrayList<EventsModel> eventsList) {
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        return new CustomHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.tvText.setText(eventsList.get(position).getEvent_title());
        holder.tvDate.setText(eventsList.get(position).getEvent_date());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
