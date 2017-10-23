package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OCRData {
    @SerializedName("images")
    List<String> images;
    @SerializedName("order_id")
    Long orderID;
    @SerializedName("shop_type")
    String shopType;
    @SerializedName("user_id")
    Long userID;

    public OCRData() {
        this.shopType = "offline";
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getOrderID() {
        return this.orderID;
    }

    public Long getUserID() {
        return this.userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public String getShopType() {
        return this.shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
