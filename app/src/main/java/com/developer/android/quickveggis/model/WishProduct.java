package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 05/09/16.
 */
public class WishProduct {

    @SerializedName("product_id")
    private String productId;
    @SerializedName("thumb")
    private String thumb;
    @SerializedName("name")
    private String name;
    @SerializedName("model")
    private String model;
    @SerializedName("stock")
    private String stock;
    @SerializedName("price")
    private String price;
    @SerializedName("special")
    private String special;
    @SerializedName("variety")
    private String variety;
    @SerializedName("size")
    private String size;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
