package com.example.gm1.pavilion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.models.list.OvertimeList;

import java.util.List;

public class OvertimeAdapter extends RecyclerView.Adapter<OvertimeAdapter.ViewHolder> {
    private List<OvertimeList> overtimeLists;
    private Context context;

    public OvertimeAdapter(List<OvertimeList> overtimeLists, Context context) {
        this.overtimeLists = overtimeLists;
        this.context = context;
    }

    @Override
    public OvertimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overtime_list, parent, false);
        return new OvertimeAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull OvertimeAdapter.ViewHolder holder, int position) {
        OvertimeList overtimeList = overtimeLists.get(position);

        holder.oDate.setText(overtimeList.getDate());
        //holder.oRequestDate.setText(overtimeList.getCreated_at());
        holder.oApprove.setText(overtimeList.getIs_approved());
        //holder.lId.setText(String.format("%d",leaveList.getId()));

    }

    @Override
    public int getItemCount() {
        return overtimeLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView oDate, oApprove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            oDate = (TextView) itemView.findViewById(R.id.overtime_date);
            //oRequestDate = (TextView) itemView.findViewById(R.id.overtime_request);
            oApprove = (TextView) itemView.findViewById(R.id.overtime_approval);


        }
    }
}
