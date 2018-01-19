
package com.example.users.myexpensemanager1.Fragments;


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
    List<MoneyItem> earningList;
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
        List<MoneyItem> moneyList = MoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
        Log.d("EXPM_Logs","list of money items generated at "+Long.toString(System.currentTimeMillis())
                + "with size " +moneyList.size());
        adapter = new MoneyHistoryAdapter(getActivity().getApplicationContext(), moneyList, getFragmentManager());
        progressBar = (ProgressBar)v.findViewById(R.id.earnings_progressbar);
        historyView = (RecyclerView)v.findViewById(R.id.history_recyclerview);
        emptyView = (TextView)v.findViewById(R.id.empty_view);

        historyView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyView.setAdapter(adapter);
        emptyView.setText(emptyMessage);
        FetchEarning fetchEarning = new FetchEarning();
        fetchEarning.execute();
        return v;
    }

    class FetchEarning extends AsyncTask< String, String, List<MoneyItem>> {

        @Override
        protected List<MoneyItem> doInBackground(String... params) {
            earningList = MoneyDAO.initialiser(getActivity().getApplicationContext()).showMoneyTuple();
            return earningList;
        }

        @Override
        protected void onPostExecute(List<MoneyItem> items) {
            progressBar.setVisibility(View.GONE);
            if(earningList.size() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                adapter.items = earningList;
                adapter.notifyDataSetChanged();
                historyView.setVisibility(View.VISIBLE);
            }
        }
    }
}