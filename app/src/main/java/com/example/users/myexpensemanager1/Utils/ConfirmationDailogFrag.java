package com.example.users.myexpensemanager1.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.users.myexpensemanager1.Adapters.MoneyHistoryAdapter;
import com.example.users.myexpensemanager1.Adapters.RemainderHistoryAdapter;
import com.example.users.myexpensemanager1.Adapters.TransactionHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.MoneyDAO;
import com.example.users.myexpensemanager1.Dao.RemaindersDAO;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;

public class ConfirmationDailogFrag extends DialogFragment{

    static String message;
    static String description;
    static int INTENT;
    static RecyclerView.Adapter historyAdapter;
    static int Id;
    public static int TRANSACTION_DELETION = 1;
    public static int MONEY_DELETION = 2;
    public static int REMAINDER_DELETION = 3;


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
            message = "Do you really want to delete this earning?";
            description = "deleting this income will erase" +
                    " all the related information of this earning from your phone";
        }else if(INTENTION == REMAINDER_DELETION){
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
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(INTENT == TRANSACTION_DELETION){
                        TransactionDAO.initialiser(getActivity().getApplicationContext()).deleteTransaction(Id);
                        TransactionHistoryAdapter adapter = (TransactionHistoryAdapter)historyAdapter;
                        adapter.itemSetChanged();}
                        if(INTENT == MONEY_DELETION) {
                            MoneyDAO.initialiser(getActivity().getApplicationContext()).deleteMoney(Id);
                            MoneyHistoryAdapter adapter = (MoneyHistoryAdapter) historyAdapter;
                            adapter.itemSetChanged();}
                        if(INTENT == REMAINDER_DELETION){
                            RemaindersDAO.initialiser(getActivity().getApplicationContext()).deleteRemainder(Id);
                            RemainderHistoryAdapter adapter = (RemainderHistoryAdapter)historyAdapter;
                            adapter.itemSetChanged();}
                        onDestroy();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDestroy();
                    }
                })
                .create();
    }
}