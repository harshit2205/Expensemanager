package com.example.users.myexpensemanager1.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ConfirmationDailogFrag extends DialogFragment{

    static String message;
    static String description;
    static int INTENT;
    static RecyclerView.Adapter historyAdapter;
    static int Id;
    public static int TRANSACTION_DELETION = 1;
    public static int MONEY_DELETION = 2;
    public static int ALARM_DELETION = 3;


    public static ConfirmationDailogFrag getConfirmationFrag(int id, int INTENTION
            , RecyclerView.Adapter adapter){
        INTENT = INTENTION;
        historyAdapter = adapter;
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
                .setTitle(message)
                .setMessage(description)
                .create();
    }
}