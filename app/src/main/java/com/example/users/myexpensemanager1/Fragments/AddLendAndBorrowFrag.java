package com.example.users.myexpensemanager1.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.Calendar;

public class AddLendAndBorrowFrag extends BaseFragment implements CompoundButton.OnCheckedChangeListener{

    Button date;
    Button time;
    CheckBox setEndDate, setRemainder;
    LinearLayout layout;
    BetterSpinner lendBorrowType;
    EditText name;

    public AddLendAndBorrowFrag() {
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
        View view = inflater.inflate(R.layout.fragment_add_lend_and_borrow, container, false);

        date = (Button) view.findViewById(R.id.date_input);
        date.setOnClickListener(this);
        time = (Button)view.findViewById(R.id.time_input);
        time.setOnClickListener(this);
        name = (EditText)view.findViewById(R.id.lender_borrower_name);
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

    private String[] LENDBORROWTYPE = new String[]{
            "Lending to","Borrowing from"};


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
        return !(name.getText().toString().equals("") || lendBorrowType.getText().toString().equals(""));
    }

    private void addLendOrBorrow(){

    }
}
