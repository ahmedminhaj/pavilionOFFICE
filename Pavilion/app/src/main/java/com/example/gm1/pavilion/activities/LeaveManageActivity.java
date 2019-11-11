package com.example.gm1.pavilion.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.adapter.LeaveManageAdapter;
import com.example.gm1.pavilion.adapter.OvertimeAdapter;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.models.list.LeaveList;
import com.example.gm1.pavilion.models.response.LeaveManageResponse;
import com.example.gm1.pavilion.models.list.OvertimeList;
import com.example.gm1.pavilion.models.response.OvertimeResponse;
import com.example.gm1.pavilion.models.User;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveManageActivity extends AppCompatActivity {
    Button mHome;
    Button mLeaveRequest;
    Button mOvertimeRequest;
    RecyclerView leaveRecyclerView;
    RecyclerView.Adapter leaveAdapter;
    List<LeaveList> leaveData;
    RecyclerView overtimeRecyclerView;
    RecyclerView.Adapter overtimeAdapter;
    List<OvertimeList> overtimeData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_manage);

        mHome = (Button)findViewById(R.id.button_home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(LeaveManageActivity.this,HomeActivity.class);
                startActivity(HomeIntent);
            }
        });

        mLeaveRequest = (Button)findViewById(R.id.button_request_leave);
        mLeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeaveRequestIntent = new Intent(LeaveManageActivity.this,LeaveRequestActivity.class);
                startActivity(LeaveRequestIntent);
            }
        });

        mOvertimeRequest = (Button)findViewById(R.id.button_request_overtime);
        mOvertimeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OvertimeRequestIntent = new Intent(LeaveManageActivity.this,OvertimeRequestActivity.class);
                startActivity(OvertimeRequestIntent);
            }
        });

        leaveRecyclerView = (RecyclerView)findViewById(R.id.leave_recyclerView);
        leaveRecyclerView.setHasFixedSize(true);
        leaveRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        leaveData = new ArrayList<>();
        leaveRecyclerViewData();

        overtimeRecyclerView = (RecyclerView)findViewById(R.id.overtime_recyclerView);
        overtimeRecyclerView.setHasFixedSize(true);
        overtimeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        overtimeData = new ArrayList<>();
        overtimeRecyclerViewData();


    }

    public void leaveRecyclerViewData(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();
        int type = 1;

        Call<LeaveManageResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .leaveList(user_id, type);

        call.enqueue(new Callback<LeaveManageResponse>() {
            @Override
            public void onResponse(Call<LeaveManageResponse> call, Response<LeaveManageResponse> response) {
                LeaveManageResponse leaveManageResponse = response.body();
                if(leaveManageResponse.isStatus()){
                    leaveAdapter = new LeaveManageAdapter(leaveManageResponse.getData(), getApplicationContext());
                    leaveRecyclerView.setAdapter(leaveAdapter);
                }else{
                    Toast.makeText(LeaveManageActivity.this, leaveManageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LeaveManageResponse> call, Throwable t) {

            }
        });
    }

    public void overtimeRecyclerViewData(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();
        int type = 2;

        Call<OvertimeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .overtimeList(user_id, type);

        call.enqueue(new Callback<OvertimeResponse>() {
            @Override
            public void onResponse(Call<OvertimeResponse> call, Response<OvertimeResponse> response) {
                OvertimeResponse overtimeResponse = response.body();
                if(overtimeResponse.isStatus()){
                    overtimeAdapter = new OvertimeAdapter(overtimeResponse.getData(), getApplicationContext());
                    overtimeRecyclerView.setAdapter(overtimeAdapter);
                }else{
                    Toast.makeText(LeaveManageActivity.this, overtimeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OvertimeResponse> call, Throwable t) {

            }
        });
    }

}
