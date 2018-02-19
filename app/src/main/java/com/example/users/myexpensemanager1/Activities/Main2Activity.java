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

import com.example.users.myexpensemanager1.Fragments.AboutUsFrag;
import com.example.users.myexpensemanager1.Fragments.LendAndBorrowHistoryFrag;
import com.example.users.myexpensemanager1.Fragments.LiveStatsFrag;
import com.example.users.myexpensemanager1.Fragments.MoneyHistoryPager;
import com.example.users.myexpensemanager1.Fragments.RemainderHistoryFrag;
import com.example.users.myexpensemanager1.Fragments.TransactionHistoryPager;
import com.example.users.myexpensemanager1.R;
import com.example.users.myexpensemanager1.Dialogs.AddTransactionDialog;

import java.io.File;

public class Main2Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static String userName = "dummy_user";
    public static MenuItem menuitem;
    Toolbar toolbar;
    int id_drawer_list;
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
            Intent intent = new Intent();
            int INTENT = 0;
            if (id_drawer_list == R.id.nav_transactions) {
                new AddTransactionDialog(this, getFragmentManager());
//                intent.setClass(Main2Activity.this, OneFragmentActivity.class);
//                INTENT = OneFragmentActivity.ADD_TRANSACTION;
            } else if (id_drawer_list == R.id.nav_earnings) {
                intent.setClass(Main2Activity.this, OneFragmentActivity.class);
                INTENT = OneFragmentActivity.ADD_EARNING;
            } else if (id_drawer_list == R.id.nav_remainders) {
                intent.setClass(Main2Activity.this, OneFragmentActivity.class);
                INTENT = OneFragmentActivity.ADD_REMAINDER;
            } else if (id_drawer_list == R.id.nav_lend_and_borrow) {
                intent.setClass(Main2Activity.this, OneFragmentActivity.class);
                INTENT = OneFragmentActivity.ADD_LEND_BORROW;
            }
            intent.putExtra("addition_type", INTENT);
            //startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id_drawer_list = item.getItemId();
        String title = "";
       switch (id_drawer_list){
        case R.id.nav_live_stats:
            title = "Live Stats";
            fragmentstarter(new LiveStatsFrag());
            break;
        case R.id.nav_transactions:
            title = "Transactions";
            fragmentstarter(new TransactionHistoryPager());
            break;
        case R.id.nav_earnings:
            title = "Earnings";
            fragmentstarter(new MoneyHistoryPager());
            break;
        case R.id.nav_remainders:
            title = "Remainders";
            fragmentstarter(new RemainderHistoryFrag());
            break;
        case R.id.nav_lend_and_borrow:
            title = "Lend and Borrows";
            fragmentstarter(new LendAndBorrowHistoryFrag());
            break;
        case R.id.nav_about_us:
            title = "About Us";
            fragmentstarter(new AboutUsFrag());
            break;
        default:Log.d("EXPM_Nav_drawer","no item identified");
    }
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (id_drawer_list) {
            case R.id.nav_live_stats:
                fragmentstarter(new LiveStatsFrag());
                break;
            case R.id.nav_transactions:
                fragmentstarter(new TransactionHistoryPager());
                break;
            case R.id.nav_earnings:
                fragmentstarter(new MoneyHistoryPager());
                break;
            case R.id.nav_remainders:
                fragmentstarter(new RemainderHistoryFrag());
                break;
            case R.id.nav_lend_and_borrow:
                fragmentstarter(new LendAndBorrowHistoryFrag());
                break;
            case R.id.nav_about_us:
                fragmentstarter(new AboutUsFrag());
            default:

        }
    }


}
