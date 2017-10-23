package com.developer.android.quickveggis.api;

public class ApiRequest {
    public static final String MAIN_URL = "http://exceedwiz.com/qv/";
    static ApiRequest apiRequest;
    ApiInterface api;

    public static ApiRequest getInstance() {
        if (apiRequest == null) {
            apiRequest = new ApiRequest();
        }
        return apiRequest;
    }

    public ApiRequest() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(Level.BODY);
//        this.api = (ApiInterface) new Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(MAIN_URL).client(new OkHttpClient.Builder().addInterceptor(interceptor).build()).build().create(ApiInterface.class);
//        OkHttpClient client = new OkHttpClient.Builder().build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(SERVER_ADDRESS)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//        mService = retrofit.create(VebebeService.class);
    }

    public ApiInterface getApi() {
        return this.api;
    }
}
