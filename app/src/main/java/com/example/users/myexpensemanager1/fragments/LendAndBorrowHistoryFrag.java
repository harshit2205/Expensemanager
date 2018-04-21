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

import com.example.users.myexpensemanager1.adapters.ParticipantsAdapter;
import com.example.users.myexpensemanager1.charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.dao.ParticipantsDAO;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.models.ParticipantItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LendAndBorrowHistoryFrag extends BaseFragment {

    ProgressBar progressBar;
    TextView emptyView, lendedAmount, borrowedAmount;
    RecyclerView participantsView;
    List<ParticipantItem> participantItemList;
    ParticipantsAdapter adapter;
    ParticipantsDAO participantsDAO;
    CardView view2;


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
        lendedAmount = view.findViewById(R.id.lended_amount);
        borrowedAmount = view.findViewById(R.id.borrowed_amount);
        String zeroValue = getActivity().getResources().getString(R.string.Rs) + " 0";
        lendedAmount.setText(zeroValue);
        lendedAmount.setTypeface(CombinedChartTransaction.mTfLight);
        borrowedAmount.setText(zeroValue);
        borrowedAmount.setTypeface(CombinedChartTransaction.mTfLight);
        view2 = view.findViewById(R.id.view2);
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
            String totalLend = getActivity().getResources().getString(R.string.Rs) + " " + participantsDAO.totalLend;
            lendedAmount.setText(totalLend);
            String totalBorrow = getActivity().getResources().getString(R.string.Rs) + " " + participantsDAO.totalBorrow;
            borrowedAmount.setText(totalBorrow);
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
                view2.setVisibility(View.VISIBLE);
            }
        }
    }

}
