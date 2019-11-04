package com.example.gm1.pavilion.models;

public class AttendanceList {
    private String date, entry_time, exit_time;

    public AttendanceList( String date, String entry_time, String exit_time) {
        this.date = date;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
    }


    public String getDate() {
        return date;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public String getExit_time() {
        return exit_time;
    }
}
