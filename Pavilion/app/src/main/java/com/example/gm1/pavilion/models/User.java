package com.example.gm1.pavilion.models;

public class User {
    private int id, role_id;
    private String name, android_id;

    public User(int id, int role_id, String name, String android_id) {
        this.id = id;
        this.role_id = role_id;
        this.name = name;
        this.android_id = android_id;
    }

    public int getId() {
        return id;
    }

    public int getRole_id() {
        return role_id;
    }

    public String getName() {
        return name;
    }

    public String getAndroid_id() {
        return android_id;
    }
}
