package com.developer.android.quickveggis.model;

import org.joda.time.DateTime;

public class TrackItem {
    int color;
    DateTime date;
    int icon;
    int title;
    String value;

    public TrackItem(int icon, int title, String value, int color) {
        this.icon = icon;
        this.title = title;
        this.value = value;
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return this.title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DateTime getDate() {
        return this.date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
