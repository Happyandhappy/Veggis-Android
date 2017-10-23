package com.developer.android.quickveggis.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 02/09/16.
 */
public class UnlockedTaskCartData {

    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("task_amount")
    private String taskAmount;
    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("quantity")
    private String quantity;

    public UnlockedTaskCartData(String customerId, String productId, String taskId, String taskAmount, String sessionId, String quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.taskId = taskId;
        this.taskAmount = taskAmount;
        this.sessionId = sessionId;
        this.quantity = quantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskAmount() {
        return taskAmount;
    }

    public void setTaskAmount(String taskAmount) {
        this.taskAmount = taskAmount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
