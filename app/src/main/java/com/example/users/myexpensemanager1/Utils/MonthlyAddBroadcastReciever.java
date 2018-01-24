package com.example.users.myexpensemanager1.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Dao.MoneyDAO;
import com.example.users.myexpensemanager1.Dao.RepetativeMoneyDAO;
import com.example.users.myexpensemanager1.Models.MoneyItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 1/20/2018.
 */

public class MonthlyAddBroadcastReciever extends BroadcastReceiver{
    Context context;
    long timestamp;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        timestamp = pref.getLong("autoadd_TS", 0);
        Log.d("EXPM_AutoAdder","the reached out timestamp = "+timestamp);
        Toast.makeText(context, "on recieve function called ", Toast.LENGTH_SHORT).show();
        updateTupple(timestamp);
    }

    private void updateTupple(long timestamp) {
        if(RepetativeMoneyDAO.initialiser(context).ifTuppleExists(timestamp)){
            MoneyItem moneyItem = RepetativeMoneyDAO.initialiser(context).getEarningByTimestamp(timestamp);
            RepetativeMoneyDAO.initialiser(context).deleteMoneyByTimeStamp(timestamp);
            MoneyDAO.initialiser(context).insertMoney(moneyItem);
            Log.d("EXPM_AutoAdder","money is deleted safely");
            moneyItem.setTimestamp(alterItem(moneyItem.getTimestamp()));
            RepetativeMoneyDAO.initialiser(context).insertMoney(moneyItem);
            AlarmHandler.addMoney(moneyItem, context);
        }else{
            Toast.makeText(context, " alarm already deleted", Toast.LENGTH_SHORT).show();
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
