package com.example.users.myexpensemanager1.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.users.myexpensemanager1.fragments.AddEarningFrag;
import com.example.users.myexpensemanager1.fragments.AddLendAndBorrowFrag;
import com.example.users.myexpensemanager1.fragments.AddParticipantFrag;
import com.example.users.myexpensemanager1.fragments.AddRemainder;
import com.example.users.myexpensemanager1.fragments.AddTransactionFragment;
import com.example.users.myexpensemanager1.R;

public class OneFragmentActivity extends BaseActivity {

    public static int ADD_TRANSACTION = 1;
    public static int ADD_EARNING = 2;
    public static int ADD_REMAINDER = 3;
    public static int ADD_LEND_BORROW = 4;
    public static int ADD_PARTICIPANT = 5;
    // activity to contain all back pressing activities.....
    ActionBar actionBar;
    int INTENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if(i.hasExtra("addition_type")){
            INTENT = i.getIntExtra("addition_type",0);
        }

        if(INTENT == ADD_TRANSACTION){
            getSupportActionBar().setTitle("Add Transaction");
            fragmentstarter(new AddTransactionFragment(false), "transaction_frag");
        }else if(INTENT == ADD_EARNING){
            getSupportActionBar().setTitle("Add Earning");
            fragmentstarter(new AddEarningFrag(), "earning_frag");
        }else if(INTENT == ADD_REMAINDER){
            getSupportActionBar().setTitle("Add Remainder");
            fragmentstarter(new AddRemainder(), "remainder_frag");
        }else if(INTENT == ADD_LEND_BORROW){
            getSupportActionBar().setTitle("Add Lend/Borrow");
            fragmentstarter(new AddLendAndBorrowFrag(), "lend_Borrow_Frag");
        } else if (INTENT == ADD_PARTICIPANT) {
            getSupportActionBar().setTitle("Add ParticipantItem");
            fragmentstarter(new AddParticipantFrag(), "add_Participant");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:return false;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }


}
