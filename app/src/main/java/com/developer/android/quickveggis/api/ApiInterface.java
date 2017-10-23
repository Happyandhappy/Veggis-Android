package com.developer.android.quickveggis.api;

import com.developer.android.quickveggis.api.model.RedeemData;
import com.developer.android.quickveggis.api.response.OCRResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiInterface {
    @POST("index.php?route=checkout/confirm/ocrData")
    Observable<OCRResponse> sendOCR(@Body RedeemData redeemData);
}
