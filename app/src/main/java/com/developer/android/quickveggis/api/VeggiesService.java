package com.developer.android.quickveggis.api;

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
import com.developer.android.quickveggis.api.response.UnlockedTaskCartData;
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
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.ProductBanner;
import com.developer.android.quickveggis.model.Session;
import com.developer.android.quickveggis.model.Task;
import com.developer.android.quickveggis.model.Wallet;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.developer.android.quickveggis.api.ServiceConfig.ADD_BANK;
import static com.developer.android.quickveggis.api.ServiceConfig.ADD_PRODUCT_TASK_TO_CART;
import static com.developer.android.quickveggis.api.ServiceConfig.ADD_PRODUCT_TO_WISHLIST;
import static com.developer.android.quickveggis.api.ServiceConfig.ADD_REWARD;
import static com.developer.android.quickveggis.api.ServiceConfig.CREATE_ORDER;
import static com.developer.android.quickveggis.api.ServiceConfig.DELETE_BANK;
import static com.developer.android.quickveggis.api.ServiceConfig.DELETE_PRODUCT_FROM_CART;
import static com.developer.android.quickveggis.api.ServiceConfig.DELETE_PRODUCT_FROM_WISHLIST;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_ACCOUNT_DETAILS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_BANKS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_CART_ITEMS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_CATEGORIES;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_COUNTRIES;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_COUNTRIES_BY_ID;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_GIFT_CARDS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_LANGUAGES;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_MANUFACTURERS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_MANUFACTURER_BY_ID;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_ORDER_BY_CUSTOMER_ID;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_ORDER_BY_ID;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_PRODUCTORDEREDTASK_ROUTE_PARAMETER;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_PRODUCTS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_BANNER_PRODUCTS;
import static com.developer.android.quickveggis.api.ServiceConfig.CHECK_IS_WISHPRODUCT;
import static com.developer.android.quickveggis.api.ServiceConfig.SEARCH_PRODUCTS;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_PRODUCTS_BY_CATEGORY;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_EARNING_VALUES;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_PRODUCT_BY_ID;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_SESSION;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_WALLET;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_WISHLIST;
import static com.developer.android.quickveggis.api.ServiceConfig.GET_WISHLIST_1;
import static com.developer.android.quickveggis.api.ServiceConfig.GOOGLE_LOGIN;
import static com.developer.android.quickveggis.api.ServiceConfig.LOGIN_USER;
import static com.developer.android.quickveggis.api.ServiceConfig.LOGOUT_USER;
import static com.developer.android.quickveggis.api.ServiceConfig.REGISTER_USER;
import static com.developer.android.quickveggis.api.ServiceConfig.SAVE_CASH_OUT;
import static com.developer.android.quickveggis.api.ServiceConfig.SAVE_GIFT_CARD;
import static com.developer.android.quickveggis.api.ServiceConfig.SOCIAL_LOGIN;
import static com.developer.android.quickveggis.api.ServiceConfig.UPDATE_ACCOUNT;
import static com.developer.android.quickveggis.api.ServiceConfig.UPDATE_BANK;
import static com.developer.android.quickveggis.api.ServiceConfig.UPLOAD_RECEIPT_IMAGE;
import static com.developer.android.quickveggis.api.ServiceConfig.UPLOAD_TASK_GROUP_IMAGE;

/**
 * Created by devmac on 30/08/16.
 */
public interface VeggiesService {

    @GET(GET_SESSION)
    Call<BaseResponse<Session>>                         getSession(@Header("X-Oc-Merchant-Id") String merchantId);

    @GET(GET_MANUFACTURERS)
    Call<BaseResponse<ArrayList<Manufacturer>>>         getManufacturers();

    @GET(GET_MANUFACTURER_BY_ID)
    Call<BaseResponse<Manufacturer>>                    getManufacturerById(@Path("id")String manufacturerId);

    @GET(GET_CATEGORIES)
    Call<BaseResponse<ArrayList<Category>>>             getCategories(@Header("X-Oc-Merchant-Id") String merchantId);

    @GET(GET_ORDER_BY_ID)
    Call<BaseResponse<Order>>                           getOrderById(@Header("X-Oc-Merchant-Id") String merchantId,
                                                                     @Header("X-Oc-Session")String session,
                                                                     @Path("id") String orderId);

    @GET(GET_LANGUAGES)
    Call<BaseResponse<LanguageSet>>                     getLanguage();

    @GET(GET_ORDER_BY_CUSTOMER_ID)
    Call<BaseResponse<CustomerOrdersResponseData>>      getOrderByCustomerId(@Header("X-Oc-Merchant-Id") String merchantId,
                                                                             @Header("X-Oc-Session")String session,
                                                                             @Header("X-Oc-Merchant-Language")String language);

    @GET(GET_PRODUCT_BY_ID)
    Call<BaseResponse<ArrayList<Task>>>                 getProductTasksById(@Query("route") String route, @Query("product_id") String productId);

    @GET(GET_PRODUCTORDEREDTASK_ROUTE_PARAMETER)
    Call<BaseResponse<ArrayList<String>>>                 getProductOrderedTasksById(@Query("product_id") String productId, @Query("customer_id") String customerId);

    @GET(GET_PRODUCTS)
    Call<BaseResponse<ArrayList<Product>>>              getProducts();

    @GET(GET_BANNER_PRODUCTS)
    Call<BaseResponse<ArrayList<ProductBanner>>>        getBannerProducts();

    @GET(CHECK_IS_WISHPRODUCT)
    Call<BaseResponse<String>>                          checkIsWishProduct(@Query("customer_id") String customerId, @Query("product_id") String productId);

    @GET(SEARCH_PRODUCTS)
    Call<BaseResponse<ArrayList<Product>>>                          searchProducts(@Path("key") String searchKey);

    @GET(GET_PRODUCTS_BY_CATEGORY)
    Call<BaseResponse<ArrayList<Product>>>              getProductsByCategory(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                              @Path("id") String categoryId);
    @GET(GET_EARNING_VALUES)
    Call<BaseResponse<EarningValue>>                    getEarningValues(@Query("customer_id") String categoryId);

    @GET(GET_WISHLIST)
    Call<BaseResponse<WishListResponseData>>            getWishList(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                    @Header("X-Oc-Session")String session,
                                                                    @Header("X-Oc-Merchant-Language")String language);


    @GET(GET_WISHLIST_1)
    Call<BaseResponse<WishListResponseData>>            getWishList1(@Query("route") String route,
                                                                     @Query("customer_id") String customerId);


    @POST(ADD_PRODUCT_TO_WISHLIST)
    Call<BaseResponse>                                  addProductToWishList(@Header("X-Oc-Merchant-Id") String merchantId,
                                                                             @Header("X-Oc-Session")String session,
                                                                             @Header("X-Oc-Merchant-Language")String language,
                                                                             @Path("id") String productId);

    @DELETE(DELETE_PRODUCT_FROM_WISHLIST)
    Call<BaseResponse>                                  removeProductToWishList (@Header("X-Oc-Merchant-Id") String merchantId,
                                                                                 @Header("X-Oc-Session")String session,
                                                                                 @Header("X-Oc-Merchant-Language")String language,
                                                                                 @Path("id") String productId);

    @GET(GET_COUNTRIES)
    Call<BaseResponse<ArrayList<Country>>>              getCountries();

    @GET(GET_COUNTRIES_BY_ID)
    Call<BaseResponse<Country>>                         getCountryById(@Path("id") String countryId);

    @POST(ADD_REWARD)
    @FormUrlEncoded
    Call<BaseResponse>                                  addReward(@Field("reward") String rewardId);

    @POST(REGISTER_USER)
    Call<BaseResponse>                                  registerUser(@Header("X-Oc-Merchant-Id") String merchantId,
                                                                     @Header("X-Oc-Session")String session,
                                                                     @Body SignUpUserData data);

    @POST(LOGIN_USER)
    Call<BaseResponse<Customer>>                        login(@Header("X-Oc-Merchant-Id")String merchantId,
                                                              @Header("X-Oc-Session")String session,
                                                              @Body LoginUserData data);

    @POST(SOCIAL_LOGIN)
    Call<BaseResponse<Customer>>                        socialLogin(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                    @Header("X-Oc-Session")String session,
                                                                    @Body SocialLoginData data);

    @POST(GOOGLE_LOGIN)
    Call<BaseResponse<Customer>>                        googleLogin(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                    @Header("X-Oc-Session")String session,
                                                                    @Body SocialLoginData data);


    @POST(LOGOUT_USER)
    Call<BaseResponse>                                  logout(@Header("X-Oc-Merchant-Id")String merchantId,
                                                               @Header("X-Oc-Session")String session);

    @GET(GET_ACCOUNT_DETAILS)
    Call<BaseResponse<Customer>>                        getAccountDetails(/*@Header("X-Oc-Merchant-Id")String merchantId,
                                                                          @Header("X-Oc-Session")String session*/);

    @GET(GET_BANKS)
    Call<BaseResponse<ArrayList<Bank>>>                 getBanks(@Query("route") String route, @Query("customer_id") String customerId);

    @POST(DELETE_BANK)
    @FormUrlEncoded
    Call<BaseResponse<ArrayList<Bank>>>                 deleteBank(@Field("customer_id") String customerId, @Field("bank_id") String bankId);

    @POST(UPDATE_BANK)
    Call<BaseResponse<ArrayList<Bank>>>                 updateBank(@Body Bank data);

    @POST(ADD_BANK)
    Call<BaseResponse<ArrayList<Bank>>>                 addBank(@Body Bank data);

    @GET(GET_WALLET)
    Call<BaseResponse<Wallet>>                          getWallet(@Query("route") String route, @Query("customer_id") String customerId);

    @POST(UPLOAD_TASK_GROUP_IMAGE)
    @Multipart
    Call<BaseResponse>                                  uploadTaskGroupImage(@Part("order_id") RequestBody orderId, @Part MultipartBody.Part image);

    @POST(UPLOAD_RECEIPT_IMAGE)
    @Multipart
    Call<BaseResponse>                                  uploadReceiptImage(@Part("order_id") RequestBody orderId, @Part MultipartBody.Part image);

    @GET(GET_GIFT_CARDS)
    Call<BaseResponse<ArrayList<GiftCard>>>             getGiftCards(@Query("route") String route);

    @POST(ADD_PRODUCT_TASK_TO_CART)
    Call<BaseResponse<Object>>                          addProductTaskToCart(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                             @Header("X-Oc-Session")String session,
                                                                             @Body UnlockedTaskCartData data);
//    @POST(DELETE_PRODUCT_FROM_CART)
//    @FormUrlEncoded
//    Call<BaseResponse<String>>                          deleteCart(@Field("customer_id") String customerId, @Field("product_id") String cartId);

    @GET(DELETE_PRODUCT_FROM_CART)
    Call<BaseResponse<String>>                          deleteCart(@Query("customer_id") String customerId, @Query("product_id") String cartId);

    @GET(GET_CART_ITEMS)
    Call<BaseResponse<ArrayList<CartItem>>>             getCartItems(@Query("route") String route, @Query("customer_id") String customerId);

    @POST(SAVE_GIFT_CARD)
    Call<BaseResponse>                                  saveGiftCard(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                             @Header("X-Oc-Session")String session,
                                                                             @Body SaveGiftCardData data);

    @POST(SAVE_CASH_OUT)
    Call<BaseResponse>                                  saveCashOutData(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                        @Header("X-Oc-Session")String session,
                                                                        @Body SaveCashOutData data);

    @POST(UPDATE_ACCOUNT)
    Call<BaseResponse>                                  updateAccount(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                      @Header("X-Oc-Session")String session,
                                                                      @Body UpdateAccountData data);

    @POST(CREATE_ORDER)
    Call<BaseResponse<Order>>                           createOrder(@Header("X-Oc-Merchant-Id")String merchantId,
                                                                    @Header("X-Oc-Session")String session,
                                                                    @Body OrderCreateData data);
}
