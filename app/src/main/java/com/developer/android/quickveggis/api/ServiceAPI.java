package com.developer.android.quickveggis.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.api.model.CustomerOrdersResponseData;
import com.developer.android.quickveggis.api.model.LoginUserData;
import com.developer.android.quickveggis.api.model.OrderCreateData;
import com.developer.android.quickveggis.api.model.SaveCashOutData;
import com.developer.android.quickveggis.api.model.SaveGiftCardData;
import com.developer.android.quickveggis.api.model.SignUpUserData;
import com.developer.android.quickveggis.api.model.SocialLoginData;
import com.developer.android.quickveggis.api.model.UpdateAccountData;
import com.developer.android.quickveggis.api.model.WishListResponseData;
import com.developer.android.quickveggis.api.response.BaseResponse;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.api.response.UnlockedTaskCartData;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.controller.SessionController;
import com.developer.android.quickveggis.model.Bank;
import com.developer.android.quickveggis.model.CartItem;
import com.developer.android.quickveggis.model.Category;
import com.developer.android.quickveggis.model.Country;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.model.EarningValue;
import com.developer.android.quickveggis.model.GiftCard;
import com.developer.android.quickveggis.model.LanguageSet;
import com.developer.android.quickveggis.model.Manufacturer;
import com.developer.android.quickveggis.model.Order;
import com.developer.android.quickveggis.model.OrderHistory;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.ProductBanner;
import com.developer.android.quickveggis.model.Session;
import com.developer.android.quickveggis.model.Task;
import com.developer.android.quickveggis.model.Wallet;
import com.developer.android.quickveggis.model.WishProduct;
import com.developer.android.quickveggis.ui.utils.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.developer.android.quickveggis.api.ServiceConfig.GET_BANKS_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_CART_ITEMS_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_GIFT_CARDS_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_PRODUCT_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_WALLET_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_WISHLIST_1_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.MECHANT_ID;
import static com.developer.android.quickveggis.api.ServiceConfig.RESULT_SERVER_ERROR;
import static com.developer.android.quickveggis.api.ServiceConfig.SERVER_ADDRESS;

/**
 * Created by devmac on 30/08/16.
 */
public class ServiceAPI {

    private static final String LOG_TAG = ServiceAPI.class.getSimpleName();

    private static ServiceAPI instance = null;

    private VeggiesService mService;
    public  Call<BaseResponse> uploadReceiptImageCall;
    private Call<BaseResponse> uploadTaskGroupImageCall;

    public static ServiceAPI newInstance() {

        if (instance == null) {
            instance = new ServiceAPI();
        }

        return instance;
    }

    public ServiceAPI() {

        CookieHandler cookieHandler = new CookieManager(new PersistentCookieStore(App.getInstance().getApplicationContext()), CookiePolicy.ACCEPT_ALL);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(CookieHandler.getDefault()))
//                .addInterceptor(new AddCookiesInterceptor())
//                .addInterceptor(new ReceivedCookiesInterceptor())
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .addInterceptor(loggingInterceptor)
                .build();
//        client.interceptors().add(new AddCookiesInterceptor());
//        client.interceptors().add(new ReceivedCookiesInterceptor());

//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mService = retrofit.create(VeggiesService.class);
    }

    HashSet<String> m_cookies = new HashSet<>();

    public class AddCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            // Preference에서 cookies를 가져오는 작업을 수행
            Set<String> preferences =  m_cookies;

            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }

            // Web,Android,iOS 구분을 위해 User-Agent세팅
            builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");


            return chain.proceed(builder.build());
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {


        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> newCookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    newCookies.add(header);
                }

//                for (String old_cookie: m_cookies){
//
//                    String[] old_cookie_items = old_cookie.split("=");
//
//                    if(old_cookie_items.length > 0) {
//
//                        boolean bFound = false;
//
//                        for (String new_cookie: newCookies) {
//
//                            String[] new_cookie_items = new_cookie.split("=");
//
//                            if (new_cookie_items.length > 0 && new_cookie_items[0].equals(old_cookie_items[0])){
//                                bFound = true;
//                                break;
//                            }
//                        }
//
//                        if(!bFound) {
//                            newCookies.add(old_cookie);
//                        }
//                    }
//                }

                if (m_cookies.isEmpty())
                // To store cookies to Preference
                    m_cookies = newCookies;

            }

            return originalResponse;
        }
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public void getSession(final ResponseCallback<Session> callback){
        Call<BaseResponse<Session>> call = mService.getSession(MECHANT_ID);
        call.enqueue(new Callback<BaseResponse<Session>>() {
            @Override
            public void onResponse(Call<BaseResponse<Session>> call, Response<BaseResponse<Session>> response) {
                if (response.body().getSuccess())
                {
//                    if (response.body().getData().is)
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Session>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getManufacturers(final ResponseCallback<ArrayList<Manufacturer>> callback){
        Call<BaseResponse<ArrayList<Manufacturer>>> call = mService.getManufacturers();
        call.enqueue(new Callback<BaseResponse<ArrayList<Manufacturer>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Manufacturer>>> call, Response<BaseResponse<ArrayList<Manufacturer>>> response) {

                BaseResponse<ArrayList<Manufacturer>> strTemp = response.body();

                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Manufacturer>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getManufacturerById(String manufacturerId, final ResponseCallback<Manufacturer> callback){
        Call<BaseResponse<Manufacturer>> call = mService.getManufacturerById(manufacturerId);
        call.enqueue(new Callback<BaseResponse<Manufacturer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Manufacturer>> call, Response<BaseResponse<Manufacturer>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Manufacturer>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getCategories(final ResponseCallback<ArrayList<Category>> callback){
        Call<BaseResponse<ArrayList<Category>>> call = mService.getCategories(MECHANT_ID);
        call.enqueue(new Callback<BaseResponse<ArrayList<Category>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Category>>> call, Response<BaseResponse<ArrayList<Category>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Category>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getLanguage(final ResponseCallback<LanguageSet> callback){
        Call<BaseResponse<LanguageSet>> call = mService.getLanguage();
        call.enqueue(new Callback<BaseResponse<LanguageSet>>() {
            @Override
            public void onResponse(Call<BaseResponse<LanguageSet>> call, Response<BaseResponse<LanguageSet>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LanguageSet>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getOrderById(String orderId, final ResponseCallback<Order> callback){
        Call<BaseResponse<Order>> call = mService.getOrderById(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), orderId);
        call.enqueue(new Callback<BaseResponse<Order>>() {
            @Override
            public void onResponse(Call<BaseResponse<Order>> call, Response<BaseResponse<Order>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Order>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getOrdersByCustomerId(String session, final ResponseCallback<ArrayList<OrderHistory>> callback){
        Call<BaseResponse<CustomerOrdersResponseData>> call = mService.getOrderByCustomerId(MECHANT_ID, session, "en");
        call.enqueue(new Callback<BaseResponse<CustomerOrdersResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<CustomerOrdersResponseData>> call, Response<BaseResponse<CustomerOrdersResponseData>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData().getOrderHistory());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CustomerOrdersResponseData>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }


    public void getOrdersByCustomerId(final ResponseCallback<ArrayList<OrderHistory>> callback){
        Call<BaseResponse<CustomerOrdersResponseData>> call = mService.getOrderByCustomerId(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), "en");
        call.enqueue(new Callback<BaseResponse<CustomerOrdersResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<CustomerOrdersResponseData>> call, Response<BaseResponse<CustomerOrdersResponseData>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData().getOrderHistory());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CustomerOrdersResponseData>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getProductTasksById(String productId, final ResponseCallback<ArrayList<Task>> callback){
        Call<BaseResponse<ArrayList<Task>>> call = mService.getProductTasksById(GET_PRODUCT_ROUTE_PARAMETER, productId);
        call.enqueue(new Callback<BaseResponse<ArrayList<Task>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Task>>> call, Response<BaseResponse<ArrayList<Task>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getTaskDescription());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Task>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getProductOrderedTasksById(String customerId, String productId, final ResponseCallback<ArrayList<String>> callback){

        Call<BaseResponse<ArrayList<String>>> call = mService.getProductOrderedTasksById(productId, customerId);
        call.enqueue(new Callback<BaseResponse<ArrayList<String>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<String>>> call, Response<BaseResponse<ArrayList<String>>> response) {
                /*
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getTaskIds());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
                */
                callback.onSuccess(response.body().getTaskIds());
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<String>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getProducts(final ResponseCallback<ArrayList<Product>> callback){
        Call<BaseResponse<ArrayList<Product>>> call = mService.getProducts();
        call.enqueue(new Callback<BaseResponse<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Product>>> call, Response<BaseResponse<ArrayList<Product>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Product>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    //  =================  Working now ===============
    public void getCustomerEarningValues(String customerId, final ResponseCallback<EarningValue> callback){

        Call<BaseResponse<EarningValue>> call = mService.getEarningValues(customerId);
        call.enqueue(new Callback<BaseResponse<EarningValue>>() {
            @Override
            public void onResponse(Call<BaseResponse<EarningValue>> call, Response<BaseResponse<EarningValue>> response) {

                //  callback.onSuccess(response.body().getBannerData());
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<EarningValue>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    //  =================  Banner ===============
    public void getBannerProducts(final ResponseCallback<ArrayList<ProductBanner>> callback){

        Call<BaseResponse<ArrayList<ProductBanner>>> call = mService.getBannerProducts();
        call.enqueue(new Callback<BaseResponse<ArrayList<ProductBanner>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<ProductBanner>>> call, Response<BaseResponse<ArrayList<ProductBanner>>> response) {

                //  callback.onSuccess(response.body().getBannerData());
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getBannerData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<ProductBanner>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    //  ================= Search Products ===============
    public void searchProducts(String searchKey, final ResponseCallback<ArrayList<Product>> callback){

        Call<BaseResponse<ArrayList<Product>>> call = mService.searchProducts(searchKey);  //
        call.enqueue(new Callback<BaseResponse<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Product>>> call, Response<BaseResponse<ArrayList<Product>>> response) {

                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Product>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void checkIsWishProduct(String customerID, String productID, final ResponseCallback<String> callback){

        Call<BaseResponse<String>> call = mService.checkIsWishProduct(customerID, productID);  //
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getMessage());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getProductsByCategory(String categoryId, final ResponseCallback<ArrayList<Product>> callback){
        Call<BaseResponse<ArrayList<Product>>> call = mService.getProductsByCategory(MECHANT_ID, categoryId);
        call.enqueue(new Callback<BaseResponse<ArrayList<Product>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Product>>> call, Response<BaseResponse<ArrayList<Product>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Product>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getWishList(final ResponseCallback<ArrayList<WishProduct>> callback){
        Call<BaseResponse<WishListResponseData>> call = mService.getWishList(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), "en");
        call.enqueue(new Callback<BaseResponse<WishListResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<WishListResponseData>> call, Response<BaseResponse<WishListResponseData>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData().getProducts());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<WishListResponseData>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getWishList1(String customerId, final ResponseCallback<ArrayList<WishProduct>> callback){
        Call<BaseResponse<WishListResponseData>> call = mService.getWishList1(GET_WISHLIST_1_ROUTE_PARAMETER, customerId);
        call.enqueue(new Callback<BaseResponse<WishListResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<WishListResponseData>> call, Response<BaseResponse<WishListResponseData>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData().getProducts());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<WishListResponseData>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void addProductToWishList(String productId, final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.addProductToWishList(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), "en", productId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getTotal());
                }
                else {
                    if (response.body().getInfo() != null)
                        callback.onFailure(response.body().getInfo());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void removeProductFromWishList(String productId, final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.removeProductToWishList(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), "en", productId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getInfo() != null)
                        callback.onFailure(response.body().getInfo());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getCountries(final ResponseCallback<ArrayList<Country>> callback){
        Call<BaseResponse<ArrayList<Country>>> call = mService.getCountries();
        call.enqueue(new Callback<BaseResponse<ArrayList<Country>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Country>>> call, Response<BaseResponse<ArrayList<Country>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Country>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getCountryById(String countryId, final ResponseCallback<Country> callback){
        Call<BaseResponse<Country>> call = mService.getCountryById(countryId);
        call.enqueue(new Callback<BaseResponse<Country>>() {
            @Override
            public void onResponse(Call<BaseResponse<Country>> call, Response<BaseResponse<Country>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Country>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    // TODO: 31/08/16 change callback
    public void addReward(String rewardId, final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.addReward(rewardId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void registerUser(String countryId, String email, String firstName, String lastName, String password, String confirm, String agree, final ResponseCallback<String> callback){
        SignUpUserData signUpUserData = new SignUpUserData("here", "", "city", "", "", countryId, email, "", firstName, lastName, "", "", "123456789", "1", password, confirm, agree);
        Call<BaseResponse> call = mService.registerUser(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), signUpUserData);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure(response.body().getError().toString());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void logout(final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.logout(MECHANT_ID, SessionController.getInstance().getLoggedInSession());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    CustomerController.getInstance().logoutUser();
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void login(String email, String password, final ResponseCallback<Customer> callback){
        LoginUserData loginUserData = new LoginUserData(email, password);
        Call<BaseResponse<Customer>> call = mService.login(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), loginUserData);
        call.enqueue(new Callback<BaseResponse<Customer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure(response.body().getError().toString());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void socialLogin(SocialLoginData data, final ResponseCallback<Customer> callback){
        Call<BaseResponse<Customer>> call = mService.socialLogin(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse<Customer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure(response.body().getError().toString());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void googleLogin(SocialLoginData data, final ResponseCallback<Customer> callback){
        Call<BaseResponse<Customer>> call = mService.googleLogin(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse<Customer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure(response.body().getError().toString());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getAccountDetails(final ResponseCallback<Customer> callback){
        Call<BaseResponse<Customer>> call = mService.getAccountDetails(/*MECHANT_ID, SessionController.getInstance().getLoggedInSession()*/);
        call.enqueue(new Callback<BaseResponse<Customer>>() {
            @Override
            public void onResponse(Call<BaseResponse<Customer>> call, Response<BaseResponse<Customer>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Customer>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getBanks(String customerId, final ResponseCallback<ArrayList<Bank>> callback){
        Call<BaseResponse<ArrayList<Bank>>> call = mService.getBanks(GET_BANKS_ROUTE_PARAMETER, customerId);
        call.enqueue(new Callback<BaseResponse<ArrayList<Bank>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Bank>>> call, Response<BaseResponse<ArrayList<Bank>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Bank>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void deleteBank(String customerId, String bankId, final ResponseCallback<ArrayList<Bank>> callback){
        Call<BaseResponse<ArrayList<Bank>>> call = mService.deleteBank(customerId, bankId);
        call.enqueue(new Callback<BaseResponse<ArrayList<Bank>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Bank>>> call, Response<BaseResponse<ArrayList<Bank>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Bank>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void updateBank(Bank bank, final ResponseCallback<ArrayList<Bank>> callback){
        Call<BaseResponse<ArrayList<Bank>>> call = mService.updateBank(bank);
        call.enqueue(new Callback<BaseResponse<ArrayList<Bank>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Bank>>> call, Response<BaseResponse<ArrayList<Bank>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Bank>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void addBank(Bank bank, final ResponseCallback<ArrayList<Bank>> callback){
        Call<BaseResponse<ArrayList<Bank>>> call = mService.addBank(bank);
        call.enqueue(new Callback<BaseResponse<ArrayList<Bank>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<Bank>>> call, Response<BaseResponse<ArrayList<Bank>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<Bank>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }


    public void getWallet(String customerId, final ResponseCallback<Wallet> callback) {
        Call<BaseResponse<Wallet>> call = mService.getWallet(GET_WALLET_ROUTE_PARAMETER, customerId);
        call.enqueue(new Callback<BaseResponse<Wallet>>() {
            @Override
            public void onResponse(Call<BaseResponse<Wallet>> call, Response<BaseResponse<Wallet>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Wallet>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void uploadTaskGroupImage(String orderId, Uri imagePath, final ResponseCallback<String> callback){
        MultipartBody.Part imageBody = null;
        if (null != imagePath) {
            File file = new File(imagePath.getPath());

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            imageBody = MultipartBody.Part.createFormData("receipt_images[]", "receipt.jpg", reqFile);
        }

        RequestBody orderIdBody = RequestBody.create(MediaType.parse("text/plain"), orderId);

        uploadTaskGroupImageCall = mService.uploadTaskGroupImage(orderIdBody, imageBody);
        uploadTaskGroupImageCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public Bitmap getBitmap(String path) {
        try {
            Bitmap bitmap=null;
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}

    public Bitmap getBitmapFromFile(File f) {
        try {
            Bitmap bitmap=null;
//            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}

    public void uploadReceiptImage(String orderId, Uri imagePath, final ResponseCallback<String> callback){

        Bitmap bitmap = this.getBitmap(imagePath.getPath());

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();


        MultipartBody.Part imageBody = null;
        if (null != imagePath) {
            File file = new File(imagePath.getPath());

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            imageBody = MultipartBody.Part.createFormData("receipt_images[]", "receipt.jpg", reqFile);
        }

        RequestBody orderIdBody = RequestBody.create(MediaType.parse("text/plain"), orderId);

        uploadReceiptImageCall  = mService.uploadReceiptImage(orderIdBody, imageBody);
        uploadReceiptImageCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getGiftCards(final ResponseCallback<ArrayList<GiftCard>> callback){
        Call<BaseResponse<ArrayList<GiftCard>>> call = mService.getGiftCards(GET_GIFT_CARDS_ROUTE_PARAMETER);
        call.enqueue(new Callback<BaseResponse<ArrayList<GiftCard>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<GiftCard>>> call, Response<BaseResponse<ArrayList<GiftCard>>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<GiftCard>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void addProductTaskToCart(UnlockedTaskCartData data, final ResponseCallback<String> callback){
        Call<BaseResponse<Object>> call = mService.addProductTaskToCart(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }
//    deleteCard

    public void deleteCart(String productID, final ResponseCallback<String> callback){

        String customerID = CustomerController.getInstance().getLoggedInCustomer().getCustomerId();

        Call<BaseResponse<String>> call = mService.deleteCart(customerID, productID);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.body().getSuccess() )
                {
                    callback.onSuccess("");
                }
                else {
                    callback.onFailure((String)response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void getCartItems(final ResponseCallback<ArrayList<CartItem>> callback){
        Call<BaseResponse<ArrayList<CartItem>>> call = mService.getCartItems(GET_CART_ITEMS_ROUTE_PARAMETER, CustomerController.getInstance().getLoggedInCustomer().getCustomerId());
        call.enqueue(new Callback<BaseResponse<ArrayList<CartItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<CartItem>>> call, Response<BaseResponse<ArrayList<CartItem>>> response) {
                if (response.body().getCartData() != null)
                {
                    callback.onSuccess(response.body().getCartData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<CartItem>>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void cancelUpload(boolean isReceipt){
        if (isReceipt)
        {
            if (uploadReceiptImageCall != null)
                uploadReceiptImageCall.cancel();
        }
        else {
            if (uploadTaskGroupImageCall != null)
                uploadTaskGroupImageCall.cancel();
        }
    }

    public void saveGiftCard(SaveGiftCardData data, final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.saveGiftCard(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void saveCashOut(SaveCashOutData data, final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.saveCashOutData(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void updateAccount(UpdateAccountData data, final ResponseCallback<String> callback){
        Call<BaseResponse> call = mService.updateAccount(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess("success");
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }

    public void createOrder(OrderCreateData data, final ResponseCallback<Order> callback){
        Call<BaseResponse<Order>> call = mService.createOrder(MECHANT_ID, SessionController.getInstance().getLoggedInSession(), data);
        call.enqueue(new Callback<BaseResponse<Order>>() {
            @Override
            public void onResponse(Call<BaseResponse<Order>> call, Response<BaseResponse<Order>> response) {
                if (response.body().getSuccess())
                {
                    callback.onSuccess(response.body().getData());
                }
                else {
                    if (response.body().getError() != null)
                        callback.onFailure((String)response.body().getError());
                    else
                        callback.onFailure(RESULT_SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Order>> call, Throwable t) {
                callback.onFailure(RESULT_SERVER_ERROR);
            }
        });
    }
}
