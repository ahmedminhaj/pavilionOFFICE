package com.example.gm1.pavilion.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gm1.pavilion.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME =  "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx){this.mCtx=mCtx;}

    public static synchronized SharedPrefManager getInstance(Context mCtx){

        if(mInstance == null){
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User data){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", data.getId());
        editor.putString("name", data.getName());
        editor.putString("android_id", data.getAndroid_id());
        editor.putInt("role_id", data.getRole_id());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getData(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getInt("role_id", 2),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("android_id", null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
