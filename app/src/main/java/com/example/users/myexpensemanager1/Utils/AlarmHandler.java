package com.example.users.myexpensemanager1.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.users.myexpensemanager1.Models.AlarmItem;

public class AlarmHandler {

    private static AlarmHandler alarmHandler;

    private AlarmHandler(){}

    public static AlarmHandler initialiser(){
        if(alarmHandler == null){
            alarmHandler = new AlarmHandler();
        }
        return alarmHandler;
    }

    public void addAlarm(AlarmItem alarmItem, Context context){
        Log.d("EXPM","notifier added");
        MyBroadcastReciever.getitem(alarmItem.getItemName(),alarmItem.getDescription());
        Intent intent = new Intent(context, MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);


        long alarmTime = alarmItem.getTimestamp();
        if (Build.VERSION.SDK_INT < 19) {
            am.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);}
    }

}