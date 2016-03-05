package com.scu.smartgrocerytracker.pantry;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.welcome.WelcomeMainActivity;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 */
public class ExpiryNotificationService extends IntentService {

    private static final String ACTION_EXPIRY_NOTIFICATION = "com.scu.smartgrocerytracker.pantry.action.EXPIRY_NOTIFICATION";
    private static final String ACTION_OUT_OF_STOCK = "com.scu.smartgrocerytracker.pantry.action.OUT_OF_STOCK";
    private static final String STATUS_EXPIRED = "EXPIRED";
    private static final String STATUS_EXPIRING = "EXPIRING";

    private static final int DEFAULT_EXPIRY_NOTIFICATION_DAYS = 2;

    // TODO: Rename parameters
    private static final String OUT_OF_STOCK_ITEM_NAME = "com.scu.smartgrocerytracker.pantry.extra.ITEM_NAME";


    public ExpiryNotificationService() {

        super("ExpiryNotificationService");
    }

        @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            startExpiryNotificationAction();
        }
    }

    /**
     * This method sends notification for expiring item
     */
    private void startExpiryNotificationAction() {
        //Get all the inventory items and iterate to find if the item expires within
        //user specified amount of time
        SmartGroceryDBHelper dbHelper = SmartGroceryDBHelper.getInstance(ExpiryNotificationService.this);
        List<PantryItem> items = dbHelper.getAllPantryItems();
        for(PantryItem item : items) {
            long expiryDate = item.getExpiryDate();
            long currentDate = System.currentTimeMillis();
            long expiryDays = DateUtils.MILLIS_PER_DAY * DEFAULT_EXPIRY_NOTIFICATION_DAYS;
            long currentExpiry = expiryDate - currentDate;
            if(currentExpiry <= expiryDays) {
                String expiryStatus;
                if(currentExpiry < 0) {
                    expiryStatus = STATUS_EXPIRED;
                }
                else {
                    expiryStatus = STATUS_EXPIRING;
                }
                sendExpiryNotification(item.getItemId(),item.getName(),expiryStatus);
            }

        }

    }

    private void sendExpiryNotification(int itemId, String itemName, String expiryStatus) {
        Log.d("ExpiryNotification","Sending notification for " + itemId + ":" + itemName + " with status:" + expiryStatus);
        int requestCode = 0;
        int flags = 0;
        Intent intent = new Intent(getApplicationContext(), WelcomeMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), requestCode, intent, flags);

        int id = itemId;
        String notificationText;
        if(expiryStatus.equals(STATUS_EXPIRED)) {
            notificationText = itemName + " expired.";
        }
        else {
            notificationText = itemName + " is expiring in " + DEFAULT_EXPIRY_NOTIFICATION_DAYS + " days.";
        }
        Log.d(this.getClass().getSimpleName(),notificationText);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Smart Grocery Tracker")
                .setContentText(notificationText )
                .setSmallIcon(android.R.drawable.ic_menu_day)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }


}
