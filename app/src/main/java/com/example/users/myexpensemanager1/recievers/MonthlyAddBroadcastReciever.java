package com.example.users.myexpensemanager1.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.users.myexpensemanager1.dao.MoneyDAO;
import com.example.users.myexpensemanager1.dao.RepetativeMoneyDAO;
import com.example.users.myexpensemanager1.models.MoneyItem;
import com.example.users.myexpensemanager1.utils.AlarmHandler;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by USER on 1/20/2018.
 */

public class MonthlyAddBroadcastReciever extends BroadcastReceiver{
    Context context;
    long timestamp;
    int pendingIntentUniqueId;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("EXPM_miscellaniousLogs", "this class is initiated by the way even after bootup");
        timestamp = intent.getLongExtra("autoadd_TS", 0);
        pendingIntentUniqueId = intent.getIntExtra("pendingIntentUniqueId", 0);
        this.context = context;
        Log.d("EXPM_AutoAdder","the reached out timestamp = "+timestamp);
        Toast.makeText(context, "on recieve function called ", Toast.LENGTH_SHORT).show();
        updateTupple(timestamp);
    }

    private void updateTupple(long timestamp) {
        if(RepetativeMoneyDAO.initialiser(context).ifTuppleExists(timestamp)){
            MoneyItem moneyItem = RepetativeMoneyDAO.initialiser(context).getEarningByTimestamp(timestamp);
            RepetativeMoneyDAO.initialiser(context).deleteMoneyByTimeStamp(timestamp);
            MoneyDAO.initialiser(context).insertMoney(moneyItem);
            Log.d("EXPM_AutoAdder","money is deleted safely and moneyAmount = "+moneyItem.getAmount());
            moneyItem.setTimestamp(alterItem(moneyItem.getTimestamp()));
            RepetativeMoneyDAO.initialiser(context).insertMoney(moneyItem);
            Log.d("EXPM_AutoAdder","money is deleted safely and moneyAmount = "+moneyItem.getAmount());
            AlarmHandler.addMoney(moneyItem, context, pendingIntentUniqueId);
        }
    }

    private long alterItem(long timeStamp){
        Date d = new Date(timeStamp);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, 1);
        Long newtimeStamp = c.getTimeInMillis();
        Log.d("EXPM_AutoAdder","timestamp altered for modification");
        return newtimeStamp;
    }

}
