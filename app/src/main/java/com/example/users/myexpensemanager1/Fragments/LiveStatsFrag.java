package com.example.users.myexpensemanager1.Fragments;


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
import com.example.users.myexpensemanager1.Utils.WaveHelper;
import com.gelitenight.waveview.library.WaveView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }




}
