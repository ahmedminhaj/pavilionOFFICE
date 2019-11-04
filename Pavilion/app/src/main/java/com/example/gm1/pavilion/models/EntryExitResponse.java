package com.example.gm1.pavilion.models;

public class EntryExitResponse {
    private boolean status;
    private String message;
    private String entry_time;
    private String exit_time;

    public EntryExitResponse(boolean status, String message, String entry_time, String exit_time){
        this.status = status;
        this.message = message;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
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
}
