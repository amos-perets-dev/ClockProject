package com.example.giniappproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

    public String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");

        String localTime = date.format(currentLocalTime);
        return localTime;
    }

}
