package com.example.users.myexpensemanager1.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.users.myexpensemanager1.R;

public class DetailShareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_share);
    }

    public void fragmentstarter(android.app.Fragment frag){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("options_frag");
        transaction.replace(R.id.container,frag);
        transaction.commit();
    }
}
