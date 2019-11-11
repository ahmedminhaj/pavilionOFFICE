package com.example.gm1.pavilion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.models.list.LeaveList;

import java.util.List;

public class LeaveManageAdapter extends RecyclerView.Adapter<LeaveManageAdapter.ViewHolder> {

    private List<LeaveList> leaveLists;
    private Context context;

    public LeaveManageAdapter(List<LeaveList> leaveLists, Context context) {
        this.leaveLists = leaveLists;
        this.context = context;
    }

    @Override
    public LeaveManageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_list, parent, false);
        return new LeaveManageAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull LeaveManageAdapter.ViewHolder holder, int position) {
        LeaveList leaveList = leaveLists.get(position);

        holder.lDate.setText(leaveList.getDate());
        //holder.lRequestDate.setText(leaveList.getCreated_at());
        holder.lApprove.setText(leaveList.getIs_approved());
        //holder.lId.setText(String.format("%d",leaveList.getId()));

    }

    @Override
    public int getItemCount() {
        return leaveLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lDate, lApprove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lDate = (TextView) itemView.findViewById(R.id.leave_date);
            //lRequestDate = (TextView) itemView.findViewById(R.id.leave_request);
            lApprove = (TextView) itemView.findViewById(R.id.leave_approval);


        }
    }
}
