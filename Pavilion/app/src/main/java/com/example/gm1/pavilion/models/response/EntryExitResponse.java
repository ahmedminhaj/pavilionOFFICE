package com.example.gm1.pavilion.models.response;

public class EntryExitResponse {
    private boolean status;
    private String message;
    private String entry_time;
    private String exit_time;
    private String today_meal;

    public EntryExitResponse(boolean status, String message, String entry_time, String exit_time, String today_meal){
        this.status = status;
        this.message = message;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
        this.today_meal = today_meal;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public String getToday_meal() {
        return today_meal;
    }
}
