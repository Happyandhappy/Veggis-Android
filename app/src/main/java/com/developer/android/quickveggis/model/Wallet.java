package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 30/08/16.
 */
public class Wallet {

    @SerializedName("wallet_total")
    private String walletTotal;
    @SerializedName("pending_earnings")
    private String pendingEarnings;

    public Wallet() {
    }

    public Wallet(String walletTotal, String pendingEarnings) {
        this.walletTotal = walletTotal;
        this.pendingEarnings = pendingEarnings;
    }

    public String getWalletTotal() {
        return walletTotal;
    }

    public void setWalletTotal(String walletTotal) {
        this.walletTotal = walletTotal;
    }

    public String getPendingEarnings() {
        return pendingEarnings;
    }

    public void setPendingEarnings(String pendingEarnings) {
        this.pendingEarnings = pendingEarnings;
    }
}
