package com.example.users.myexpensemanager1.Activities;

import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.users.myexpensemanager1.Fragments.SelectHistoryFrag;
import com.example.users.myexpensemanager1.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SelectHistoryFrag selectHistoryFrag = new SelectHistoryFrag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,selectHistoryFrag);
        transaction.commit();
    }
}
