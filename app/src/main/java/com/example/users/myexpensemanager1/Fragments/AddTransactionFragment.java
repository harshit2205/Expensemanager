package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Activities.Main2Activity;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.File;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransactionFragment extends BaseFragment implements AdapterView.OnItemSelectedListener{
    Button date;
    Button time;
    Button addTransaction,takeSnap;
    EditText itemname, itemcost, description;
    BetterSpinner spinner;
    File imageFile;

    public AddTransactionFragment() {
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
        View v =  inflater.inflate(R.layout.fragment_add_transaction, container, false);

        Main2Activity.menuitem.setVisible(false);
        date = (Button) v.findViewById(R.id.date_input);
        date.setOnClickListener(this);
        time = (Button)v.findViewById(R.id.time_input);
        time.setOnClickListener(this);
        itemname = (EditText)v.findViewById(R.id.item_name);
        itemcost = (EditText)v.findViewById(R.id.item_cost);
        description = (EditText)v.findViewById(R.id.item_description);
        addTransaction = (Button)v.findViewById(R.id.add_transaction);
        addTransaction.setOnClickListener(this);
        takeSnap = (Button)v.findViewById(R.id.take_snapshot);
        takeSnap.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.dropdown_item_spinner, COUNTRIES);
        BetterSpinner textView = (BetterSpinner)
                v.findViewById(R.id.type_chooser);
        textView.setAdapter(adapter);

        return v;
    }

    private String[] COUNTRIES = new String[] {
            "Food Expenses", "Movies Expenses", "Household Expenses", "Tax Expenses", "Other Expenses"
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_input:
                datePicker();
                break;
            case R.id.time_input:
                timePicker();
                break;
            case R.id.add_transaction:
                if(inputcheck()){
                    addTransaction();
                }
                else{
                    Snackbar.make(this.getView(),"unfilled columns",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.take_snapshot:
                takeSnapshot();
        }
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

    public boolean inputcheck(){
        if(itemname.getText().toString().equals("") || itemcost.getText().toString().equals("")){
            return false;
        }
                return true;
    }

    private void takeSnapshot(){
        imageFile = new File(Environment.getExternalStorageDirectory()+ "/app.myexpensemanager1", "file"+ System.currentTimeMillis() +".jpg");
        Intent i = new Intent();
        i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(i,0);
    }

    private void addTransaction(){
        now.set(CURRENT_YEAR,CURRENT_MONTH,CURRENT_DATE,CURREN_HRS,CURRENT_MINS,CURRENT_SEC);
            TransactionItem transactionItem = new TransactionItem(
                    Main2Activity.userName,
                    itemname.getText().toString(),
                    Long.parseLong(itemcost.getText().toString()),
                    now.getTimeInMillis(),
                    description.getText().toString());

            TransactionDAO transactionDAO = TransactionDAO.initialiser(getActivity().getApplicationContext());
            transactionDAO.insertTransaction(transactionItem);
        hideKeyboard();
        Snackbar.make(this.getView(),"transaction added",Snackbar.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Main2Activity.menuitem.setVisible(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = (String)parent.getItemAtPosition(position);
        Toast.makeText(parent.getContext(),"item is "+item,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 0 && imageFile.exists()){
            Log.d("EXPM_Snapshot","snap taken "+ imageFile.getAbsolutePath());
        }
    }
}
