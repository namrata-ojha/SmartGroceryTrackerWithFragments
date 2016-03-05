package com.scu.smartgrocerytracker.pantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * This activity will be invoked by Alarm Manager every day
 * This activity in-turn starts the expiry notification intent service
 *
 */
public class ExpiryNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ExpiryNotification", "Invoking Expiry notification service");
        Intent intent = new Intent(this, ExpiryNotificationService.class);
        this.startService(intent);

    }
}
