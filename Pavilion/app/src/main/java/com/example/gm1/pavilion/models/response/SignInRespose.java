package com.example.gm1.pavilion.models.response;

import com.example.gm1.pavilion.models.User;

public class SignInRespose {
    private boolean status;
    private String message;
    private User data;

    public SignInRespose(boolean status, String message, User data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
