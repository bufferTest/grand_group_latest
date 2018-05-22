package com.grandgroup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandgroup.R;
import com.grandgroup.model.ShiftDetailModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShiftStructureAdapter extends RecyclerView.Adapter<ShiftStructureAdapter.CustomHolder> {

    private ArrayList<ShiftDetailModel> dayList;
    private Context context;

    public ShiftStructureAdapter(Context context, ArrayList<ShiftDetailModel> daysList) {
        this.dayList = daysList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShiftStructureAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        return new ShiftStructureAdapter.CustomHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftStructureAdapter.CustomHolder holder, int position) {
        holder.tvText.setText(dayList.get(position).getShift_name());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;

        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
