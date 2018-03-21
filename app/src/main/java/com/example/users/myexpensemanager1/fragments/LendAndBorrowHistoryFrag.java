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

import com.example.users.myexpensemanager1.adapters.ParticipantsAdapter;
import com.example.users.myexpensemanager1.dao.ParticipantsDAO;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.models.ParticipantItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LendAndBorrowHistoryFrag extends BaseFragment {

    ProgressBar progressBar;
    TextView emptyView;
    RecyclerView participantsView;
    List<ParticipantItem> participantItemList;
    ParticipantsAdapter adapter;
    ParticipantsDAO participantsDAO;


    public LendAndBorrowHistoryFrag() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lend_borrow_history, container, false);

        participantsView = (RecyclerView) view.findViewById(R.id.participants_list);
        emptyView = view.findViewById(R.id.empty_view);
        progressBar = view.findViewById(R.id.lend_borrow_progressBar);
        adapter = new ParticipantsAdapter(getActivity(), participantItemList, getFragmentManager());
        participantsView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        participantsView.setLayoutManager(layoutManager);
        FetchData fetchData = new FetchData();
        fetchData.execute();
        return view;
    }

    class FetchData extends AsyncTask<String, String, List<ParticipantItem>> {

        @Override
        protected List<ParticipantItem> doInBackground(String... strings) {
            participantsDAO = ParticipantsDAO.initialiser(getActivity().getApplicationContext());
            participantItemList = participantsDAO.showParticipants();
            return participantItemList;
        }

        @Override
        protected void onPostExecute(List<ParticipantItem> participantItemList) {
            super.onPostExecute(participantItemList);
            progressBar.setVisibility(View.GONE);
            if (participantItemList.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                adapter.items = participantItemList;
                Log.d("EXPM_lendborrow", "list send to adapter");
                adapter.notifyDataSetChanged();
                participantsView.setVisibility(View.VISIBLE);
            }
        }
    }

}
