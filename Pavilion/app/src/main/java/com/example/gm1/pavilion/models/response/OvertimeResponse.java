package com.example.gm1.pavilion.models.response;

import com.example.gm1.pavilion.models.list.OvertimeList;

import java.util.List;

public class OvertimeResponse {
    boolean status;
    String message;
    List<OvertimeList> data;

    public OvertimeResponse(boolean status, String message, List<OvertimeList> data) {
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

    public List<OvertimeList> getData() {
        return data;
    }
}
