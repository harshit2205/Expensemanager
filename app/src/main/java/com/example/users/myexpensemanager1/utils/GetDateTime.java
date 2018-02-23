package com.example.users.myexpensemanager1.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by USER on 2/23/2018.
 */

public class GetDateTime {

    public static String getDate(long timeStamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp);
        return DateFormat.format("dd-MM-yyyy", cal).toString();
    }

    public static String getTime(long timeStamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp);
        String time = DateFormat.format("HH:mm", cal).toString();
        return time;
    }
}
