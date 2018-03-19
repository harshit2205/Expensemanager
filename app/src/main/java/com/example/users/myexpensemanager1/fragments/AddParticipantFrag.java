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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.dao.ParticipantsDAO;
import com.example.users.myexpensemanager1.models.Participant;
import com.weiwangcn.betterspinner.library.BetterSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddParticipantFrag extends BaseFragment {

    EditText participantsName;
    EditText amount;
    Button addParticipant;
    BetterSpinner lendBorrowType;
    private String[] LENDBORROWTYPE = new String[]{
            "Lending to", "Borrowing from"};

    public AddParticipantFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_participant, container, false);
        participantsName = view.findViewById(R.id.participant_name);
        amount = view.findViewById(R.id.amount);
        lendBorrowType = view.findViewById(R.id.type_chooser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.dropdown_item_spinner, LENDBORROWTYPE);
        lendBorrowType.setAdapter(adapter);
        addParticipant = view.findViewById(R.id.add_participant);
        addParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (participantsName.getText().toString().equals("")) {
                    Snackbar.make(view, "enter participant's name", Snackbar.LENGTH_SHORT).show();
                } else {
                    int isInDebt = Participant.NO_DEBT;
                    if (lendBorrowType.getText().toString().equals("Borrowing from")) {
                        isInDebt = Participant.IS_IN_DEBT;
                    } else if (lendBorrowType.getText().toString().equals("Lending to")) {
                        isInDebt = Participant.HAS_LENDED;
                    }
                    Log.d("EXPM_lendborrow", "input data = " + participantsName.getText().toString() + ", " + isInDebt);
                    Participant participant = new Participant(participantsName.getText().toString(),
                            isInDebt);
                    ParticipantsDAO.initialiser(getActivity()).addparticipants(participant);
                }
                hideKeyboard();
                Snackbar.make(view, "participant added", Snackbar.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        return view;
    }

}
