package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

public class RedeemData extends RequestData {
    @SerializedName("ocrdata")
    OCRData ocrData;

    public OCRData getOcrData() {
        return this.ocrData;
    }

    public void setOcrData(OCRData ocrData) {
        this.ocrData = ocrData;
    }
}
