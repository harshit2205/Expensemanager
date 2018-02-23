package com.example.users.myexpensemanager1.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.charts.CombinedChartEarning;
import com.example.users.myexpensemanager1.R;
import com.github.mikephil.charting.charts.CombinedChart;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyBarChartFrag extends Fragment {

    CombinedChart mChart2;

    public MoneyBarChartFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_bar_chart, container, false);
        mChart2 = (CombinedChart) view.findViewById(R.id.chart2);

        CombinedChartEarning combinedChart = new CombinedChartEarning(getActivity().getApplicationContext(), mChart2);
        return view;
    }

}
