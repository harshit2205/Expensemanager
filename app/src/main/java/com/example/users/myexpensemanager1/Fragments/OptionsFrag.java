package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.users.myexpensemanager1.Activities.HistoryActivity;
import com.example.users.myexpensemanager1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFrag extends BaseFragment{
    Button addmoney;
    Button addTransaction;
    Button addReminder;
    Button history;

    public OptionsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_options, container, false);
        addmoney = (Button)v.findViewById(R.id.add_money);
        addmoney.setOnClickListener(this);
        addTransaction = (Button)v.findViewById(R.id.add_transaction);
        addTransaction.setOnClickListener(this);
        addReminder = (Button)v.findViewById(R.id.set_reminder);
        addReminder.setOnClickListener(this);
        history = (Button)v.findViewById(R.id.view_history);
        history.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_money:
                Log.d("datahex","case 1 encountered");
                MoneyAddMethodFrag frag = new MoneyAddMethodFrag();
                fragmentstarter(frag);
                break;
            case R.id.add_transaction:
                Log.d("datahex","case 2 encountered");
                AddTransaction addTransaction = new AddTransaction();
                fragmentstarter(addTransaction);
                break;
            case R.id.set_reminder:
                Log.d("datahex","case 3 encountered");
                AddAlarm addAlarm = new AddAlarm();
                fragmentstarter(addAlarm);
                break;
            case R.id.view_history:
                Log.d("datahex","show history" );
                Intent i = new Intent(getActivity(), HistoryActivity.class);
                startActivity(i);
                break;
        }
    }
}
