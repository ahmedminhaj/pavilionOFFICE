package com.example.gm1.pavilion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.gm1.pavilion.models.response.SignInRespose;
import com.example.gm1.pavilion.api.RetrofitClient;
import com.example.gm1.pavilion.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonSignin;
    TextView mTextViewSignup;
    CheckBox mShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mShowPassword = (CheckBox)findViewById(R.id.show_password);
        mButtonSignin = (Button)findViewById(R.id.button_singin);
        mTextViewSignup = (TextView)findViewById(R.id.textview_singup);

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

        // redirect registration/sign up page
        mTextViewSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        // profile sign in operation
        mButtonSignin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /* Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(homeIntent); */
                userSingIn();
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

    //user login validation
    private void userSingIn() {
        String email = mTextEmail.getText().toString().trim();
        String password = mTextPassword.getText().toString().trim();
        int role_id = 2;

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

        Call<SignInRespose> call = RetrofitClient
                .getInstance()
                .getApi()
                .userSignIn(email, password, role_id);

        call.enqueue(new Callback<SignInRespose>() {
            @Override
            public void onResponse(Call<SignInRespose> call, Response<SignInRespose> response) {
                SignInRespose signInRespose = response.body();

                if(signInRespose.isStatus())
                {
                    // save the response from API in sharedPrefManager
                    SharedPrefManager.getInstance(MainActivity.this)
                            .saveUser(signInRespose.getData());

                    // check if android id is matched

                    // View the message from api server
                    Toast.makeText(MainActivity.this, signInRespose.getMessage(), Toast.LENGTH_SHORT).show();

                    // Start homeActivity to show the homepage
                    Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);

                }
                else{
                    //error message
                    Toast.makeText(MainActivity.this, signInRespose.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignInRespose> call, Throwable t) {

            }
        });
    }
}
