package com.example.gm1.pavilion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gm1.pavilion.R;

public class LeaveManageActivity extends AppCompatActivity {
    Button mHome;
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
    }
}
