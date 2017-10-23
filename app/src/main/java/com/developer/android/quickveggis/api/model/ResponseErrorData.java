package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 02/09/16.
 */
public class ResponseErrorData {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm")
    private String confirm;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
