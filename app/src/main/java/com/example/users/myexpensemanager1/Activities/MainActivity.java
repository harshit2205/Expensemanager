package com.example.users.myexpensemanager1.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.users.myexpensemanager1.Fragments.OptionsFrag;
import com.example.users.myexpensemanager1.R;

public class MainActivity extends BaseActivity  {

    public static String userName = "dummy_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OptionsFrag optionsFrag = new OptionsFrag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,optionsFrag,"options_frag");
        transaction.commit();
    }
}
