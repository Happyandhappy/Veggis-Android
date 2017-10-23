package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by devmac on 02/09/16.
 */
public class Task implements Serializable {

    @SerializedName("product_id")
    private String productId;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("amount")
    private String amount;
    @SerializedName("disable_task")
    private int disableTask;
    @SerializedName("task_data")
    private TaskData taskData;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getDisableTask() {
        return disableTask;
    }

    public void setDisableTask(int disableTask) {
        this.disableTask = disableTask;
    }

    public TaskData getTaskData() {
        return taskData;
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }

}
