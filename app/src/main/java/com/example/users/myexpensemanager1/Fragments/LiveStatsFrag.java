package com.example.users.myexpensemanager1.Fragments;


import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.Charts.BarChartTransaction;
import com.example.users.myexpensemanager1.Charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveStatsFrag extends BaseFragment  implements OnChartValueSelectedListener {

    private BarChart mChart;
    private CombinedChart mChart2;

    public LiveStatsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_live_stats, container, false);




        mChart = (BarChart) view.findViewById(R.id.chart1);
        mChart2 = (CombinedChart) view.findViewById(R.id.chart2);
//        mChart.setOnChartValueSelectedListener(this);
        BarChartTransaction chartTransaction = new BarChartTransaction(getActivity().getApplicationContext(),mChart);
        CombinedChartTransaction combinedChart = new CombinedChartTransaction(getActivity().getApplicationContext(), mChart2);
        return view;

    }




    protected RectF mOnValueSelectedRectF;

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() { }

}
