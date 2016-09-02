package com.challenge.concrete.concretegithubrepolist.githubRepository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eliete on 7/26/16.
 */
public abstract class InternetDetectionActivity extends AppCompatActivity {

    private ConnectionReceiver receiver;

    @Override
    public void onResume() {
        super.onResume();
        receiver = new ConnectionReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public abstract void setupDownload();


    public class ConnectionReceiver extends BroadcastReceiver {
        boolean firstTime = true;

        @Override
        public void onReceive(Context context, Intent intent) {
            if(firstTime){
                firstTime = false;
                return;
            }else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                if (MainActivity.hasConnection(context)){
                    setupDownload();
                }

            }
        }

    }
}
