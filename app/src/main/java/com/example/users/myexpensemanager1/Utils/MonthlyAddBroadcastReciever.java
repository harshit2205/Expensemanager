package com.example.users.myexpensemanager1.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

    static MoneyItem moneyItem;
    Context context;

    public static void setMoneyItem(MoneyItem moneyItem){
        MonthlyAddBroadcastReciever.moneyItem = moneyItem;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.d("EXPM_AutoAdder","onrecieve function called for auto add");
        updateTupple(moneyItem.getTimestamp());
    }

    private void updateTupple(Long timeStamp) {
        if(RepetativeMoneyDAO.initialiser(context).ifTuppleExists(timeStamp)){
            RepetativeMoneyDAO.initialiser(context).deleteMoneyByTimeStamp(timeStamp);
            MoneyDAO.initialiser(context).insertMoney(moneyItem);
            Log.d("EXPM_AutoAdder","money is deleted safely");
            alterItem(moneyItem.getTimestamp());
            RepetativeMoneyDAO.initialiser(context).insertMoney(moneyItem);
            MoneyItem newitem = RepetativeMoneyDAO.initialiser(context).getRecentEarning();
            AlarmHandler.addMoney(newitem, context);
        }
    }

    private void alterItem(long timeStamp){
        Date d = new Date(timeStamp);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, 1);
        Long newtimeStamp = c.getTimeInMillis();
        moneyItem.setTimestamp(newtimeStamp);
        Log.d("EXPM_AutoAdder","timestamp altered for modification");
    }
}
