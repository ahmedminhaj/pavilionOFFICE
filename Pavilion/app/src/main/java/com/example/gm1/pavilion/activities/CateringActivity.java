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
import com.example.gm1.pavilion.adapter.CateringAdapter;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.models.list.CateringList;
import com.example.gm1.pavilion.models.response.CateringResponse;
import com.example.gm1.pavilion.models.User;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CateringActivity extends AppCompatActivity {
    RecyclerView cateringRecyclerView;
    RecyclerView.Adapter adapter;
    List<CateringList> data;
    Button mPlaceOrder;
    Button mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering);

        mHome = (Button)findViewById(R.id.button_home);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(CateringActivity.this,HomeActivity.class);
                startActivity(HomeIntent);
            }
        });

        mPlaceOrder = (Button)findViewById(R.id.button_place_order);
        mPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OrderIntent = new Intent(CateringActivity.this,MealOrderActivity.class);
                startActivity(OrderIntent);
            }
        });

        cateringRecyclerView = (RecyclerView)findViewById(R.id.catering_recyclerView);
        cateringRecyclerView.setHasFixedSize(true);
        cateringRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        cateringRecyclerViewData();

//        for(int i =0; i<=10; i++){
//            CateringList cateringList = new CateringList(
//                    "id" + (i + 1),
//                    "date" + (i),
//                    "day"
//            );
//
//            data.add(cateringList);
//        }
//        adapter = new CateringAdapter(data, this);
//        cateringRecyclerView.setAdapter(adapter);
    }

    private void cateringRecyclerViewData(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();

        Call<CateringResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .cateringList(user_id);

        call.enqueue(new Callback<CateringResponse>() {
            @Override
            public void onResponse(Call<CateringResponse> call, Response<CateringResponse> response) {
                CateringResponse cateringResponse = response.body();
                if(cateringResponse.isStatus()){
                    adapter = new CateringAdapter(cateringResponse.getData(), getApplicationContext());
                    cateringRecyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(CateringActivity.this, cateringResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CateringResponse> call, Throwable t) {

            }
        });
    }
}
