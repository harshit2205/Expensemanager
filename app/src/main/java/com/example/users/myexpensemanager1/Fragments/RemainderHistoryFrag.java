package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Adapters.RemainderHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.AlarmsDAO;
import com.example.users.myexpensemanager1.Models.AlarmItem;
import com.example.users.myexpensemanager1.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemainderHistoryFrag extends Fragment {
    RecyclerView remainderHistoryView;
    RemainderHistoryAdapter adapter;
    TextView emptyView;
    List<AlarmItem> alarmItems;

    public RemainderHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remainder_history, container, false);;
        emptyView = (TextView)view.findViewById(R.id.empty_view);
        remainderHistoryView = (RecyclerView)view.findViewById(R.id.remainder_history_recyclerview);
        alarmItems = AlarmsDAO.initialiser(getActivity().getApplicationContext()).showAlarmsTuple();
        adapter = new RemainderHistoryAdapter(getActivity().getApplicationContext(),alarmItems, getFragmentManager());

        remainderHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(alarmItems.size() == 0){
            emptyView.setVisibility(View.VISIBLE);
            remainderHistoryView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.GONE);
            remainderHistoryView.setVisibility(View.VISIBLE);
        }

        //knvfdknn fd

        remainderHistoryView.setAdapter(adapter);
        return view;
    }

}
