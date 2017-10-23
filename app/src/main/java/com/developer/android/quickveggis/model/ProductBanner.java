package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 10/16/2016.
 */

public class ProductBanner {
    @SerializedName("product_id")
    private String productId;
    @SerializedName("product_info")
    private Product productInfo;
    @SerializedName("link")
    private String link;
    @SerializedName("image")
    private String image;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
