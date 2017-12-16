package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.myexpensemanager1.Adapters.TransactionHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistoryFrag extends Fragment {
    TransactionHistoryAdapter adapter ;
    RecyclerView historyView;
    TextView emptyView;

    public TransactionHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        List<TransactionItem> transactionlist = TransactionDAO.initialiser(getActivity().getApplicationContext()).showTransactionTuple();
        Log.d("EXPM_Logs","list of transaction items generated at "+Long.toString(System.currentTimeMillis())
                + "with size " +transactionlist.size());
        adapter = new TransactionHistoryAdapter(getActivity().getApplicationContext(), transactionlist);
        historyView = (RecyclerView)view.findViewById(R.id.history_recyclerview);
        emptyView = (TextView)view.findViewById(R.id.empty_view);

        historyView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (TransactionDAO.initialiser(getActivity().getApplicationContext()).getTransactionsCount() == 0){
            historyView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else {
            historyView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        historyView.setAdapter(adapter);
        return view;
    }

}
