package com.developer.android.quickveggis.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 30/08/16.
 */
public class BaseResponse<T> {

    @SerializedName("success")
    private String success;
    @SerializedName("error")
    private Object error;
    @SerializedName("total")
    private String total;
    @SerializedName("info")
    private String info;
    @SerializedName("data")
    private T data;
    @SerializedName("task_descrition")
    private T taskDescription;
    @SerializedName("taskIds")
    private T taskIds;
    @SerializedName("cart_data")
    private T cartData;
    @SerializedName("banners")
    private T bannerData;
    @SerializedName("message")
    private T message;

    public boolean getSuccess() {
        if (success == null)
            return false;
        boolean boolSuccess = !success.equals("false");
        return boolSuccess;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(T taskDescription) {
        this.taskDescription = taskDescription;
    }

    public T getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(T taskIds) {
        this.taskIds = taskIds;
    }

    public T getCartData() {
        return cartData;
    }

    public void setCartData(T cartData) {
        this.cartData = cartData;
    }

    public T getBannerData() {
        return bannerData;
    }

    public void setBannerData(T bannerData) {
        this.bannerData = bannerData;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
