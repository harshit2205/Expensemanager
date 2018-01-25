package com.example.users.myexpensemanager1.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.users.myexpensemanager1.Dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.Models.LendBorrowItem;
import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.Calendar;

public class AddLendAndBorrowFrag extends BaseFragment implements CompoundButton.OnCheckedChangeListener{

    Button date;
    Button time,addLendBorrow;
    CheckBox setEndDate, setRemainder;
    LinearLayout layout;
    BetterSpinner lendBorrowType;
    EditText name,moneyAmount,description;
    private String[] LENDBORROWTYPE = new String[]{
            "Lending to","Borrowing from"};


    public AddLendAndBorrowFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lend_and_borrow, container, false);

        date = (Button) view.findViewById(R.id.date_input);
        date.setOnClickListener(this);
        time = (Button)view.findViewById(R.id.time_input);
        time.setOnClickListener(this);
        name = (EditText)view.findViewById(R.id.lender_borrower_name);
        moneyAmount = (EditText) view.findViewById(R.id.money_amount);
        description = (EditText)view.findViewById(R.id.item_description);
        addLendBorrow = (Button)view.findViewById(R.id.add_lendorborrow);
        addLendBorrow.setOnClickListener(this);
        setEndDate = (CheckBox)view.findViewById(R.id.set_endate_checkbox);
        setEndDate.setOnCheckedChangeListener(this);
        layout = (LinearLayout)view.findViewById(R.id.date_time_layout);
        setRemainder = (CheckBox)view.findViewById(R.id.set_remainder_checkbox);
        setRemainder.setChecked(false);
        setEndDate.setChecked(false);
        lendBorrowType = (BetterSpinner)view.findViewById(R.id.type_chooser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.dropdown_item_spinner, LENDBORROWTYPE);
        lendBorrowType.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox box = (CheckBox)buttonView;
        if (box.getId() == R.id.set_endate_checkbox ){
            if(isChecked){
                layout.setVisibility(View.VISIBLE);
                setRemainder.setVisibility(View.VISIBLE);
            }
            else{
                layout.setVisibility(View.GONE);
                setRemainder.setVisibility(View.GONE);
            }
        }
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
            case R.id.add_lendorborrow:
                Log.d("EXPM_LendBorrow","lend/borrow add button clicked");
                if(inputcheck()){
                    addLendOrBorrow();
                }
                else{
                    Snackbar.make(this.getView(),"unfilled columns",Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
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

    public boolean inputcheck(){
        if(setEndDate.isChecked() &&
                (date.getText().toString().equals("") ||
                time.getText().toString().equals("") )) return false;
        else{
        return !(name.getText().toString().equals("") ||
                lendBorrowType.getText().toString().equals("") ||
                moneyAmount.getText().toString().equals("") );}
    }

    private void addLendOrBorrow(){
        now.set(CURRENT_YEAR,CURRENT_MONTH,CURRENT_DATE,CURREN_HRS,CURRENT_MINS,CURRENT_SEC);
        long amount =Long.parseLong(moneyAmount.getText().toString());
        int remainderSet = LendBorrowDAO.REMINDER_NOT_SET;
        long timeStamp = 0;
        Log.d("EXPM_LendBorrow","type = "+lendBorrowType.getText().toString());
        if(lendBorrowType.getText().toString().equals("Borrowing from")){
            amount = 0 - Long.parseLong(moneyAmount.getText().toString());
        Log.d("EXPM_LendBorrow","amount = "+ amount);}

        if(setEndDate.isChecked()){
            remainderSet = LendBorrowDAO.REMINDER_SET;
            timeStamp = now.getTimeInMillis();
            Log.d("EXPM_LendBorrow","timestamp of added money is "+now.getTimeInMillis());
        }

        String namealtered = name.getText().toString();
        LendBorrowItem lendBorrowItem = new LendBorrowItem(
                namealtered.replaceFirst("\\s++$",""),
                amount,
                description.getText().toString(),
                remainderSet,
                timeStamp
        );

        LendBorrowDAO lendBorrowDAO = LendBorrowDAO.initialiser(getActivity().getApplicationContext());
        if(lendBorrowDAO.searchOldItem(name.getText().toString())){
            lendBorrowDAO.updateitem(lendBorrowItem);
        }else {
            lendBorrowDAO.insertLendBorrowItem(lendBorrowItem);
        }
        hideKeyboard();
        Snackbar.make(this.getView(),"transaction added",Snackbar.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }
}
