package com.example.users.myexpensemanager1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.users.myexpensemanager1.Fragments.LendAndBorrowHistoryFrag;
import com.example.users.myexpensemanager1.Fragments.LiveStatsFrag;
import com.example.users.myexpensemanager1.Fragments.MoneyHistoryPager;
import com.example.users.myexpensemanager1.Fragments.RemainderHistoryFrag;
import com.example.users.myexpensemanager1.Fragments.TransactionHistoryPager;
import com.example.users.myexpensemanager1.R;

import java.io.File;

public class Main2Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static String userName = "dummy_user";

    Toolbar toolbar;
    int id_drawer_list;
    public static MenuItem menuitem;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Live Stats");
        createStorageDirectory();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        startupfragmentloader();
    }

    private void startupfragmentloader(){
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportActionBar().setTitle("Live Stats");
        LiveStatsFrag liveStatsFrag = new LiveStatsFrag();
        fragmentstarter(liveStatsFrag);
    }

    private void createStorageDirectory(){
        String folder_main = "app.myexpensemanager1";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main2, menu);

        menuitem = menu.findItem(R.id.action_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            if (id_drawer_list == R.id.nav_live_stats) {
            } else if (id_drawer_list == R.id.nav_transactions) {
                Log.d("Expm", "transaction add button pressed");
                Intent i = new Intent(Main2Activity.this, OneFragmentActivity.class);
                i.putExtra("addition_type",OneFragmentActivity.ADD_TRANSACTION);
                startActivity(i);

            } else if (id_drawer_list == R.id.nav_earnings) {
                Log.d("Expm", "money add button pressed");
                Intent i = new Intent(Main2Activity.this, OneFragmentActivity.class);
                i.putExtra("addition_type",OneFragmentActivity.ADD_EARNING);
                startActivity(i);
            } else if (id_drawer_list == R.id.nav_remainders) {
                Log.d("Expm", "remainder add button pressed");
                Intent i = new Intent(Main2Activity.this, OneFragmentActivity.class);
                i.putExtra("addition_type",OneFragmentActivity.ADD_REMAINDER);
                startActivity(i);
            } else if (id_drawer_list == R.id.nav_lend_and_borrow) {
                Log.d("Expm", "lendings add button pressed");
                Intent i = new Intent(Main2Activity.this, OneFragmentActivity.class);
                i.putExtra("addition_type",OneFragmentActivity.ADD_LEND_BORROW);
                startActivity(i);
            } else if (id_drawer_list == R.id.nav_bill_split) {
                Log.d("Expm", "borrowing add button pressed");
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id_drawer_list = item.getItemId();

       switch (id_drawer_list){
        case R.id.nav_live_stats:
            getSupportActionBar().setTitle("Live Stats");
            LiveStatsFrag liveStatsFrag = new LiveStatsFrag();
            fragmentstarter(liveStatsFrag);
            break;
        case R.id.nav_transactions:
            getSupportActionBar().setTitle("Transactions");
            TransactionHistoryPager transactionHistoryPager = new TransactionHistoryPager();
            fragmentstarter(transactionHistoryPager);
            break;
        case R.id.nav_earnings:
            getSupportActionBar().setTitle("Earnings");
            MoneyHistoryPager moneyHistoryPager = new MoneyHistoryPager();
            fragmentstarter(moneyHistoryPager);
            break;
        case R.id.nav_remainders:
            getSupportActionBar().setTitle("Remainders");
            RemainderHistoryFrag remainderHistoryFrag = new RemainderHistoryFrag();
            fragmentstarter(remainderHistoryFrag);
            break;
        case R.id.nav_lend_and_borrow:
            getSupportActionBar().setTitle("Lend and Borrows");
            LendAndBorrowHistoryFrag lendAndBorrowHistoryFrag = new LendAndBorrowHistoryFrag();
            fragmentstarter(lendAndBorrowHistoryFrag);
            break;
        case R.id.nav_bill_split:
            getSupportActionBar().setTitle("Bill Splits");
            break;
        default:Log.d("EXPM_Nav_drawer","no item identified");

    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (id_drawer_list) {
            case R.id.nav_live_stats:
                LiveStatsFrag liveStatsFrag = new LiveStatsFrag();
                fragmentstarter(liveStatsFrag);
                break;
            case R.id.nav_transactions:
                TransactionHistoryPager transactionHistoryPager = new TransactionHistoryPager();
                fragmentstarter(transactionHistoryPager);
                break;
            case R.id.nav_earnings:
                MoneyHistoryPager moneyHistoryPager = new MoneyHistoryPager();
                fragmentstarter(moneyHistoryPager);
                break;
            case R.id.nav_remainders:
                RemainderHistoryFrag remainderHistoryFrag = new RemainderHistoryFrag();
                fragmentstarter(remainderHistoryFrag);
                break;
            case R.id.nav_lend_and_borrow:
                LendAndBorrowHistoryFrag lendAndBorrowHistoryFrag = new LendAndBorrowHistoryFrag();
                fragmentstarter(lendAndBorrowHistoryFrag);
                break;
            case R.id.nav_bill_split:
            default:

        }
    }
}
