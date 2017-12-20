
package com.example.users.myexpensemanager1.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Adapters.MoneyHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.MoneyDAO;
import com.example.users.myexpensemanager1.Models.MoneyItem;
import com.example.users.myexpensemanager1.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyHistoryFrag extends BaseFragment {
    RecyclerView historyView;
    MoneyHistoryAdapter adapter;
    TextView emptyView;


    public MoneyHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and get moneyList.....
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        List<MoneyItem> moneyList = MoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
        Log.d("EXPM_Logs","list of money items generated at "+Long.toString(System.currentTimeMillis())
                + "with size " +moneyList.size());
        adapter = new MoneyHistoryAdapter(getActivity().getApplicationContext(), moneyList, getFragmentManager());
        historyView = (RecyclerView)v.findViewById(R.id.history_recyclerview);
        emptyView = (TextView)v.findViewById(R.id.empty_view);

        historyView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (MoneyDAO.initialiser(getActivity().getApplicationContext()).getMoneyCount() == 0){
            historyView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else {
            historyView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        historyView.setAdapter(adapter);
        return v;
    }


}