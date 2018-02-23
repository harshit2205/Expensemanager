package com.example.users.myexpensemanager1.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.users.myexpensemanager1.adapters.LendBorrowHistoryAdapter;
import com.example.users.myexpensemanager1.dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.models.LendBorrowItem;
import com.example.users.myexpensemanager1.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LendAndBorrowHistoryFrag extends BaseFragment {

    ProgressBar progressBar;
    TextView emptyView;
    RecyclerView historyView;
    List<LendBorrowItem> lendBorrList;
    LendBorrowHistoryAdapter adapter;
    TextView lends, borrows;
    LendBorrowDAO lendBorrowDAO;


    public LendAndBorrowHistoryFrag() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lend_borrow_history, container, false);

        historyView = (RecyclerView) view.findViewById(R.id.lend_borrow_history_recyclerview);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
        progressBar = (ProgressBar) view.findViewById(R.id.lend_borrow_progressBar);
        lends = (TextView) view.findViewById(R.id.total_lends);
        borrows = (TextView) view.findViewById(R.id.total_borrows);
        adapter = new LendBorrowHistoryAdapter(getActivity().getApplicationContext(), lendBorrList, getFragmentManager());
        historyView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyView.setLayoutManager(layoutManager);
        FetchData fetchData = new FetchData();
        fetchData.execute();
        return view;
    }

    class FetchData extends AsyncTask<String, String, List<LendBorrowItem>> {

        @Override
        protected List<LendBorrowItem> doInBackground(String... strings) {
            lendBorrowDAO = LendBorrowDAO.initialiser(getActivity().getApplicationContext());
            lendBorrList = lendBorrowDAO.showLendBorrowTupple();
            Log.d("EXPM_LendAndBorrow", "do in background called with list size " + lendBorrList.size());
            return lendBorrList;
        }

        @Override
        protected void onPostExecute(List<LendBorrowItem> lendBorrowItems) {
            super.onPostExecute(lendBorrowItems);
            Log.d("EXPM_LendAndBorrow", "onPost execute called with list size " + lendBorrowItems.size());
            progressBar.setVisibility(View.GONE);
            if (lendBorrowItems.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                String notAvailableString = "-NA-";
                lends.setText(notAvailableString);
                borrows.setText(notAvailableString);
            } else {
                adapter.items = lendBorrowItems;
                adapter.notifyDataSetChanged();
                historyView.setVisibility(View.VISIBLE);
                String lendString = getActivity().getResources().getString(R.string.Rs) + Long.toString(lendBorrowDAO.getLends());
                lends.setText(lendString);
                String borrowString = getActivity().getResources().getString(R.string.Rs) + Long.toString(lendBorrowDAO.getBorrows());
                borrows.setText(borrowString);
            }
        }
    }

}
