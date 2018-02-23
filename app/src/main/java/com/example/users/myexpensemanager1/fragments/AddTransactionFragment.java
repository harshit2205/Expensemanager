package com.example.users.myexpensemanager1.fragments;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.users.myexpensemanager1.activities.Main2Activity;
import com.example.users.myexpensemanager1.charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.dao.TransactionDAO;
import com.example.users.myexpensemanager1.models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.File;
import java.util.Calendar;

import static com.example.users.myexpensemanager1.utils.GetDateTime.getDate;
import static com.example.users.myexpensemanager1.utils.GetDateTime.getTime;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AddTransactionFragment extends BaseFragment implements AdapterView.OnItemSelectedListener{
    Button date;
    Button time;
    Button addTransaction,takeSnap;
    EditText purpose, itemcost;
    File imageFile;
    String filePath = "";
    int itemId;
    TransactionItem item;
    boolean isEditing = false;
    BetterSpinner transactionType;
    private String[] TRANSACTION_TYPE = new String[]{
            "Food Expenses", "Movies Expenses", "Household Expenses", "Tax Expenses", "Other Expenses"
    };

    public AddTransactionFragment(boolean isEditing) {
        this.isEditing = isEditing;
        // Required empty public constructor
        CURRENT_YEAR = now.get(Calendar.YEAR);
        CURRENT_MONTH = now.get(Calendar.MONTH);
        CURRENT_DATE = now.get(Calendar.DATE);
        CURREN_HRS = now.get(Calendar.HOUR_OF_DAY);
        CURRENT_MINS = now.get(Calendar.MINUTE);
        CURRENT_SEC = now.get(Calendar.SECOND);
    }

    public TransactionItem getItem() {
        return item;
    }

    public void setItem(TransactionItem item) {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_transaction, container, false);

        date = (Button) v.findViewById(R.id.date_input);
        date.setTypeface(CombinedChartTransaction.mTfLight);
        date.setOnClickListener(this);
        time = (Button)v.findViewById(R.id.time_input);
        time.setTypeface(CombinedChartTransaction.mTfLight);
        time.setOnClickListener(this);
        purpose = (EditText)v.findViewById(R.id.item_name);
        itemcost = (EditText)v.findViewById(R.id.item_cost);
        itemcost.setTypeface(CombinedChartTransaction.mTfLight);
        addTransaction = (Button)v.findViewById(R.id.add_transaction);
        addTransaction.setOnClickListener(this);
        takeSnap = (Button)v.findViewById(R.id.take_snapshot);
        takeSnap.setOnClickListener(this);

        date.setText(getDate(now.getTimeInMillis()));
        time.setText(getTime(now.getTimeInMillis()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.dropdown_item_spinner, TRANSACTION_TYPE);
        transactionType = (BetterSpinner)
                v.findViewById(R.id.type_chooser);
        transactionType.setAdapter(adapter);
        if (isEditing) viewElementEditor();
        return v;
    }

    public void viewElementEditor() {
        purpose.setText(item.getItem_name());
        itemcost.setText(Long.toString(item.getAmount()));
        date.setText(getDate(item.getTimestamp()));
        time.setText(getTime(item.getTimestamp()));
        transactionType.setText(item.getTransactionType());
        now.setTimeInMillis(item.getTimestamp());
        itemId = item.getId();
        isEditing = true;

        CURRENT_YEAR = now.get(Calendar.YEAR);
        CURRENT_MONTH = now.get(Calendar.MONTH);
        CURRENT_DATE = now.get(Calendar.DATE);
        CURREN_HRS = now.get(Calendar.HOUR_OF_DAY);
        CURRENT_MINS = now.get(Calendar.MINUTE);
        CURRENT_SEC = now.get(Calendar.SECOND);
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
            case R.id.add_transaction:
                if(inputcheck()){
                    addTransaction();
                }
                else{
                    String message = "";
                    if(purpose.getText().toString().equals("")){
                        message = "Purpose not set";
                    }else if( itemcost.getText().toString().equals("")){
                        message = "Amount paid not set";
                    }else{
                        message = "Type of transaction not set";
                    }
                    Snackbar.make(this.getView(),message,Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.take_snapshot:
                takeSnapshot();
        }
    }


    public boolean inputcheck(){
        return !(purpose.getText().toString().equals("") || itemcost.getText().toString().equals("")
                || transactionType.getText().toString().equals(""));
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
                    purpose.getText().toString(),
                    Long.parseLong(itemcost.getText().toString()),
                    now.getTimeInMillis(),
                    "",
                    filePath,
                    transactionType.getText().toString());

        if (isEditing) {
            transactionItem.setId(itemId);
            TransactionDAO.initialiser(getActivity().getApplicationContext()).updateTransaction(transactionItem);
        } else {
            TransactionDAO transactionDAO = TransactionDAO.initialiser(getActivity().getApplicationContext());
            transactionDAO.insertTransaction(transactionItem);
        }
        hideKeyboard();
        Snackbar.make(this.getView(),"transaction added",Snackbar.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            filePath = imageFile.getAbsolutePath();
        }
    }

}
