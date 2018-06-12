package com.grandgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandgroup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.CustomHolder> {

    private ArrayList<String> dayList;
    private OnClick onClick;

    public SelectItemAdapter(ArrayList<String> daysList, OnClick onClick) {
        this.dayList = daysList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public SelectItemAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        return new SelectItemAdapter.CustomHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemAdapter.CustomHolder holder, int position) {
        holder.tvText.setText(dayList.get(position));
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public interface OnClick {
        void OnClick(String item);
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;

        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @butterknife.OnClick(R.id.tv_text)
        public void onClick() {
            onClick.OnClick(dayList.get(getLayoutPosition()));
        }

    }
}