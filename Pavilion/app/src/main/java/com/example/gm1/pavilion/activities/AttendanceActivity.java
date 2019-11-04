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
import com.example.gm1.pavilion.adapter.ListAdapter;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.models.AttendanceList;
import com.example.gm1.pavilion.models.AttendanceResponse;
import com.example.gm1.pavilion.models.User;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<AttendanceList> data;
    Button mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        mHome = (Button)findViewById(R.id.button_home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(AttendanceActivity.this,HomeActivity.class);
                startActivity(HomeIntent);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();

        loadRecyclerViewData();

        }

        private void loadRecyclerViewData(){
            User user = SharedPrefManager.getInstance(this).getData();
            int user_id = user.getId();

            Call<AttendanceResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .timingList(user_id);

            call.enqueue(new Callback<AttendanceResponse>() {
                @Override
                public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {

                    AttendanceResponse attendanceResponse = response.body();

                    if(attendanceResponse.isStatus()){

                        JSONObject jsonObject = new JSONObject();

                        try {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                AttendanceList timingList = new AttendanceList(
                                        o.getString("date"),
                                        o.getString("entry_time"),
                                        o.getString("exit_time")
                                );
                                data.add(timingList);
                            }

                            adapter = new ListAdapter(data, getApplicationContext());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(AttendanceActivity.this, attendanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {

                }
            });
        }

    }



