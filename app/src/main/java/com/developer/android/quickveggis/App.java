package com.developer.android.quickveggis;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.crashlytics.android.Crashlytics;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Category;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.snappydb.DB;
import com.snappydb.SnappyDB;
import com.snappydb.SnappydbException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
    public static final String DEVELOPER_KEY = "AIzaSyDE14XnoMMPy5hIwwnxb7gYodeqS4aCrxU";
    public static boolean bool = false;

    private static App instance;

    private EventBus eventBus;
    private DB db;

    static Context context;

    public static boolean launched = false;
    public static boolean wishlistChanged = true;
    public static boolean historyChanged = true;
    public static boolean shoppingListChanged = true;

    public static ArrayList<Category> categories = new ArrayList();

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

         /*
            //      OneSignal
         */
        OneSignal.enableSound(true);
        OneSignal.enableVibrate(true);


        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.WARN);
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .autoPromptLocation(true).
                init();

        /*
          ActiveAndroid.initialize(this);
         */
        ActiveAndroid.initialize(this);

        context = getApplicationContext();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        configureDatabase();
        configureEventBus();
    }

    public static Context getContext() {
        return context;
    }

    public static void setTask(boolean b){
        bool = b;
    }

    public static boolean getTask() {
        return bool;
    }

    public void intentShare(Activity act, String subject, String des) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, des);
        act.startActivityForResult(Intent.createChooser(share, "Share"), Config.REQUEST_CODE_SHARE);
    }

    private void configureDatabase() {
        try {
            db = new SnappyDB.Builder(this)
                    //.directory(Environment.getExternalStorageDirectory().getAbsolutePath()) //optional
                    .name("veggies_db")
                    .build();
        } catch (SnappydbException e) {
            Log.d("error", "Unable to create database");
        }
    }

    private void configureEventBus() {

        EventBusBuilder builder = EventBus.builder();
        eventBus = builder.build();
    }

    public DB getDb() {
        return db;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {

            /*
             To get Notification title and body
             */

            String title = result.notification.payload.title;

            String description = result.notification.payload.body;

            Log.i("OneSignal message - ", description);


            /*
                    To get current date
             */
//            Calendar c = Calendar.getInstance();
//            System.out.println("Current time => "+c.getTime());
//
//            SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm am");
//            String formattedDate = df.format(c.getTime());
//
//
//            Notification notification = new Notification(title, description, formattedDate);
//            NotificationRepo notificationRepo = NotificationRepo.getInstance();
//
//            notificationRepo.saveNotification(notification);
//            /*
//
//             */
//            OSNotificationAction.ActionType actionType = result.action.type;
//            JSONObject data = result.notification.payload.additionalData;
//            String customKey;
//
//            if (data != null) {
//                customKey = data.optString("customkey", null);
//                if (customKey != null)
//                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
//            }
//
//            if (actionType == OSNotificationAction.ActionType.ActionTaken)
//                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            // The following can be used to open an Activity of your choice.

            // Intent intent = new Intent(getApplication(), YourActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(intent);

            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //  if you are calling startActivity above.
         /*
            <application ...>
              <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
            </application>
         */

        }
    }
}
