
package com.example.users.myexpensemanager1.Fragments;


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

import com.example.users.myexpensemanager1.Adapters.MoneyHistoryAdapter;
import com.example.users.myexpensemanager1.Dao.MoneyDAO;
import com.example.users.myexpensemanager1.Dao.RepetativeMoneyDAO;
import com.example.users.myexpensemanager1.Models.MoneyItem;
import com.example.users.myexpensemanager1.R;

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
    public String emptyMessage;


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
        adapter1 = new MoneyHistoryAdapter(getActivity().getApplicationContext(), nonmoneyList, getFragmentManager());
        NonRepHistoryView = (RecyclerView)v.findViewById(R.id.history_recyclerview);
        NonRepHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NonRepHistoryView.setAdapter(adapter1);


        List<MoneyItem> repmoneyList = RepetativeMoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
        Log.d("EXPM_Logs","list of money items generated at "+Long.toString(System.currentTimeMillis())
                + "with size " +repmoneyList.size());
        adapter2 = new MoneyHistoryAdapter(getActivity().getApplicationContext(), repmoneyList, getFragmentManager());
        RepHistoryView = (RecyclerView)v.findViewById(R.id.rep_history_recyclerview);
        RepHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RepHistoryView.setAdapter(adapter2);

        emptyView = (TextView)v.findViewById(R.id.empty_view);
        emptyView.setText(emptyMessage);


        FetchEarning fetchEarning = new FetchEarning();
        fetchEarning.execute();
        return v;
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