package com.example.users.myexpensemanager1.charts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.example.users.myexpensemanager1.dao.TransactionDAO;
import com.example.users.myexpensemanager1.utils.DayAxisValueFormatter;
import com.example.users.myexpensemanager1.utils.MyAxisValueFormatter;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CombinedChartTransaction {

    public static Typeface mTfLight;
    private final int itemcount = 5;
    Context context;
    CombinedChart mChart;
    private List<Long> datalist;

    public CombinedChartTransaction(Context mcontext, CombinedChart mChart) {
        this.context = mcontext;
        this.mChart = mChart;
        datalist = new ArrayList<>();
        mTfLight = Typeface.createFromAsset(mcontext.getAssets(), "OpenSans-Light.ttf");

        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(ColorTemplate.rgb("#000000"));
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE,
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextColor(ColorTemplate.rgb("#ffffff"));
        l.setDrawInside(false);


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        IAxisValueFormatter custom = new MyAxisValueFormatter(mcontext);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setValueFormatter(custom);
        leftAxis.setAxisMinimum(0f);// this replaces setStartAtZero(true)
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.rgb("#ffffff"));
        leftAxis.setZeroLineColor(ColorTemplate.rgb("#ffffff"));
        leftAxis.setAxisLineColor(ColorTemplate.rgb("#fffffff"));
        leftAxis.setGridColor(ColorTemplate.rgb("#fffffff"));

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(5.5f);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextColor(ColorTemplate.rgb("#fffffff"));
        xAxis.setAxisLineColor(ColorTemplate.rgb("#fffffff"));
        xAxis.setGridColor(ColorTemplate.rgb("#fffffff"));

        CombinedData data = new CombinedData();

        data.setData(generateBarData());
        data.setData(generateLineData());
        data.setValueTypeface(mTfLight);

//        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        float start = 1f;
        long value = 0;

        for (int index = (int) start; index < itemcount + start; index++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, index - 5);
            c.add(Calendar.DATE, 0);
            value = TransactionDAO.initialiser(context).gettransactionamountByMonth(c);
            datalist.add(value);
            entries1.add(new BarEntry(index, value));
            Log.d("EXPM_Chart_combined", "value index = " + index + ", value = " + value);
        }
        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(ColorTemplate.rgb("#ffffff"));
        set1.setValueTextSize(10f);
        set1.setColor(ColorTemplate.rgb("#008888"));
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setHighlightEnabled(false);
        float barWidth = .9f;
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData d = new BarData(dataSets);
        d.setBarWidth(barWidth);
        return d;
    }

    private LineData generateLineData() {

        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 1; index <= itemcount; index++) {
            long sum = 0;
            for (int i = 0; i < index; i++) {
                sum = sum + datalist.get(i);
            }
            float val = (float) sum / index;
            entries.add(new Entry(index, val));
        }


        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(220, 128, 0));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(220, 128, 0));
        set.setCircleRadius(3f);
        set.setFillColor(Color.rgb(220, 128, 0));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(255, 255, 255));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

}