package com.developer.android.quickveggis.model;

import java.io.Serializable;

public class PaymentType implements Serializable {
    public static final int TYPE_CARD = 2;
    public static final int TYPE_CASH = 1;
    int logo;
    String subtitle;
    int title;
    int type;

    public PaymentType(int type, int title, String subtitle, int logo) {
        this.title = title;
        this.subtitle = subtitle;
        this.logo = logo;
        this.type = type;
    }

    public int getTitle() {
        return this.title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public int getLogo() {
        return this.logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getType() {
        return this.type;
    }
}
