package com.example.users.myexpensemanager1.activities;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;


public class MyExpenseManagerApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        Log.d("datahex","my program begins here");
        // Normal app init code...
    }
}
