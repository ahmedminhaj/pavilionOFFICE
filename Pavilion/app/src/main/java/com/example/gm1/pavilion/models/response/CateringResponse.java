package com.example.gm1.pavilion.models.response;

import com.example.gm1.pavilion.models.list.CateringList;

import java.util.List;

public class CateringResponse {
    private  int id;
    private boolean status;
    private String message;
    private List<CateringList> data;

    public CateringResponse(int id, boolean status, String message, List<CateringList> data ) {
        this.id = id;
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

    public List<CateringList> getData() {
        return data;
    }

    public int getId() {
        return id;
    }
}
