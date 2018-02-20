package com.example.users.myexpensemanager1.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.myexpensemanager1.Activities.Main2Activity;
import com.example.users.myexpensemanager1.Activities.OneFragmentActivity;
import com.example.users.myexpensemanager1.Adapters.TransactionHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.TransactionDAO;
import com.example.users.myexpensemanager1.Models.MessageEvent;
import com.example.users.myexpensemanager1.Models.TransactionItem;
import com.example.users.myexpensemanager1.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistoryFrag extends Fragment {
    public TextView emptyView;
    public String message;
    public List<TransactionItem> transactionlist;
    TransactionHistoryAdapter adapter ;
    RecyclerView historyView;
    ProgressBar progressBar;
    RelativeLayout emptyLayout;

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
        emptyLayout = (RelativeLayout) view.findViewById(R.id.empty_layout);
        adapter = new TransactionHistoryAdapter(getActivity(), transactionlist, getFragmentManager());
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

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("transaction_update")) {
            progressBar.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            historyView.setVisibility(View.GONE);
            FetchTransaction fetch = new FetchTransaction();
            fetch.execute("get details");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
                emptyLayout.setVisibility(View.VISIBLE);
            }else{
                adapter.items = transactionlist;
                adapter.notifyDataSetChanged();
                historyView.setVisibility(View.VISIBLE);
            }
        }
    }

}
