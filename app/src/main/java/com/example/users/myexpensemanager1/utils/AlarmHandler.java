package com.example.users.myexpensemanager1.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.users.myexpensemanager1.models.MoneyItem;
import com.example.users.myexpensemanager1.models.RemainderItem;
import com.example.users.myexpensemanager1.recievers.MonthlyAddBroadcastReciever;
import com.example.users.myexpensemanager1.recievers.MyBroadcastReciever;

public class AlarmHandler {

    private static AlarmHandler alarmHandler;

    private AlarmHandler(){}

    public static AlarmHandler initialiser(){
        if(alarmHandler == null){
            alarmHandler = new AlarmHandler();
        }
        return alarmHandler;
    }

    public static void addMoney(MoneyItem moneyItem, Context context, int pendingIntentUniqueId) {


        if (context == null) {
            Log.d("EXPM_AutoAdder", "context recieved");
        }
        Intent intent = new Intent(context, MonthlyAddBroadcastReciever.class);
        intent.putExtra("autoadd_TS", moneyItem.getTimestamp());
        intent.putExtra("pendingIntentUniqueId", pendingIntentUniqueId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pendingIntentUniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = null;
        assert context != null;
        alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        long alarmTime = moneyItem.getTimestamp();
        if (Build.VERSION.SDK_INT < 19) {
            assert alarmManager != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } else {
            assert alarmManager != null;
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        }
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
}