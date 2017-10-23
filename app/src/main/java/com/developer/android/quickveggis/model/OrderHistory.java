package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 05/09/16.
 */
public class OrderHistory {

    @SerializedName("order_id")
    private String orderId;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("date_added")
    private String dateAdded;
    @SerializedName("products")
    private int products;
    @SerializedName("total")
    private String total;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getProducts() {
        return products;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
