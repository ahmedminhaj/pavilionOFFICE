package com.example.gm1.pavilion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.models.response.EntryExitResponse;
import com.example.gm1.pavilion.models.User;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    TextView mUserName;
    Button mButtonLogIn;//entry time button
    Button mButtonLogOff;//exit time button
    Button mButtonSignOut;//account sign out
    Button mAttendance;
    Button mCatering;
    Button mLeaveManagement;
    TextView mTodayMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mUserName = findViewById(R.id.edittext_name);

        User user = SharedPrefManager.getInstance(this).getData(); //user data
        mUserName.setText(user.getName()); //show user name

        mAttendance = findViewById(R.id.attendance);
        mCatering = findViewById(R.id.catering);
        mLeaveManagement = findViewById(R.id.leaveManage);

        mButtonSignOut = findViewById(R.id.button_signOut);

        mButtonLogIn = findViewById(R.id.button_logIn);
        mButtonLogOff = findViewById(R.id.button_logOff);
        mButtonLogIn.setVisibility(View.GONE);
        mButtonLogOff.setVisibility(View.GONE);


        //entry and exit time checker
        timeChecker();


        //redirect to attendance list page
        mAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendanceIntent = new Intent(HomeActivity.this,AttendanceActivity.class);
                startActivity(attendanceIntent);
            }
        });

        //redirect to catering service
        mCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendanceIntent = new Intent(HomeActivity.this,CateringActivity.class);
                startActivity(attendanceIntent);
            }
        });

        //redirect to leave management
        mLeaveManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendanceIntent = new Intent(HomeActivity.this,LeaveManageActivity.class);
                startActivity(attendanceIntent);
            }
        });



        //account sign out
        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    //time checker method
    private void timeChecker(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();

        Call<EntryExitResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .timeChecker(user_id);

        call.enqueue(new Callback<EntryExitResponse>() {
            @Override
            public void onResponse(Call<EntryExitResponse> call, Response<EntryExitResponse> response) {
                EntryExitResponse timeCheckResponse = response.body();
                //entry time checker
                if(timeCheckResponse.isStatus()){

                    if(timeCheckResponse.getEntry_time() != null && timeCheckResponse.getExit_time() == null){
                        TextView textView = findViewById(R.id.login_time);
                        textView.setText(" Entry Time: " + timeCheckResponse.getEntry_time());
                        TextView textViewMeal = findViewById(R.id.today_meal);
                        textViewMeal.setText(" Today Meal: " + timeCheckResponse.getToday_meal());
                        //exit time button
                        mButtonLogOff.setVisibility(View.VISIBLE);
                        mButtonLogIn.setVisibility(View.GONE);
                        mButtonLogOff.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mButtonLogIn.setVisibility(View.GONE);
                                mButtonLogOff.setVisibility(View.GONE);

                                exitTime();
                            }
                        });
                    }else{
                        mButtonLogIn.setVisibility(View.GONE);
                        mButtonLogOff.setVisibility(View.GONE);
                        TextView textView = findViewById(R.id.login_time);
                        textView.setText(" Entry Time: " + timeCheckResponse.getEntry_time());
                        TextView textViewMeal = findViewById(R.id.today_meal);
                        textViewMeal.setText(" Today Meal: " + timeCheckResponse.getToday_meal());
                        TextView textViewExit = findViewById(R.id.logoff_time);
                        textViewExit.setText(" Exit Time: " + timeCheckResponse.getExit_time());
                    }
                }else{
                    //entry time button
                    //mButtonLogIn = findViewById(R.id.button_logIn);
                    mButtonLogIn.setVisibility(View.VISIBLE);
                    mButtonLogIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mButtonLogIn.setVisibility(View.GONE);
                            mButtonLogOff.setVisibility(View.VISIBLE);
                            /*
                            mButtonLogIn.postDelayed(new Runnable() {
                                public void run() {
                                    mButtonLogIn.setVisibility(View.VISIBLE);
                                }
                            }, 57600000);
                            */
                            entryTime();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<EntryExitResponse> call, Throwable t) {

            }
        });
    }

    //entry time method
    private void entryTime(){
//        mButtonLogIn.setVisibility(View.GONE);
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();

        Call<EntryExitResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .entryTime(user_id);

        call.enqueue(new Callback<EntryExitResponse>() {
            @Override
            public void onResponse(Call<EntryExitResponse> call, Response<EntryExitResponse> response) {
                EntryExitResponse entryExitResponse = response.body();
                if(entryExitResponse.isStatus()){

                    TextView textView = findViewById(R.id.login_time);
                    textView.setText(" Entry Time: " + entryExitResponse.getEntry_time());
                    TextView textViewMeal = findViewById(R.id.today_meal);
                    textViewMeal.setText(" Today Meal: " + entryExitResponse.getToday_meal());
                    Toast.makeText(HomeActivity.this, entryExitResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(HomeActivity.this, entryExitResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntryExitResponse> call, Throwable t) {
            }
        });
    }

    //exit time method
    private void exitTime(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();

        Call<EntryExitResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .exitTime(user_id);

        call.enqueue(new Callback<EntryExitResponse>() {
            @Override
            public void onResponse(Call<EntryExitResponse> call, Response<EntryExitResponse> response) {
                EntryExitResponse entryExitResponse = response.body();
                if(entryExitResponse.isStatus()){

                    TextView textView = findViewById(R.id.logoff_time);
                    textView.setText(" Exit Time: " + entryExitResponse.getExit_time());
                    Toast.makeText(HomeActivity.this, entryExitResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(HomeActivity.this, entryExitResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntryExitResponse> call, Throwable t) {

            }
        });
    }

    //account signOut method
    private void signOut(){
        SharedPrefManager.getInstance(this).clear();
        Intent homeIntent = new Intent(this,MainActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent homeIntent = new Intent(this,MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);

        }
    }
}
