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
public class MoneyAddMethodFrag extends BaseFragment{

    Button manualadd;
    Button autoadd;

    public MoneyAddMethodFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_money_add_method, container, false);
        manualadd = (Button)v.findViewById(R.id.manually_add_money);
        manualadd.setOnClickListener(this);
        autoadd = (Button)v.findViewById(R.id.auto_add_money);
        autoadd.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.manually_add_money:
                AddMoneyManual addMoneyManual = new AddMoneyManual();
                fragmentstarter(addMoneyManual);
                break;
            case R.id.auto_add_money:
                AddMoneyAuto addMoneyAuto = new AddMoneyAuto();
                fragmentstarter(addMoneyAuto);
        }
    }
}
