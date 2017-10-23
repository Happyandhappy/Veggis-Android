package com.developer.android.quickveggis.api.model;

import com.developer.android.quickveggis.model.WishProduct;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by devmac on 07/09/16.
 */
public class WishListResponseData {

    @SerializedName("products")
    private ArrayList<WishProduct> products;

    public ArrayList<WishProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<WishProduct> products) {
        this.products = products;
    }
}
