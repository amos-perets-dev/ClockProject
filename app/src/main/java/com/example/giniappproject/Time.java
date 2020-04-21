package com.example.giniappproject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Time extends RealmObject {

    @PrimaryKey
    private String id;

    private String time;

    public Time() {
    }

    public Time(String time) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
