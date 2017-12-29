package com.example.users.myexpensemanager1.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.users.myexpensemanager1.Activities.Main2Activity;
import com.example.users.myexpensemanager1.R;


public class MyBroadcastReciever extends BroadcastReceiver {

    static String itemname,description;
    public static void getitem(String item, String desc){
        itemname = item;
        description = desc;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notifier(0);
    }

    //notifies the console that data has been recieved.....
    public void notifier(int requestCode){
        Log.d("EXPM", "request code = " +requestCode);
    }

    //broadcast reciever in order to create .....
    public void notificationBuilder(Context context){

        Intent i= new Intent(context, Main2Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,i,PendingIntent.FLAG_ONE_SHOT);
        android.support.v7.app.NotificationCompat.Builder builder= (android.support.v7.app.NotificationCompat.Builder)
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo_app1)
                .setContentTitle("New Transaction Today ")
                .setContentText(itemname)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(true);


        NotificationManager notifier;
        int notificationid = 001;
        notifier=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notifier.notify(notificationid,builder.build());
    }
}
