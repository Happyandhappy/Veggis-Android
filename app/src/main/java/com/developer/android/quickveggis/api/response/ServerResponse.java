package com.developer.android.quickveggis.api.response;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";
    @SerializedName("result")
    String result;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
