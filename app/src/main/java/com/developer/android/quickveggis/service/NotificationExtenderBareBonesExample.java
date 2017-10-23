package com.developer.android.quickveggis.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.developer.android.quickveggis.api.model.NotificationData;
import com.developer.android.quickveggis.db.NotificationRepo;
import com.developer.android.quickveggis.model.NotificationModel;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by Administrator on 11/8/2016.
 */

public class NotificationExtenderBareBonesExample extends NotificationExtenderService {

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        OverrideSettings overrideSettings = new OverrideSettings();

        final NotificationRepo repo = NotificationRepo.getInstance();

        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {

                int note = 0;

                if (repo.getNotifySound(getApplicationContext())) {
                    note |= Notification.DEFAULT_SOUND;
                }

                if (repo.getNotifyVibrate(getApplicationContext())) {
                    note |= Notification.DEFAULT_VIBRATE;
                }

                note |= Notification.DEFAULT_LIGHTS;

                builder.setDefaults(note);

                // Sets the background notification color to Green on Android 5.0+ devices.
                return builder;
//                return builder.setColor(new BigInteger("FF00FF00", 16).intValue());
            }
        };


        /*
        android.support.v4.app.NotificationCompat.Builder noBuilder = new android.support.v4.app.NotificationCompat.Builder(getApplicationContext());

        int note = 0;

        note = Notification.DEFAULT_SOUND;

//        note |= Notification.DEFAULT_VIBRATE;

        note |= Notification.DEFAULT_LIGHTS;

        noBuilder.setContentTitle(receivedResult.payload.title)
                .setContentText(receivedResult.payload.body )
                .setAutoCancel(true).setDefaults(note);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) Long.parseLong(receivedResult.payload.notificationID), noBuilder.build()); //0 = ID of notification
        */

        /// ====================

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);

        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

        String description = receivedResult.payload.body;

        description = receivedResult.payload.rawPayload;

        NotificationModel notification = new NotificationModel(receivedResult.payload.notificationID, receivedResult.payload.title, description, "");

        notification.save();

        int i = 4; i ++;

        i += 9;

        /*
        NotificationModel.getNewNotification(getApplicationContext());

        Notification notificationDefault = new Notification();
//        notificationDefault.defaults |= Notification.DEFAULT_SOUND;
        notificationDefault.defaults |= Notification.DEFAULT_VIBRATE;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setDefaults(notificationDefault.defaults)
                        .setContentText("Hello World!");

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        */

        return false;
    }
}
