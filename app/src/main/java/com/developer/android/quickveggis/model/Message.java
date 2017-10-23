package com.developer.android.quickveggis.model;

public class Message {
    public static final int TYPE_ADMIN = 1;
    public static final int TYPE_USER = 2;
    int type;

    public Message(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
