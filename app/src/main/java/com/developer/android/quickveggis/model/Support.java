package com.developer.android.quickveggis.model;

public class Support {

    String imgIcon;
    String title;
    String content;

    public Support(String imgIcon, String title, String content) {
        this.imgIcon = imgIcon;
        this.title = title;
        this.content = content;
    }

    public String getImgIcon() {
        return this.imgIcon;
    }

    public void setImgIcon(String imgIcon) {
        this.imgIcon = imgIcon;
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

}
