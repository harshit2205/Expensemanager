package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.users.myexpensemanager1.Activities.MainActivity;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransactionFragment extends BaseFragment{
    Button date;
    Button time;
    Button snapshot;
    ImageView demoImage;
    Button addTransaction;
    EditText itemname, itemcost, description;

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
        date = (Button) v.findViewById(R.id.date_input);
        date.setOnClickListener(this);
        time = (Button)v.findViewById(R.id.time_input);
        time.setOnClickListener(this);
        snapshot = (Button)v.findViewById(R.id.snapshot);
        snapshot.setOnClickListener(this);
        itemname = (EditText)v.findViewById(R.id.item_name);
        itemcost = (EditText)v.findViewById(R.id.item_cost);
        description = (EditText)v.findViewById(R.id.item_description);
        addTransaction = (Button)v.findViewById(R.id.add_transaction);
        addTransaction.setOnClickListener(this);
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
            case R.id.snapshot:
                Log.d("datahex","screenshot button pressed");
                //takeScreenshot();
                break;
            case R.id.add_transaction:
                if(inputcheck()){
                    addTransaction();
                } //addTransaction();
                else{
                    Snackbar.make(this.getView(),"unfilled columns",Snackbar.LENGTH_SHORT).show();
                }
                break;
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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
            // image naming and path  to include sd card  appending name you choose for file
        File mydir = getActivity().getDir("users", Context.MODE_PRIVATE); //Creating an internal dir;
        if (!mydir.exists()) {
            mydir.mkdirs();
            Log.d("hexbozome","file created");
        }Log.d("hexbozome","path "+mydir.getPath());
            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
        String fname = "Image-"+ now +".jpg";
        File imagefile = new File (mydir, fname);
        try {
            FileOutputStream outputStream = new FileOutputStream(imagefile);
            int quality = 90;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().sendBroadcast(new Intent(
                Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        if(imagefile.exists())Log.d("hexbozome","file created");
        else {
            Log.d("EXPM","file not created");
        }
    }



    public boolean inputcheck(){
        if(itemname.getText().toString().equals("") || itemcost.getText().toString().equals("")){
            return false;
        }
                return true;
    }

    private void addTransaction(){
        now.set(CURRENT_YEAR,CURRENT_MONTH,CURRENT_DATE,CURREN_HRS,CURRENT_MINS,CURRENT_SEC);
        TransactionItem transactionItem = new TransactionItem(
                MainActivity.userName,
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


}
