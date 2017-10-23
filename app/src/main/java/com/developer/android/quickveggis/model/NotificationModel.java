package com.developer.android.quickveggis.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Table(name = "NotificationModel")
public class NotificationModel extends Model { //  implements Serializable

    @Column(name = "NotId")
    public String notId;

    @Column(name = "Title")
    public String title;

    @Column(name = "Content")
    public String content;

    @Column(name = "DateTime")
    public String dateTime;

    public NotificationModel() {
        super();
    }

    public NotificationModel(int ic_launcher, String alarm, long l) {
        super();
    }

    public NotificationModel(String id, String title, String content, String dateTime) {

        super();

        this.notId = id;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat();
        String formattedDate = df.format(c.getTime());

        this.dateTime = formattedDate;
    }

    public String getNotId() {
        return this.notId;
    }

    public void setNotId(String content) {
        this.notId = notId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String content) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public  static  NotificationModel getRandom() {
        return new Select().from(NotificationModel.class).orderBy("RANDOM()").executeSingle();
    }

    public static List<NotificationModel> getAll() {

        List<NotificationModel> list = null;

        try {
            list = new Select()
                    .from(NotificationModel.class)
//                .where("Category = ?", category.getId())
                .orderBy("DateTime DESC")
                    .execute();
        } catch (Exception e) {
            e = e;
        }

        return list;
    }

//    public int save(Repo repo)
//    {
//        if(repo.Users.getByUsername(notId) == null)
//        {
//            return repo.Users.create(this);
//        }
//        else
//        {
//            return repo.Users.update(this);
//        }
//    }
//
//    public int delete(Repo repo)
//    {
//        return repo.Users.delete(this);
//    }

//    public String toString()
//    {
//        return alias;
//    }

    public static void lastVisited(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.notification", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        Long date = new Date().getTime();
        String strCurrentDate = Long.toString(date);

        editor.putString("lastVisited", strCurrentDate);

        editor.commit();
    }

    public static void getNewNotification(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.notification", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        Long date = new Date().getTime();
        String strCurrentDate = Long.toString(date);

        editor.putString("newNotification", strCurrentDate);

        editor.commit();
    }

    public static String isNewNotification(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.notification", Context.MODE_PRIVATE);

        String strLastVisited = preferences.getString("lastVisited", "0");
        String strNewNotification = preferences.getString("newNotification", "0");

        if (Long.valueOf(strLastVisited) < Long.valueOf(strNewNotification)) {
            return " ";
        }

        return "0";
    }
}
