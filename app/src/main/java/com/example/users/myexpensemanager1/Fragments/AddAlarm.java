package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.users.myexpensemanager1.Activities.Main2Activity;
import com.example.users.myexpensemanager1.Dao.AlarmsDAO;
import com.example.users.myexpensemanager1.Models.AlarmItem;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Utils.AlarmHandler;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAlarm extends BaseFragment{
    Button date;
    Button time;
    Button setReminder;
    EditText itemname, description;

    public AddAlarm() {
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
        View v =  inflater.inflate(R.layout.fragment_add_reminder, container, false);
        date = (Button) v.findViewById(R.id.date_input);
        date.setOnClickListener(this);
        time = (Button)v.findViewById(R.id.time_input);
        time.setOnClickListener(this);
        setReminder = (Button)v.findViewById(R.id.add_Reminder);
        setReminder.setOnClickListener(this);
        description = (EditText)v.findViewById(R.id.item_description);
        itemname = (EditText)v.findViewById(R.id.transaction_item_name);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_input:
                datePicker();
                break;
            case R.id.time_input:
                timePicker();
                break;
            case R.id.add_Reminder:
                if(inputcheck()){
                    addAlarm();
                   }
                else{
                    Snackbar.make(getView(),"unfilled columns",Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        CURRENT_YEAR = year;
        CURRENT_MONTH = monthOfYear;
        CURRENT_DATE = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time.setText(hourOfDay+":"+minute);
        CURREN_HRS = hourOfDay;
        CURRENT_MINS = minute;
        CURRENT_SEC = second;
    }

    public void addAlarm(){
        now.set(CURRENT_YEAR,CURRENT_MONTH,CURRENT_DATE,CURREN_HRS,CURRENT_MINS,CURRENT_SEC);
        AlarmItem alarmItem = new AlarmItem(Main2Activity.userName,
                itemname.getText().toString(),
                now.getTimeInMillis(),
                description.getText().toString(),
                System.currentTimeMillis());

        //datapass check.....
        Log.d("EXPM","data of alarm item username = " +Main2Activity.userName+
                " item name = " + itemname.getText().toString()+
                " timestamp = " + now.getTimeInMillis()+
                " description = " + description.getText().toString()+
                " uniquestamp = " +System.currentTimeMillis()
        );

        //adding alarm to the alarm database.....
        AlarmsDAO.initialiser(getActivity().getApplicationContext()).insertAlarm(alarmItem);
        //calling remainder notification adder function.......
        AlarmHandler.initialiser().addAlarm(alarmItem,getActivity().getApplicationContext());
        hideKeyboard();
        Snackbar.make(this.getView(),"Transaction Remainder added",Snackbar.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }



    public boolean inputcheck(){
        if(itemname.getText().toString().equals("") || description.getText().toString().equals("")){
            return false;
        }
        return true;
    }


}
