package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.users.myexpensemanager1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectHistoryFrag extends BaseFragment {
    Button moneyhistory;
    Button transactionhistory;
    Button allHistory;
    Button allRemainders;


    public SelectHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_whoeshistory, container, false);
        moneyhistory = (Button) v.findViewById(R.id.view_money_history);
        moneyhistory.setOnClickListener(this);
        transactionhistory = (Button)v.findViewById(R.id.view_transaction_history);
        transactionhistory.setOnClickListener(this);
        allRemainders = (Button)v.findViewById(R.id.view_Remainders);
        allRemainders.setOnClickListener(this);
        allHistory = (Button)v.findViewById(R.id.all_history);
        allHistory.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.view_money_history:
                MoneyHistoryFrag moneyHistoryFrag = new MoneyHistoryFrag();
                fragmentstarter(moneyHistoryFrag);
                break;
            case R.id.view_transaction_history:
                TransactionHistoryFrag transactionHistoryFrag = new TransactionHistoryFrag();
                fragmentstarter(transactionHistoryFrag);
                break;
            case R.id.view_Remainders:
                RemainderHistoryFrag remainderHistoryFrag = new RemainderHistoryFrag();
                fragmentstarter(remainderHistoryFrag);
                break;
            case R.id.view_history:
                break;
        }
    }
}
