package com.example.users.myexpensemanager1.Utils;

import android.content.Context;

import com.example.users.myexpensemanager1.R;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyAxisValueFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;
    private Context context;

    public MyAxisValueFormatter(Context context) {
        mFormat = new DecimalFormat("###,###,###,##0.0");
        this.context = context;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return context.getResources().getString(R.string.Rs)+" "+mFormat.format(value) ;
    }
}
