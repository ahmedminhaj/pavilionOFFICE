package com.example.gm1.pavilion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gm1.pavilion.R;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextEmail;
    Button mButtonSignup;
    TextView mTextViewSignin;
    CheckBox mShowPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mTextUsername = (EditText)findViewById(R.id.edittext_name);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mShowPassword = (CheckBox)findViewById(R.id.show_password);
        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mButtonSignup = (Button)findViewById(R.id.button_singup);
        mTextViewSignin = (TextView)findViewById(R.id.textview_signin);

        //password visibility option
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //show password
                    mTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //hide password
                    mTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //redirect to login/sign in page
        mTextViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(signInIntent);
            }
        });
        //sign up page
        mButtonSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //user registration method
                userSignUp();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent homeIntent = new Intent(this,HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
        }
    }

    //new user registration validation method
    private void userSignUp() {
        String email = mTextEmail.getText().toString().trim();
        String password = mTextPassword.getText().toString().trim();
        String name = mTextUsername.getText().toString().trim();
        int role_id = 2;
        String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);


        if(name.isEmpty()){
            mTextUsername.setError("Name required");
            mTextUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            mTextEmail.setError("Email required");
            mTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mTextEmail.setError("Required vail email");
            mTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mTextPassword.setError("Password required");
            mTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            mTextPassword.setError("Password require 6 digit length");
            mTextPassword.requestFocus();
            return;
        }
        /* User registration using api call */
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(role_id, name, email, password, android_id);

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

                            Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            Intent HomeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(HomeIntent);

                        }
                        else{
                            Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


