package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    public TextView emptyView;
    ProgressBar progressBar;
    public String message;
    public List<TransactionItem> transactionlist;

    public TransactionHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        historyView = (RecyclerView)view.findViewById(R.id.history_recyclerview);
        emptyView = (TextView)view.findViewById(R.id.empty_view);
        progressBar = (ProgressBar)view.findViewById(R.id.fetch_progressbar);
        adapter = new TransactionHistoryAdapter(getActivity().getApplicationContext(), transactionlist,getFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyView.setLayoutManager(layoutManager);
        historyView.setAdapter(adapter);
        setEmptyView(message);
        FetchTransaction fetch = new FetchTransaction();
        fetch.execute("get details");

        return view;
    }

    public void setEmptyView(String emptyViewText){
        emptyView.setText(emptyViewText);
    }

    class FetchTransaction extends AsyncTask< String, String, List<TransactionItem>>{

        @Override
        protected List<TransactionItem> doInBackground(String... params) {
            transactionlist = TransactionDAO.initialiser(getActivity().getApplicationContext()).showTransactionTuple();
            return transactionlist;
        }

        @Override
        protected void onPostExecute(List<TransactionItem> items) {
            progressBar.setVisibility(View.GONE);
            if(transactionlist.size() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                adapter.items = transactionlist;
                adapter.notifyDataSetChanged();
                historyView.setVisibility(View.VISIBLE);
            }
        }
    }



}
