package com.example.users.myexpensemanager1.fragments;


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

import com.example.users.myexpensemanager1.adapters.RemainderHistoryAdapter;
import com.example.users.myexpensemanager1.dao.RemaindersDAO;
import com.example.users.myexpensemanager1.models.RemainderItem;
import com.example.users.myexpensemanager1.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemainderHistoryFrag extends Fragment {
    RecyclerView remainderHistoryView;
    RemainderHistoryAdapter adapter;
    TextView emptyView;
    List<RemainderItem> remainderItems;
    ProgressBar progressBar;

    public RemainderHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_history, container, false);
        emptyView = (TextView)view.findViewById(R.id.empty_view);
        remainderHistoryView = (RecyclerView)view.findViewById(R.id.remainder_history_recyclerview);
        progressBar = (ProgressBar)view.findViewById(R.id.remainder_progressBar);
        remainderItems = RemaindersDAO.initialiser(getActivity().getApplicationContext()).showRemainderTuple();
        adapter = new RemainderHistoryAdapter(getActivity().getApplicationContext(), remainderItems, getFragmentManager());
        remainderHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
        remainderHistoryView.setAdapter(adapter);
        FetchRemainders fetchRemainders = new FetchRemainders();
        fetchRemainders.execute();
        return view;
    }

    class FetchRemainders extends AsyncTask< String, String, List<RemainderItem>> {

        @Override
        protected List<RemainderItem> doInBackground(String... params) {
            remainderItems = RemaindersDAO.initialiser(getActivity().getApplicationContext()).showRemainderTuple();
            return remainderItems;
        }

        @Override
        protected void onPostExecute(List<RemainderItem> items) {
            progressBar.setVisibility(View.GONE);
            if(remainderItems.size() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                adapter.items = remainderItems;
                adapter.notifyDataSetChanged();
                remainderHistoryView.setVisibility(View.VISIBLE);
            }
        }
    }

}
