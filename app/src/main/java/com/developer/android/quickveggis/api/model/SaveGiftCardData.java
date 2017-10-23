package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 06/09/16.
 */
public class SaveGiftCardData {

    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("giftcards_id")
    private String giftcardsId;
    @SerializedName("amount")
    private String amount;

    public SaveGiftCardData(String customerId, String giftcardsId, String amount) {
        this.customerId = customerId;
        this.giftcardsId = giftcardsId;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGiftcardsId() {
        return giftcardsId;
    }

    public void setGiftcardsId(String giftcardsId) {
        this.giftcardsId = giftcardsId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
