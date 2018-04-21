package com.example.users.myexpensemanager1.fragments;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.adapters.ParticipantsHistoryAdapter;
import com.example.users.myexpensemanager1.charts.CombinedChartTransaction;
import com.example.users.myexpensemanager1.dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.dao.ParticipantsDAO;
import com.example.users.myexpensemanager1.dao.TransactionDAO;
import com.example.users.myexpensemanager1.dialogs.EditParticipantDialog;
import com.example.users.myexpensemanager1.models.LendBorrowItem;
import com.example.users.myexpensemanager1.models.MessageEvent;
import com.example.users.myexpensemanager1.models.ParticipantItem;
import com.example.users.myexpensemanager1.models.TransactionItem;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipanthistoryFrag extends BaseFragment {
    View view;
    TextView participant;
    TextView total;
    EditText purpose;
    EditText amount;
    Button add;
    LinearLayout topBar;
    ParticipantItem participantItem;
    BetterSpinner typeSpinner;
    SmoothProgressBar progressbar;
    RecyclerView historyView;
    ParticipantsHistoryAdapter adapter1;
    TextView emptyView;
    List<LendBorrowItem> lendBorrowItemList;
    private String participantName;
    private String[] LENDBORROWTYPE = new String[]{
            "Lending to", "Borrowing from"};

    public ParticipanthistoryFrag() {
        // Required empty public constructor
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_participanthistory, container, false);
        participant = view.findViewById(R.id.participant_name);
        topBar = view.findViewById(R.id.line_1);
        participant.setText(participantName);
        total = view.findViewById(R.id.total_amount);
        total.setTypeface(CombinedChartTransaction.mTfLight);
        purpose = view.findViewById(R.id.purpose);
        amount = view.findViewById(R.id.amount);
        amount.setTypeface(CombinedChartTransaction.mTfLight);
        typeSpinner = view.findViewById(R.id.type_chooser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.dropdown_item_spinner, LENDBORROWTYPE);
        typeSpinner.setAdapter(adapter);

        topBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditParticipantDialog(getActivity(), getFragmentManager(), ParticipantsDAO.initialiser(getActivity()).getParticipant(participantName));
            }
        });

        add = view.findViewById(R.id.add_debt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                long totalamount = 0;
                ParticipantItem participantItem = ParticipantsDAO.initialiser(getActivity()).getParticipant(participantName);
                if (inputChecker()) {
                    Log.d("EXPM_Totalamount", "initial val = " + participantItem.getDues());
                    if (participantItem.isInDebt() == ParticipantItem.IS_IN_DEBT) {
                        totalamount = -participantItem.getDues();
                    } else {
                        totalamount = participantItem.getDues();
                    }

                    if (typeSpinner.getText().toString().equals("Borrowing from")) {
                        Log.d("EXPM_Participant", "new data added to borrow");
                        LendBorrowItem item = new LendBorrowItem(participantName,
                                -Long.parseLong(amount.getText().toString()),
                                purpose.getText().toString(),
                                0,
                                System.currentTimeMillis());
                        Log.d("EXPM_Totalamount", "amount = " + totalamount);
                        totalamount = totalamount - Long.parseLong(amount.getText().toString());
                        Log.d("EXPM_Totalamount", "amount = " + totalamount);
                        LendBorrowDAO.initialiser(getActivity()).insertLendBorrowItem(item);
                    } else if (typeSpinner.getText().toString().equals("Lending to")) {
                        Log.d("EXPM_Participant", "new data added to lend");
                        LendBorrowItem item = new LendBorrowItem(participantName,
                                Long.parseLong(amount.getText().toString()),
                                purpose.getText().toString(),
                                0,
                                System.currentTimeMillis());
                        totalamount = totalamount + Long.parseLong(amount.getText().toString());
                        LendBorrowDAO.initialiser(getActivity()).insertLendBorrowItem(item);
                    }
                    int isinDebt = 0;
                    if (totalamount < 0) {
                        totalamount = -totalamount;
                        isinDebt = ParticipantItem.IS_IN_DEBT;
                    } else if (totalamount > 0) {
                        isinDebt = ParticipantItem.HAS_LENDED;
                    } else {
                        isinDebt = ParticipantItem.NO_DEBT;
                    }
                    ParticipantItem participantItem1 = new ParticipantItem(participantName, totalamount, isinDebt);
                    ParticipantsDAO.initialiser(getActivity()).updateParticipant(participantItem1, participantName);
                    MessageEvent event = new MessageEvent("update_all_data");
                    event.setParticipantsName(participantName);
                    EventBus.getDefault().post(event);
                }
            }
        });

        progressbar = view.findViewById(R.id.history_progressbar);
        historyView = view.findViewById(R.id.history_recyclerview);
        adapter1 = new ParticipantsHistoryAdapter(lendBorrowItemList, getActivity());
        historyView.setAdapter(adapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyView.setLayoutManager(layoutManager);
        emptyView = view.findViewById(R.id.empty_view);
        updateAllData();
        return view;
    }

    void resetValues() {
        purpose.setText(null);
        typeSpinner.setText(null);
        amount.setText(null);
    }

    void updateAllData() {
        new FetchParticipantHistory().execute(participantName);
    }

    private boolean inputChecker() {
        if (typeSpinner.getText().toString().equals("") || amount.getText().toString().equals("")) {
            Snackbar.make(view, "required values unfilled", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        resetValues();

        participantName = event.getParticipantsName();

        participant.setText(participantName);
        emptyView.setVisibility(GONE);
        historyView.setVisibility(GONE);
        progressbar.setVisibility(View.VISIBLE);
        if (event.getMessage().equals("update_all_data")) {
            participantItem = ParticipantsDAO.initialiser(getActivity().getApplicationContext()).getParticipant(participantName);
            String totalamount = getActivity().getResources().getString(R.string.Rs) + " " + Long.toString(participantItem.getDues());
            if (participantItem.isInDebt() == ParticipantItem.IS_IN_DEBT) {
                topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            } else if (participantItem.isInDebt() == ParticipantItem.HAS_LENDED) {
                topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
            } else if (participantItem.isInDebt() == ParticipantItem.NO_DEBT) {
                topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
            }
            total.setText(totalamount);
            Log.d("EXPM_progresscheck", "the code is fine till here");
            lendBorrowItemList = LendBorrowDAO.initialiser(getActivity().getApplicationContext()).showTupplesByName(participantName);
            progressbar.setVisibility(GONE);
            if (lendBorrowItemList.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                adapter1.items = lendBorrowItemList;
                adapter1.notifyDataSetChanged();
                historyView.setVisibility(View.VISIBLE);
            }
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

    class FetchParticipantHistory extends AsyncTask<String, String, List<LendBorrowItem>> {

        @Override
        protected List<LendBorrowItem> doInBackground(String... params) {
            participantItem = ParticipantsDAO.initialiser(getActivity().getApplicationContext()).getParticipant(participantName);
            String totalamount = getActivity().getResources().getString(R.string.Rs) + " " + Long.toString(participantItem.getDues());
            if (participantItem.isInDebt() == ParticipantItem.IS_IN_DEBT) {
                topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            } else if (participantItem.isInDebt() == ParticipantItem.HAS_LENDED) {
                topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
            } else if (participantItem.isInDebt() == ParticipantItem.NO_DEBT) {
                topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));
            }
            total.setText(totalamount);
            lendBorrowItemList = LendBorrowDAO.initialiser(getActivity().getApplicationContext()).showTupplesByName(participantName);
            return lendBorrowItemList;
        }

        @Override
        protected void onPostExecute(List<LendBorrowItem> items) {
            Log.d("EXPM_Participant", "item list size = " + items.size());
            super.onPostExecute(items);
            progressbar.setVisibility(GONE);
            if (items.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                adapter1.items = items;
                adapter1.notifyDataSetChanged();
                historyView.setVisibility(View.VISIBLE);
            }
        }
    }

}
