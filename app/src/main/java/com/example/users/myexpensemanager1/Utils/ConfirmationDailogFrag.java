package com.example.users.myexpensemanager1.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Dao.AlarmsDAO;
import com.example.users.myexpensemanager1.Dao.MoneyDAO;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;

public class ConfirmationDailogFrag extends DialogFragment{

    static String message;
    static String description;
    static int INTENT;
    static int Id;
    public static int TRANSACTION_DELETION = 1;
    public static int MONEY_DELETION = 2;
    public static int ALARM_DELETION = 3;


    public static ConfirmationDailogFrag getConfirmationFrag(int id, int INTENTION){
        INTENT = INTENTION;
        Id = id;
        if(INTENTION == TRANSACTION_DELETION){
            message = "Do you really want to delete this transaction?";
            description = "deleting this transaction will erase" +
                    " all the related information of this transaction from your phone";
        }else if(INTENTION == MONEY_DELETION){
            message = "Do you really want to delete this income?";
            description = "deleting this income will erase" +
                    " all the related information of this transaction from your phone";
        }else if(INTENTION == ALARM_DELETION){
            message = "Do you really want to delete this reminder?";
            description = "deleting this reminder will erase" +
                    " all the related information of this reminder from your phone";
        }
        return new ConfirmationDailogFrag();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle(message)
                .setMessage(description)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Data deleted!!", Toast.LENGTH_SHORT).show();
                        if(INTENT == TRANSACTION_DELETION){
                            TransactionDAO.initialiser(getActivity().getApplicationContext()).deleteTransaction(Id);
                        }else if(INTENT == MONEY_DELETION){
                            MoneyDAO.initialiser(getActivity().getApplicationContext()).deleteMoney(Id);
                        }else if(INTENT == ALARM_DELETION){
                            AlarmsDAO.initialiser(getActivity().getApplicationContext()).deleteAlarm(Id);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }
}