package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 06/09/16.
 */
public class SaveCashOutData {

    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("amount")
    private String amount;

    public SaveCashOutData(String customerId, String bankId, String amount) {
        this.customerId = customerId;
        this.bankId = bankId;
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
