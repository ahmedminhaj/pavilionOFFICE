package com.example.gm1.pavilion.models.list;

public class LeaveList {
    int id;
    String date, is_approved, created_at;

    public LeaveList(int id, String date, String is_approved, String created_at) {
        this.id = id;
        this.date = date;
        this.is_approved = is_approved;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public String getCreated_at() {
        return created_at;
    }
}
