package com.example.users.myexpensemanager1.Activities;

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

import com.example.users.myexpensemanager1.Fragments.AddEarningFrag;
import com.example.users.myexpensemanager1.Fragments.AddTransactionFragment;
import com.example.users.myexpensemanager1.Fragments.LiveStatsFrag;
import com.example.users.myexpensemanager1.Fragments.MoneyHistoryFrag;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                AddTransactionFragment fragment = new AddTransactionFragment();
                fragmentstarter(fragment, "transaction_frag");
            } else if (id_drawer_list == R.id.nav_earnings) {
                Log.d("Expm", "money add button pressed");
                AddEarningFrag frag = new AddEarningFrag();
                fragmentstarter(frag, "earning_frag");
            } else if (id_drawer_list == R.id.nav_remainders) {
                Log.d("Expm", "remainder add button pressed");
            } else if (id_drawer_list == R.id.nav_lendings) {
                Log.d("Expm", "lendings add button pressed");
            } else if (id_drawer_list == R.id.nav_borrowings) {
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
        menuitem.setVisible(true);
        getSupportActionBar().setTitle("Live Stats");
        LiveStatsFrag liveStatsFrag = new LiveStatsFrag();
        fragmentstarter(liveStatsFrag);
        break;
        case R.id.nav_transactions:
        menuitem.setVisible(true);
        getSupportActionBar().setTitle("Transactions");
        TransactionHistoryPager transactionHistoryPager = new TransactionHistoryPager();
        fragmentstarter(transactionHistoryPager);
        break;
        case R.id.nav_earnings:
        menuitem.setVisible(true);
        getSupportActionBar().setTitle("Earnings");
        MoneyHistoryFrag moneyHistoryFrag = new MoneyHistoryFrag();
        fragmentstarter(moneyHistoryFrag);
        break;
        case R.id.nav_remainders:
        menuitem.setVisible(true);
        getSupportActionBar().setTitle("Remainders");
        RemainderHistoryFrag remainderHistoryFrag = new RemainderHistoryFrag();
        fragmentstarter(remainderHistoryFrag);
        break;
        case R.id.nav_lendings:
        menuitem.setVisible(true);
        getSupportActionBar().setTitle("Lendings");
        break;
        case R.id.nav_borrowings:
        menuitem.setVisible(true);
        getSupportActionBar().setTitle("Borrowings");
        break;
        default:Log.d("EXPM_Nav_drawer","no item identiied");

    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
