package com.grandgroup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

public class ShiftStructureAdapter extends RecyclerView.Adapter<ShiftStructureAdapter.CustomHolder> implements View.OnClickListener {

    private ArrayList<ShiftDetailModel> dayList;
    private Context context;
    private ShiftStructureAdapter.ItemClickListener itemClickListener;

    public ShiftStructureAdapter(Context context, ArrayList<ShiftDetailModel> daysList, ShiftStructureAdapter.ItemClickListener itemClickListener) {
        this.dayList = daysList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ShiftStructureAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_structure_item, parent, false);
        return new ShiftStructureAdapter.CustomHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftStructureAdapter.CustomHolder holder, int position) {
        holder.tvText.setText(dayList.get(position).getShift_name());
        holder.tvDate.setText(dayList.get(position).getShift_start_date_str()+" to "+dayList.get(position).getShift_end_date_str());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }
    @Override
    public void onClick(View v) {

    }

    public interface ItemClickListener {
        void onClick(int position);
    }
    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.lay_shift)
        ConstraintLayout clContainer;

        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            clContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
    }
}
