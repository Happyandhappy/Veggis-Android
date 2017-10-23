package com.developer.android.quickveggis.model;

public class Card {
    String link;
    int res;

    public Card(String link) {
        this.link = link;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRes() {
        return this.res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
