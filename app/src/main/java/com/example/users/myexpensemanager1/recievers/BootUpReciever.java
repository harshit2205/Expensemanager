package com.example.users.myexpensemanager1.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by USER on 2/24/2018.
 */

public class BootUpReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("EXPM_miscellanious", "this class is involked");
    }
}
