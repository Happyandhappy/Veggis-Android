package com.developer.android.quickveggis.api.model;

import com.developer.android.quickveggis.model.NotificationModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/8/2016.
 */

public class NotificationData {

    @SerializedName("testData")
    private List<String> testdataList;

    @SerializedName("notification")
    private List<NotificationModel> notificationList;

    private ArrayList<NotificationModel> notifications;

    public ArrayList<NotificationModel> getNotifications() {

        this.testdataList.add("addData");

        this.notifications = new ArrayList<>(this.notificationList);

        return this.notifications;
    }

    public void setNotifications(ArrayList<NotificationModel> notifications) {

        testdataList = new ArrayList<>();
        testdataList.add("sd1");
        testdataList.add("sd2");

        this.notificationList = new ArrayList<>(notifications);

        this.notifications = notifications;
    }




}
