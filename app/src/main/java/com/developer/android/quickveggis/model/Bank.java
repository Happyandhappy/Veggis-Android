package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 31/08/16.
 */
public class Bank {

    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("name")
    private String name;
    @SerializedName("branch")
    private String branch;
    @SerializedName("ifsc")
    private String ifsc;
    @SerializedName("accountnumber")
    private String accountnumber;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }
}
