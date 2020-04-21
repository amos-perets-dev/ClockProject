package com.example.giniappproject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

    /**
     * Get the current time by "HH:mm"
     *
     * @return time
     */
    String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");

        return date.format(currentLocalTime);
    }

}
