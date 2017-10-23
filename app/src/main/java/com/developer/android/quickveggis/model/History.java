package com.developer.android.quickveggis.model;

public class History {

    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_PENDING = "Pending";
    public static final int TYPE_DELIVER = 2;
    public static final int TYPE_REDEEM = 1;
    String name;
    String status;

    public History(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
