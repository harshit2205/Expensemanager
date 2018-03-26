package com.example.users.myexpensemanager1.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.dao.LendBorrowDAO;
import com.example.users.myexpensemanager1.dao.ParticipantsDAO;
import com.example.users.myexpensemanager1.models.LendBorrowItem;
import com.example.users.myexpensemanager1.models.ParticipantItem;
import com.weiwangcn.betterspinner.library.BetterSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParticipantFrag extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    EditText participantsName;
    EditText amount;
    EditText purpose;
    CheckBox setLendBorrow;
    Button addParticipant;
    BetterSpinner lendBorrowType;
    LinearLayout amountLayout;
    View view;
    private String[] LENDBORROWTYPE = new String[]{
            "Lending to", "Borrowing from"};

    public AddParticipantFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_participant, container, false);
        participantsName = view.findViewById(R.id.participant_name);
        amount = view.findViewById(R.id.amount);
        setLendBorrow = view.findViewById(R.id.line0);
        setLendBorrow.setChecked(false);
        setLendBorrow.setOnCheckedChangeListener(this);
        lendBorrowType = view.findViewById(R.id.type_chooser);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.dropdown_item_spinner, LENDBORROWTYPE);
        lendBorrowType.setAdapter(adapter);
        purpose = view.findViewById(R.id.purpose);
        amountLayout = view.findViewById(R.id.line1);
        addParticipant = view.findViewById(R.id.add_participant);

        addParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String participantString = participantsName.getText().toString();
                String amountString = amount.getText().toString();
                if (amountString.equals("")) amountString = "0";

                if (inputchecker()) {
                    int isInDebt = ParticipantItem.NO_DEBT;
                    if (lendBorrowType.getText().toString().equals("Borrowing from")) {
                        Log.d("EXPM_Participant", "borrows from condition");
                        isInDebt = ParticipantItem.IS_IN_DEBT;
                        LendBorrowItem item = new LendBorrowItem(participantString,
                                -Long.parseLong(amountString),
                                purpose.getText().toString(),
                                0,
                                System.currentTimeMillis());
                        LendBorrowDAO.initialiser(getActivity().getApplicationContext()).insertLendBorrowItem(item);
                    } else if (lendBorrowType.getText().toString().equals("Lending to")) {
                        Log.d("EXPM_Participant", "Lending to condition");
                        isInDebt = ParticipantItem.HAS_LENDED;
                        LendBorrowItem item = new LendBorrowItem(participantString,
                                Long.parseLong(amountString),
                                purpose.getText().toString(),
                                0,
                                System.currentTimeMillis());
                        LendBorrowDAO.initialiser(getActivity().getApplicationContext()).insertLendBorrowItem(item);
                    }
                    ParticipantItem participantItem = new ParticipantItem(participantString,
                            Long.parseLong(amountString),
                            isInDebt);
                    ParticipantsDAO.initialiser(getActivity()).addparticipants(participantItem);
                    hideKeyboard();
                    Snackbar.make(view, "participantItem added", Snackbar.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
        });
        return view;
    }

    private boolean inputchecker() {
        if (participantsName.getText().toString().equals("")) {
            Snackbar.make(view, "enter participant's name", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (ParticipantsDAO.initialiser(getActivity()).ifParticipantExists(participantsName.getText().toString())) {
            Snackbar.make(view, "participant already in list, please alter the name", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (setLendBorrow.isChecked()) {
            if (amount.getText().toString().equals("") || purpose.getText().toString().equals("")
                    || lendBorrowType.getText().toString().equals("")) {
                Snackbar.make(view, "values unfilled", Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox box = (CheckBox) buttonView;
        Log.d("EXPM_Checkchange", "check is changed");
        if (box.getId() == R.id.line0) {
            if (isChecked) {
                lendBorrowType.setVisibility(View.VISIBLE);
                purpose.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);
            } else {
                lendBorrowType.setVisibility(View.GONE);
                purpose.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
            }
        }
    }

}
