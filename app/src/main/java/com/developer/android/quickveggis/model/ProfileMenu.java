package com.developer.android.quickveggis.model;

public class ProfileMenu {
    boolean checkable;
    int id;
    int title;
    String value;

    public ProfileMenu(int title, int id) {
        this.title = title;
        this.id = id;
    }

    public ProfileMenu(int title, int id, boolean checkable) {
        this.title = title;
        this.id = id;
        this.checkable = checkable;
    }

    public ProfileMenu(int title, int id, String value) {
        this.title = title;
        this.id = id;
        this.value = value;
    }

    public int getTitle() {
        return this.title;
    }

    public void setValue(String value){ this.value = value;}

    public String getValue(){ return this.value;}

    public void setTitle(int title) {
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }
}
