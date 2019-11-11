package com.example.gm1.pavilion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.models.list.AttendanceList;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<AttendanceList> attendanceLists;
    private Context context;

    public ListAdapter(List<AttendanceList> attendanceLists, Context context) {
        this.attendanceLists = attendanceLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_list,parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceList attendanceList =attendanceLists.get(position);

        holder.aDate.setText("Date : " + attendanceList.getDate());
        holder.aEntry.setText("Entry Time : " + attendanceList.getEntry_time());
        holder.aExit.setText("Exit Time : " + attendanceList.getExit_time());

    }

    @Override
    public int getItemCount() {
        return attendanceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView aDate, aEntry, aExit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            aDate = (TextView) itemView.findViewById(R.id.attendance_date);
            aEntry =(TextView) itemView.findViewById(R.id.attendance_entryTime);
            aExit = (TextView) itemView.findViewById(R.id.attendance_exitTime);
        }
    }
}
