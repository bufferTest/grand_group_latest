package com.grandgroup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.grandgroup.R;
import com.grandgroup.model.IncidentModel;
import com.grandgroup.model.RiskReportModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private ArrayList<IncidentModel> incidentList;
    private ArrayList<RiskReportModel> riskList;
    private Context context;
    private int reportSelection;
    private ReportAdapter.ItemClickListener itemClickListener;

    public ReportAdapter(Context context,int reportSelection, ArrayList<IncidentModel> incidentList, ReportAdapter.ItemClickListener itemClickListener) {
        this.incidentList = incidentList;
        this.context = context;
        this.reportSelection = reportSelection;
        this.itemClickListener = itemClickListener;

    }
    public ReportAdapter(Context context, ArrayList<RiskReportModel> riskList,int reportSelection, ReportAdapter.ItemClickListener itemClickListener) {
        this.riskList = riskList;
        this.context = context;
        this.reportSelection = reportSelection;
        this.itemClickListener = itemClickListener;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportAdapter.CustomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ReportAdapter.CustomHolder viewholder = (ReportAdapter.CustomHolder) holder;
        if(reportSelection == 0)
            viewholder.tvText.setText( "ID :"+incidentList.get(position).getOjectId());
        else if(reportSelection == 1)
            viewholder.tvText.setText( "ID :"+riskList.get(position).getObjectId());

    }

    @Override
    public int getItemCount() {
        int listSize =0;
        if(reportSelection == 0)
            listSize = incidentList.size();
        else if(reportSelection == 1)
            listSize = riskList.size();
        return listSize;
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
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.cl_container)
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








/*RecyclerView.Adapter<ReportAdapter.CustomHolder> implements View.OnClickListener{

    private ArrayList<IncidentModel> incidentList;
    private ArrayList<RiskReportModel> riskList;
    private Context context;
    private int reportSelection;

    ReportAdapter.ItemClickListener itemClickListener;

    @Override
    public void onClick(View v) {

    }

    public interface ItemClickListener {
        void onClick(int position);
    }



    public ReportAdapter(Context context,int reportSelection, ArrayList<IncidentModel> incidentList, ReportAdapter.ItemClickListener itemClickListener) {
        this.incidentList = incidentList;
        this.context = context;
        this.reportSelection = reportSelection;
        this.itemClickListener = itemClickListener;

    }
    public ReportAdapter(Context context, ArrayList<RiskReportModel> riskList,int reportSelection, ReportAdapter.ItemClickListener itemClickListener) {
        this.riskList = riskList;
        this.context = context;
        this.reportSelection = reportSelection;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public ReportAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
        return new ReportAdapter.CustomHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.CustomHolder holder, int position) {
       // holder.tvText.setVisibility(View.VISIBLE);
        if(reportSelection == 0)
        holder.tvText.setText( "ID :"+incidentList.get(position).getOjectId());
        else if(reportSelection == 1)
            holder.tvText.setText( "ID :"+riskList.get(position).getObjectId());
    }

    @Override
    public int getItemCount() {
        int listSize =0;
         if(reportSelection == 0)
             listSize = incidentList.size();
         else if(reportSelection == 1)
             listSize = riskList.size();
        return listSize;
    }

    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public CustomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getLayoutPosition());
        }
}

}
*/