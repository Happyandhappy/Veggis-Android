package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 12/15/2016.
 */

public class EarningValue {

    @SerializedName("lifetime_earnings")
    private String lifetime;
    @SerializedName("wallet_earnings")
    private String wallet;
    @SerializedName("total_pending_earnings")
    private String totalPending;

    public String getLifetime() {
        return lifetime;
    }

    public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getTotalPending() {
        return totalPending;
    }

    public void setTotalPending(String lifetime) {
        this.totalPending = totalPending;
    }
}
