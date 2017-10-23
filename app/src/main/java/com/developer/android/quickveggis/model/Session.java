package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 01/09/16.
 */
public class Session {

    @SerializedName("session")
    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
