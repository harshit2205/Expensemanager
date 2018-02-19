package com.example.users.myexpensemanager1.Dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by USER on 2/20/2018.
 */

public class BaseDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        View.OnClickListener {
    Calendar now = Calendar.getInstance();
    int CURRENT_YEAR, CURRENT_DATE, CURRENT_MONTH, CURREN_HRS, CURRENT_MINS, CURRENT_SEC;

    public BaseDialog() {
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

    public DatePickerDialog datePicker() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        return dpd;
    }

    public TimePickerDialog timePicker() {
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );
        return tpd;
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
}
