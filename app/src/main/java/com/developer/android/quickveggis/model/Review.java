package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 30/08/16.
 */
public class Review {

    @SerializedName("review_total")
    private String reviewTotal;

    public String getReviewTotal() {
        return reviewTotal;
    }

    public void setReviewTotal(String reviewTotal) {
        this.reviewTotal = reviewTotal;
    }
}
