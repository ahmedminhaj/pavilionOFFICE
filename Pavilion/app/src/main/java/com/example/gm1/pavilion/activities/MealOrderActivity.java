package com.example.gm1.pavilion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

public class MealOrderActivity extends AppCompatActivity {

    private static  final String TAG = "MealOrderActivity";
    Button mCatering;
    Button mOrder;
    TextView mDatePicker;
    EditText mComment;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_order);

        mComment = (EditText)findViewById(R.id.foodRemark);

        mCatering = (Button)findViewById(R.id.button_catering_redirect);
        mCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(MealOrderActivity.this,CateringActivity.class);
                startActivity(HomeIntent);
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
                        MealOrderActivity.this,
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

        mOrder = (Button)findViewById(R.id.orderMeal);
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrderNow();
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

    private void saveOrderNow(){
        User user = SharedPrefManager.getInstance(this).getData();
        int user_id = user.getId();
        String comment = mComment.getText().toString().trim();
        String date = mDatePicker.getText().toString().trim();

        if(date.isEmpty()){
            mDatePicker.setError("Date required");
            mDatePicker.requestFocus();
            return;
        }
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .saveOrder(user_id, date, comment);

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

                            Toast.makeText(MealOrderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            Intent cateringIntent = new Intent(MealOrderActivity.this, CateringActivity.class);
                            startActivity(cateringIntent);

                        }
                        else{
                            Toast.makeText(MealOrderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
