package com.developer.android.quickveggis.db;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 11/9/2016.
 */

@Table(name = "OneSignNotifications")
public class OneSignNotification extends Model {
    @Column(name = "NotId")
    public String notId;

    @Column(name = "Title")
    public String title;

    @Column(name = "Content")
    public String content;

    @Column(name = "DateTime")
    public String dateTime;

}
