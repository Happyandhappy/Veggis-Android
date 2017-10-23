package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 31/08/16.
 */
public class GiftCard {

    @SerializedName("giftcards_id")
    private String giftcardsId;
    @SerializedName("name")
    private String name;
    @SerializedName("amount")
    private String amount;
    @SerializedName("image")
    private String image;
    @SerializedName("sort_order")
    private String sortOrder;

    public String getGiftcardsId() {
        return giftcardsId;
    }

    public void setGiftcardsId(String giftcardsId) {
        this.giftcardsId = giftcardsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
