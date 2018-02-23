
package com.example.users.myexpensemanager1.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.users.myexpensemanager1.adapters.MoneyHistoryAdapter;
import com.example.users.myexpensemanager1.dao.MoneyDAO;
import com.example.users.myexpensemanager1.dao.RepetativeMoneyDAO;
import com.example.users.myexpensemanager1.dialogs.ShowMoneyDetailsDialog;
import com.example.users.myexpensemanager1.models.MessageEvent;
import com.example.users.myexpensemanager1.models.MoneyItem;
import com.example.users.myexpensemanager1.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyHistoryFrag extends BaseFragment {
    CardView nonRepCV;
    CardView repCV;
    RecyclerView NonRepHistoryView;
    RecyclerView RepHistoryView;
    MoneyHistoryAdapter adapter1, adapter2;
    TextView emptyView;
    List<MoneyItem> earningList,repearninglist;
    ProgressBar progressBar;


    public MoneyHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and get moneyList.....
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        progressBar = (ProgressBar)v.findViewById(R.id.earnings_progressbar);
        nonRepCV = (CardView)v.findViewById(R.id.history_cardView1);
        repCV = (CardView)v.findViewById(R.id.history_cardView2);


        List<MoneyItem> nonmoneyList = MoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
        Log.d("EXPM_Logs","list of money items generated at "+Long.toString(System.currentTimeMillis())
                + "with size " +nonmoneyList.size());
        adapter1 = new MoneyHistoryAdapter(getActivity(), nonmoneyList, getFragmentManager(),
                ShowMoneyDetailsDialog.ONETIME);
        NonRepHistoryView = (RecyclerView)v.findViewById(R.id.history_recyclerview);
        NonRepHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NonRepHistoryView.setAdapter(adapter1);


        List<MoneyItem> repmoneyList = RepetativeMoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
        Log.d("EXPM_Logs","list of money items generated at "+Long.toString(System.currentTimeMillis())
                + "with size " +repmoneyList.size());
        adapter2 = new MoneyHistoryAdapter(getActivity(), repmoneyList, getFragmentManager(),
                ShowMoneyDetailsDialog.MONTHLY);
        RepHistoryView = (RecyclerView)v.findViewById(R.id.rep_history_recyclerview);
        RepHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RepHistoryView.setAdapter(adapter2);

        emptyView = (TextView)v.findViewById(R.id.empty_view);
        emptyView.setText("no earns till now");


        FetchEarning fetchEarning = new FetchEarning();
        fetchEarning.execute();
        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equals("earnings_update")) {
            progressBar.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            nonRepCV.setVisibility(View.GONE);
            repCV.setVisibility(View.GONE);
            FetchEarning fetch = new FetchEarning();
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

    class FetchEarning extends AsyncTask< String, String, List<MoneyItem>> {

        @Override
        protected List<MoneyItem> doInBackground(String... params) {
            earningList = MoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
            repearninglist = RepetativeMoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
            return earningList;
        }

        @Override
        protected void onPostExecute(List<MoneyItem> items) {
            progressBar.setVisibility(View.GONE);
            if(earningList.size() !=0){
                adapter1.items = earningList;
                adapter1.notifyDataSetChanged();
                nonRepCV.setVisibility(View.VISIBLE);
            }if(repearninglist.size() !=0){
                adapter2.items = repearninglist;
                adapter2.notifyDataSetChanged();
                repCV.setVisibility(View.VISIBLE);
            }if(earningList.size() == 0 && repearninglist.size() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }
        }
    }
}