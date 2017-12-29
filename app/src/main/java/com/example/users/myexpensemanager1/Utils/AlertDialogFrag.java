package com.example.users.myexpensemanager1.Utils;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

public class AlertDialogFrag extends DialogFragment {

    public TransactionItem item;
    TextView itemName, itemCost, date, description;
    ImageView view;

    public static AlertDialogFrag getAlertDialog(){
        return new AlertDialogFrag();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transaction_history_item_data,container,false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        itemName = (TextView)v.findViewById(R.id.item_name);
        itemCost = (TextView)v.findViewById(R.id.item_cost);
        date = (TextView)v.findViewById(R.id.date);
        description = (TextView)v.findViewById(R.id.item_description);
        view = (ImageView)v.findViewById(R.id.reciept_image);
        bindView();
        return v;
    }

    public void bindView(){
        itemName.setText("Item: "+item.getItem_name());
        itemCost.setText("Item cost: "+item.getAmount());
        date.setText("Dated: "+getDate(item.getTimestamp()));
        description.setText("Description: "+item.getDescription());
        view.setImageBitmap(getImage("/storage/emulated/0/app.myexpensemanager1/file1514478268041.jpg"));
    }

    private String getDate(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private Bitmap getImage(String filePath){
        File imgFile = new File("/storage/emulated/0/app.myexpensemanager1/file1514478268041.jpg");

        Bitmap myBitmap;

        if(imgFile.exists()){
           myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;

        }else{
            Toast.makeText(getActivity(),"the image can't be load, might have been tampered!!",Toast.LENGTH_SHORT).show();
            return null;
        }

    }
}