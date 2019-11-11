package com.example.gm1.pavilion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.models.list.CateringList;
import com.example.gm1.pavilion.models.response.CateringResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CateringAdapter extends RecyclerView.Adapter<CateringAdapter.ViewHolder> {

    private List<CateringList> cateringLists;
    private Context context;

    public CateringAdapter(List<CateringList> cateringLists, Context context) {
        this.cateringLists = cateringLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catering_list,parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CateringList cateringList =cateringLists.get(position);

        holder.cDate.setText(cateringList.getDate());
        holder.cDay.setText(cateringList.getDay());
        holder.cDelete.setText("CANCEL");
        holder.cId.setText(String.format("%d",cateringList.getId()));

    }

    @Override
    public int getItemCount() {
        return cateringLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cDate, cDay, cDelete, cId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cDate = (TextView) itemView.findViewById(R.id.catering_date);
            cDay =(TextView) itemView.findViewById(R.id.catering_day);
            cDelete = (TextView) itemView.findViewById(R.id.catering_delete);
            cId =(TextView) itemView.findViewById(R.id.catering_id);
            cId.setVisibility(View.GONE);

            cDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOder();
                }
            });
        }
        private void deleteOder(){
            int id = Integer.valueOf(cId.getText().toString());

            Call<CateringResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .deleteMeal(id);

            call.enqueue(new Callback<CateringResponse>() {
                @Override
                public void onResponse(Call<CateringResponse> call, Response<CateringResponse> response) {
                    CateringResponse deleteResponse = response.body();
                    if(deleteResponse.isStatus()){
                        Toast.makeText(itemView.getContext(), deleteResponse.getMessage(), Toast.LENGTH_LONG).show();
                        cateringLists.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }else{
                        Toast.makeText(itemView.getContext(), deleteResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<CateringResponse> call, Throwable t) {

                }
            });
        }
    }
}
