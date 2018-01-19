package com.example.users.myexpensemanager1.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.users.myexpensemanager1.Fragments.AddEarningFrag;
import com.example.users.myexpensemanager1.Fragments.AddLendAndBorrowFrag;
import com.example.users.myexpensemanager1.Fragments.AddRemainder;
import com.example.users.myexpensemanager1.Fragments.AddTransactionFragment;
import com.example.users.myexpensemanager1.R;

public class OneFragmentActivity extends BaseActivity {

    // activity to contain all back pressing activities.....
    ActionBar actionBar;
    public static int ADD_TRANSACTION = 1;
    public static int ADD_EARNING = 2;
    public static int ADD_REMAINDER = 3;
    public static int ADD_LEND_BORROW = 4;
    int INTENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if(i.hasExtra("addition_type")){
            INTENT = i.getIntExtra("addition_type",0);
        }

        if(INTENT == ADD_TRANSACTION){
            getSupportActionBar().setTitle("Add Transaction");
            AddTransactionFragment fragment = new AddTransactionFragment();
            fragmentstarter(fragment, "transaction_frag");
        }else if(INTENT == ADD_EARNING){
            getSupportActionBar().setTitle("Add Earning");
            AddEarningFrag frag = new AddEarningFrag();
            fragmentstarter(frag, "earning_frag");
        }else if(INTENT == ADD_REMAINDER){
            getSupportActionBar().setTitle("Add Remainder");
            AddRemainder frag = new AddRemainder();
            fragmentstarter(frag,"remainder_frag");
        }else if(INTENT == ADD_LEND_BORROW){
            getSupportActionBar().setTitle("Add Lend/Borrow");
            AddLendAndBorrowFrag frag = new AddLendAndBorrowFrag();
            fragmentstarter(frag, "lend_Borrow_Frag");
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
