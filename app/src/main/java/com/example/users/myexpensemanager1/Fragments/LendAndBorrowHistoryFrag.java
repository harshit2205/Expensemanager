package com.example.users.myexpensemanager1.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Adapters.LendBorrowHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.Models.LendBorrowItem;
import com.example.users.myexpensemanager1.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LendAndBorrowHistoryFrag extends BaseFragment {

    ProgressBar progressBar;
    TextView emptyView;
    RecyclerView historyView;
    LendBorrowHistoryAdapter adapter;
    TextView lends, borrows;


    public LendAndBorrowHistoryFrag() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lend_borrow_history, container, false);

        historyView = (RecyclerView)view.findViewById(R.id.lend_borrow_history_recyclerview);
        emptyView = (TextView)view.findViewById(R.id.empty_view);
        progressBar = (ProgressBar)view.findViewById(R.id.lend_borrow_progressBar);
        lends = (TextView)view.findViewById(R.id.total_lends);
        borrows = (TextView)view.findViewById(R.id.total_borrows);

        LendBorrowDAO lendBorrowDAO = LendBorrowDAO.initialiser(getActivity().getApplicationContext());
        List<LendBorrowItem> list = lendBorrowDAO.showLendBorrowTupple();

        lends.setText(Long.toString(lendBorrowDAO.getLends()));
        borrows.setText(Long.toString(lendBorrowDAO.getBorrows()));
        adapter = new LendBorrowHistoryAdapter(getActivity().getApplicationContext(),list, getFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyView.setLayoutManager(layoutManager);
        historyView.setAdapter(adapter);
        return view;
    }

}
