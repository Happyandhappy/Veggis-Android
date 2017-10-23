package com.developer.android.quickveggis.model;

import java.util.ArrayList;

/**
 * Created by devmac on 05/09/16.
 */
public class OrderHistorySection {

    int type;
    ArrayList<OrderHistory> aryOrders;

    public OrderHistorySection(int type) {
        this.type = type;
        aryOrders = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<OrderHistory> getAryOrders() {
        return aryOrders;
    }

    public void setAryOrders(ArrayList<OrderHistory> aryOrders) {
        this.aryOrders = aryOrders;
    }
}
