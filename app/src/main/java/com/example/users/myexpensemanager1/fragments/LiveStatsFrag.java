package com.example.users.myexpensemanager1.fragments;


import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.utils.WaveHelper;
import com.example.users.myexpensemanager1.utils.ZoomOutPageTransformer;
import com.gelitenight.waveview.library.WaveView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveStatsFrag extends BaseFragment  implements OnChartValueSelectedListener {

//    private CombinedChart mChart2;
    public FragmentPagerItemAdapter adapter;
    protected RectF mOnValueSelectedRectF;
    WaveView waveView ;
    WaveHelper waveHelper;
    private int mBorderColor = Color.parseColor("#008888");


    public LiveStatsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_live_stats, container, false);

        adapter = new FragmentPagerItemAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        waveView = (WaveView)view.findViewById(R.id.waveView);
        waveView.setBorder(0, mBorderColor);
        waveView.setShapeType(WaveView.ShapeType.CIRCLE);
        waveHelper = new WaveHelper(waveView);
        waveView.setWaveColor(
                Color.parseColor("#00aaaa"),
                Color.parseColor("#008888"));
        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        if (e == null)
//            return;
//
//        RectF bounds = mOnValueSelectedRectF;
//        mChart.getBarBounds((BarEntry) e, bounds);
//        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        Log.i("x-index",
//                "low: " + mChart.getLowestVisibleX() + ", high: "
//                        + mChart.getHighestVisibleX());
//
//        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() { }

    @Override
    public void onResume() {
        super.onResume();
        waveHelper.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        waveHelper.cancel();
    }

    class FragmentPagerItemAdapter extends FragmentPagerAdapter {
        private FragmentPagerItemAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.app.Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new TransactionBarChartFrag();
                case 1:
                    return new MoneyBarChartFrag();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


    }

}
