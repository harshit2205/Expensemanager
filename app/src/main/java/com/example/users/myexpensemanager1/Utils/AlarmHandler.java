package com.example.users.myexpensemanager1.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.users.myexpensemanager1.Models.MoneyItem;
import com.example.users.myexpensemanager1.Models.RemainderItem;

import java.util.Calendar;
import java.util.Date;

public class AlarmHandler {

    private static AlarmHandler alarmHandler;

    private AlarmHandler(){}

    public static AlarmHandler initialiser(){
        if(alarmHandler == null){
            alarmHandler = new AlarmHandler();
        }
        return alarmHandler;
    }

    public void addAlarm(RemainderItem remainderItem, Context context){
        Log.d("EXPM","notifier added");
        MyBroadcastReciever.getitem(remainderItem.getItemName(), remainderItem.getDescription());
        Intent intent = new Intent(context, MyBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);


        long alarmTime = remainderItem.getTimestamp();
        if (Build.VERSION.SDK_INT < 19) {
            am.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);}
    }


    public static void addMoney(MoneyItem moneyItem, Context context){
        MonthlyAddBroadcastReciever.setMoneyItem(moneyItem);
        Intent intent = new Intent(context, MonthlyAddBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
        Log.d("EXPM_AutoAdder","alarm service is set for money item amount = "+ moneyItem.getTimestamp());

        long alarmTime = moneyItem.getTimestamp();
        if (Build.VERSION.SDK_INT < 19) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);}
    }

}