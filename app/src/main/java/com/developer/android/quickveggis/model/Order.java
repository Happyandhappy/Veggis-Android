package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devmac on 30/08/16.
 */
public class Order {

    @SerializedName("invoice_no")
    private String invoiceNo;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("date_added")
    private String dateAdded;
    @SerializedName("payment_address")
    private String paymentAddress;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("shipping_address")
    private String shippingAddress;
    @SerializedName("shipping_method")
    private String shippingMethod;
    @SerializedName("comment")
    private String comment;
    @SerializedName("products")
    private List<ProductsBean> products;
    @SerializedName("vouchers")
    private List<?> vouchers;
    @SerializedName("totals")
    private ArrayList<OrderTotal> totals;
    @SerializedName("histories")
    private List<?> histories;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public List<?> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<?> vouchers) {
        this.vouchers = vouchers;
    }

    public ArrayList<OrderTotal> getTotals() {
        return totals;
    }

    public void setTotals(ArrayList<OrderTotal> totals) {
        this.totals = totals;
    }

    public List<?> getHistories() {
        return histories;
    }

    public void setHistories(List<?> histories) {
        this.histories = histories;
    }

    public static class ProductsBean {
        @SerializedName("image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("model")
        private String model;
        @SerializedName("quantity")
        private String quantity;
        @SerializedName("price")
        private String price;
        @SerializedName("total")
        private String total;
        @SerializedName("return")
        private String returnX;
        @SerializedName("option")
        private List<?> option;

        public String getImageIcon() {
            return image;
        }

        public void setImageIcon(String image) {
            this.image = image;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getReturnX() {
            return returnX;
        }

        public void setReturnX(String returnX) {
            this.returnX = returnX;
        }

        public List<?> getOption() {
            return option;
        }

        public void setOption(List<?> option) {
            this.option = option;
        }
    }

}
