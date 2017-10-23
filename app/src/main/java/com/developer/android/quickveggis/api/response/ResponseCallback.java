package com.developer.android.quickveggis.api.response;

/**
 * Created by devmac on 30/08/16.
 */
public interface ResponseCallback<T> {
    public void onSuccess(T data);
    public void onFailure(String error);


}

