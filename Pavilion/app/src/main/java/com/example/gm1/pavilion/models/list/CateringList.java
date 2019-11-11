package com.example.gm1.pavilion.models.list;

public class CateringList {
    int id;
    String date , day;

    public CateringList(int id, String date, String day) {
        this.id = id;
        this.date = date;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }
}
