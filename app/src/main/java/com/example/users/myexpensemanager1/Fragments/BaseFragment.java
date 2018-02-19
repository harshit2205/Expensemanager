package com.example.users.myexpensemanager1.Fragments;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class BaseFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        View.OnClickListener{
    Calendar now = Calendar.getInstance();
    int CURRENT_YEAR,CURRENT_DATE,CURRENT_MONTH,CURREN_HRS,CURRENT_MINS,CURRENT_SEC;

    public BaseFragment() {
        // Required empty public constructor...
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    protected void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getView() != null) {
            imm.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
        }
    }

    public void datePicker() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "date_picker_dialog");
    }

    public void timePicker(){
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );
        tpd.show(getFragmentManager(), "time_picker_dialog");
    }
}
