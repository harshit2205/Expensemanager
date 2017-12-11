 package com.example.users.myexpensemanager1.Fragments;


 import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.users.myexpensemanager1.Activities.MainActivity;
 import com.example.users.myexpensemanager1.Dao.MoneyDAO;
 import com.example.users.myexpensemanager1.Models.MoneyItem;
import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


 /**
  * A simple {@link Fragment} subclass.
  */
 public class AddMoneyManual extends BaseFragment{
     Button date;
     Button time;
     EditText amount,description;
     Button addMoney;

     public AddMoneyManual() {
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
         View v =inflater.inflate(R.layout.fragment_add_money_manual, container, false);
         date = (Button) v.findViewById(R.id.date_input);
         date.setOnClickListener(this);
         time = (Button)v.findViewById(R.id.time_input);
         time.setOnClickListener(this);
         addMoney = (Button)v.findViewById(R.id.add_money);
         addMoney.setOnClickListener(this);
         amount = (EditText)v.findViewById(R.id.money_amount);
         description = (EditText)v.findViewById(R.id.money_description);
         return v;
     }



     @Override
     public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
         CURRENT_DATE = dayOfMonth;
         CURRENT_MONTH = monthOfYear;
         CURRENT_YEAR = year;
     }

     @Override
     public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
         time.setText(hourOfDay+":"+minute);
         CURREN_HRS = hourOfDay;
         CURRENT_MINS = minute;
         CURRENT_SEC = second;
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
             case R.id.add_money:
                 if(inputcheck()) addMoney();
                 else{
                     Snackbar.make(this.getView(),"unfilled columns",Snackbar.LENGTH_SHORT).show();
                 }
                 break;
         }
     }

     public void addMoney(){
         now.set(CURRENT_YEAR,CURRENT_MONTH,CURRENT_DATE,CURREN_HRS,CURRENT_MINS,CURRENT_SEC);
         MoneyItem moneyItem = new MoneyItem(
                 MainActivity.userName,
                 Long.parseLong(amount.getText().toString()),
                 now.getTimeInMillis(),
                 description.getText().toString());

         MoneyDAO moneyDAO = MoneyDAO.initialiser(getActivity().getApplicationContext());
         moneyDAO.insertMoney(moneyItem);

         hideKeyboard();
         Snackbar.make(this.getView(),"Money added",Snackbar.LENGTH_SHORT).show();
         getActivity().onBackPressed();
     }

     public boolean inputcheck(){
         if(amount.getText().toString().equals("")){
             return false;
         }
         return true;
     }

 }
