package com.grandgroup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandgroup.R;

import java.util.ArrayList;

/**
 * Created by wegile on 25/7/17.
 */

public class header_Adapter extends RecyclerView.Adapter<header_Adapter.CustomHolder> {
    private AppCompatActivity mContext;
    private ArrayList<String> arrayList;

    public header_Adapter(AppCompatActivity mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_header, parent, false);
        CustomHolder vh = new CustomHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.calender_header_name.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        TextView calender_header_name;

        CustomHolder(View itemView) {
            super(itemView);
            calender_header_name = itemView.findViewById(R.id.calender_header_name);
        }
    }
}
