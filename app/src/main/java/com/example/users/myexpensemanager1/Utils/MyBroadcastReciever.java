package com.example.users.myexpensemanager1.Utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.example.users.myexpensemanager1.Activities.Main2Activity;
import com.example.users.myexpensemanager1.R;

import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;
import br.com.goncalves.pugnotification.notification.Load;
import br.com.goncalves.pugnotification.notification.PugNotification;


public class MyBroadcastReciever extends BroadcastReceiver implements ImageLoader{

    static String itemname,description;
    public static void getitem(String item, String desc){
        itemname = item;
        description = desc;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notifier(0);
        notificationBuilder(context);
    }

    //notifies the console that data has been recieved.....
    public void notifier(int requestCode){
        Log.d("EXPM_Remainder", "request code = " +requestCode);
    }

    //broadcast reciever in order to create .....
    public void notificationBuilder(Context context){

        Intent i= new Intent(context, Main2Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,i,PendingIntent.FLAG_ONE_SHOT);
//        android.support.v7.app.NotificationCompat.Builder builder= (android.support.v7.app.NotificationCompat.Builder)
//                new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.logo_app1)
//                .setContentTitle("New Transaction Today ")
//                .setContentText(itemname)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setOngoing(true);

//
//        NotificationManager notifier;
//        int notificationid = 001;
//        notifier=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
//        notifier.notify(notificationid,builder.build());

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Load load = PugNotification.with(context)
                .load();
        load.ongoing(false);
        load.title("Expense Manager")
                .message("remainder")
                .bigTextStyle("new remainder")
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .color(R.color.colorPrimary)
                .button(R.drawable.ic_done_black_24dp, "Task Done", pendingIntent)
                .dismiss(pendingIntent)
                .ticker("Expense Manager")
                .sound(uri)
                .simple()
                .build();
    }

    @Override
    public void load(String uri, OnImageLoadingCompleted onCompleted) {

    }

    @Override
    public void load(int imageResId, OnImageLoadingCompleted onCompleted) {

    }
}
