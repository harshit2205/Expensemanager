package com.example.users.myexpensemanager1.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.utils.ZoomOutPageTransformer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistoryPager extends Fragment {

    public FragmentPagerItemAdapter adapter;

    public TransactionHistoryPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transaction_history_pager, container, false);

        Log.d("EXPM_Fragmentadapter","oncreate view called");

        adapter = new FragmentPagerItemAdapter(getChildFragmentManager());

        TabLayout viewPagerTab = (TabLayout) v.findViewById(R.id.viewpagertab);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        Log.d("EXPM_tags","adapter set");

        viewPagerTab.setupWithViewPager(viewPager);
        Log.d("EXPM_tags","view pager tab linked with view pager");
        return v;
    }

    class FragmentPagerItemAdapter extends FragmentPagerAdapter {
        public FragmentPagerItemAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.app.Fragment getItem(int position) {
            switch(position){
                case 0:
                    TransactionHistoryFrag presentMonthHistory = new TransactionHistoryFrag();
                    presentMonthHistory.STATE = TransactionHistoryFrag.CURRENT_MONTH;
                    return presentMonthHistory;
                case 1:
                    TransactionHistoryFrag pastMonthHistory = new TransactionHistoryFrag();
                    pastMonthHistory.STATE = TransactionHistoryFrag.PAST_MONTH;
                    return pastMonthHistory;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:return "THIS MONTH";
                case 1:return "PAST MONTH";
            }
            return "UNKNOWN TAB";
        }
    }
}
