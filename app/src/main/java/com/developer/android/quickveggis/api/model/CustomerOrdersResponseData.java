package com.developer.android.quickveggis.api.model;

import com.developer.android.quickveggis.model.OrderHistory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/**
 * Created by devmac on 05/09/16.
 */
public class CustomerOrdersResponseData {

    @SerializedName("orders")
    private ArrayList<OrderHistory> orders;

    public ArrayList<OrderHistory> getOrderHistory() {
        return orders;
    }

    public void setOrderHistory(ArrayList<OrderHistory> orders) {
        this.orders = orders;
    }

}
