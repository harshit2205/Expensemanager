package com.example.users.myexpensemanager1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddMoneyAuto extends BaseFragment{
    Button date;
    Button time;
    Button addMoneymonth;
    Button addMoneyyear;

    public AddMoneyAuto() {
        // Required empty public constructor
        CURRENT_YEAR = now.get(Calendar.YEAR);
        CURRENT_MONTH = now.get(Calendar.MONTH);
        CURRENT_DATE = now.get(Calendar.DATE);
        CURREN_HRS = now.get(Calendar.HOUR_OF_DAY);
        CURRENT_MINS = now.get(Calendar.MINUTE);
        CURRENT_SEC = now.get(Calendar.SECOND);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_money_auto, container, false);

        date = (Button) v.findViewById(R.id.date_input);
        date.setOnClickListener(this);
        time = (Button)v.findViewById(R.id.time_input);
        time.setOnClickListener(this);
        addMoneymonth = (Button)v.findViewById(R.id.add_money_month);
        addMoneymonth.setOnClickListener(this);
        addMoneyyear = (Button)v.findViewById(R.id.add_money_year);
        addMoneyyear.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_input:
                datePicker();
                break;
            case R.id.time_input:
                timePicker();;
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        CURRENT_DATE = dayOfMonth;
        CURRENT_MONTH = monthOfYear+1;
        CURRENT_YEAR = year;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time.setText(hourOfDay+":"+minute);
        CURREN_HRS = hourOfDay;
        CURRENT_MINS = minute;
        CURRENT_SEC = second;
    }



}
