package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 05/09/16.
 */
public class OrderTotal {
    @SerializedName("order_total_id")
    private String orderTotalId;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("code")
    private String code;
    @SerializedName("title")
    private String title;
    @SerializedName("value")
    private String value;
    @SerializedName("sort_order")
    private String sortOrder;

    public OrderTotal(String orderTotalId, String orderId, String code, String title, String value, String sortOrder) {
        this.orderTotalId = orderTotalId;
        this.orderId = orderId;
        this.code = code;
        this.title = title;
        this.value = value;
        this.sortOrder = sortOrder;
    }

    public String getOrderTotalId() {
        return orderTotalId;
    }

    public void setOrderTotalId(String orderTotalId) {
        this.orderTotalId = orderTotalId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
