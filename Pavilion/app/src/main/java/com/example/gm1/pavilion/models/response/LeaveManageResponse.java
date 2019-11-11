package com.example.gm1.pavilion.models.response;

import com.example.gm1.pavilion.models.list.LeaveList;

import java.util.List;

public class LeaveManageResponse {
    boolean status;
    String message;
    List<LeaveList> data;

    public LeaveManageResponse(boolean status, String message, List<LeaveList> data) {
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

    public List<LeaveList> getData() {
        return data;
    }
}
