package com.example.gm1.pavilion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.models.User;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OvertimeRequestActivity extends AppCompatActivity {

    Button mLeaveRedirect;
    TextView mDatePicker;
    Button mRequestNow;
    EditText mOvertimeCause;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_request);

        mOvertimeCause = (EditText)findViewById(R.id.overtime_cause);

        mLeaveRedirect = (Button)findViewById(R.id.button_leave_redirect);
        mLeaveRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeaveManagementIntent = new Intent(OvertimeRequestActivity.this,LeaveManageActivity.class);
                startActivity(LeaveManagementIntent);
            }
        });

        mDatePicker = (TextView) findViewById(R.id.pickDate);
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        OvertimeRequestActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //month = month + 1;
                String date = day + "-" + getMonthShortName(month) + "-" + year;
                mDatePicker.setText(date);
            }
        };

        mRequestNow = (Button)findViewById(R.id.request_now);
        mRequestNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overtimeRequestNow();
            }
        });
    }

    public static String getMonthShortName(int monthNumber)
    {
        String monthName="";

        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            }
            catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }
        return monthName;
    }

    private void overtimeRequestNow(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();
        int type = 2;
        String date = mDatePicker.getText().toString().trim();
        String comment = mOvertimeCause.getText().toString().trim();

        if(date.isEmpty()){
            mDatePicker.setError("Date required");
            mDatePicker.requestFocus();
            return;
        }
        if(comment.isEmpty()){
            mOvertimeCause.setError("Overtime details require");
            mOvertimeCause.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .overtimeRequestNow(user_id, type, date, comment);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
                try{
                    if(response.code() == 200) {
                        s = response.body().string();
                    }
                    else{
                        s = response.errorBody().string();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
                if(s != null){
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if(jsonObject.getBoolean("status")) {

                            Toast.makeText(OvertimeRequestActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            Intent cateringIntent = new Intent(OvertimeRequestActivity.this, LeaveManageActivity.class);
                            startActivity(cateringIntent);

                        }
                        else{
                            Toast.makeText(OvertimeRequestActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
