package com.developer.android.quickveggis.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.api.model.NotificationData;
import com.developer.android.quickveggis.model.NotificationModel;
import com.j256.ormlite.stmt.QueryBuilder;
import com.snappydb.DB;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/31/2016.
 */

public class NotificationRepo {
    private static NotificationRepo instance;
    private DatabaseHelper helper;

    private static String SoundKey = "NotifyOptionSound";
    private static String VibrateKey = "NotifyOptionVibration";

    public static NotificationRepo getInstance() {
        if (instance == null) {
            instance = new NotificationRepo(App.getContext());
        }
        return instance;
    }

    private NotificationRepo(Context ctx) {
        this.helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return this.helper;
    }

    public long getCount() {
        try {
            return getHelper().getNotificationDao().queryBuilder().countOf();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<NotificationModel> getList() {
        try {
            return getHelper().getNotificationDao().queryForAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveNotification(NotificationModel notification) {
        try {
            getHelper().getNotificationDao().createOrUpdate(notification);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static final String KEY_NOTIFICATOIN_FROM_ONESIGNAL = "onsigian_notifications";

    public void addNotification(NotificationModel notification) {

        NotificationData notificationData = this.getNotificationData();
        notificationData.getNotifications().add(notification);

        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_NOTIFICATOIN_FROM_ONESIGNAL, notificationData);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to login");
        }
    }

    public void saveNotificationData(NotificationData notifications) {

        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_NOTIFICATOIN_FROM_ONESIGNAL, notifications);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to login");
        }
    }

    public NotificationData getNotificationData() {

        NotificationData notifications = null;

        DB db = App.getInstance().getDb();
        try {
            notifications = db.getObject(KEY_NOTIFICATOIN_FROM_ONESIGNAL, NotificationData.class);
        } catch (SnappydbException e) {
            Log.d("error", "Failed on getLoggedInUser");
        }

        if (notifications == null) {
            notifications = new NotificationData();
            notifications.setNotifications(new ArrayList<NotificationModel>());
        }

        return notifications;
    }

    public void setNotifyOption(boolean sound, boolean vibrate, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SoundKey, String.valueOf(sound));
        editor.putString(VibrateKey, String.valueOf(vibrate));

        editor.commit();
    }

    public void setNotifySound(boolean sound, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SoundKey, String.valueOf(sound));

        editor.commit();
    }

    public void setNotifyVibrate(boolean vibrate, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(VibrateKey, String.valueOf(vibrate));

        editor.commit();
    }

    public boolean getNotifySound(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        String string = preferences.getString(SoundKey,"true");

        return Boolean.valueOf(string);
    }

    public boolean getNotifyVibrate(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        String string = preferences.getString(VibrateKey,"true");

        return Boolean.valueOf(string);
    }
}
