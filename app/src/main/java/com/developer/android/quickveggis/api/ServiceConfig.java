package com.developer.android.quickveggis.api;

/**
 * Created by devmac on 30/08/16.
 */
public class ServiceConfig {

    public static final String RESULT_SERVER_ERROR = "Server Error";

//    public static final String MECHANT_ID = "12345";
    public static final String MECHANT_ID = "123";

    public static final String SERVER_ADDRESS  = "http://kikbac.com";
    public static final String FIRST_ENDPOINT = "/api/rest";
    //    http://qv.opencart-api.com/api/rest/session
//    public static final String SERVER_ADDRESS  = "http://qv.opencart-api.com";
    //    public static final String FIRST_ENDPOINT = "/api/rest";


    public static final String SECOND_ENDPOINT = "/index.php";

    public static final String GET_SESSION = FIRST_ENDPOINT + "/session";

    public static final String GET_MANUFACTURERS = FIRST_ENDPOINT + "/manufacturers";
    public static final String GET_MANUFACTURER_BY_ID = FIRST_ENDPOINT + "/manufacturers/{id}";

    public static final String GET_CATEGORIES = FIRST_ENDPOINT + "/categories";

    public static final String GET_LANGUAGES = FIRST_ENDPOINT + "/languages";

    public static final String GET_ORDER_BY_ID = FIRST_ENDPOINT + "/customerorders/{id}";
    public static final String GET_ORDER_BY_CUSTOMER_ID = FIRST_ENDPOINT + "/customerorders";

    public static final String GET_PRODUCT_BY_ID = SECOND_ENDPOINT;
    public static final String GET_PRODUCT_ROUTE_PARAMETER = "feed/rest_api/getProductTasks";
    public static final String GET_PRODUCTORDEREDTASK_ROUTE_PARAMETER = SECOND_ENDPOINT + "?route=feed/rest_api/getProductTaskInCart";;
    public static final String GET_PRODUCTS = FIRST_ENDPOINT + "/products";
//SECOND_ENDPOINT + "?route=feed/rest_api/checkProductInWishlist";
    //    public static final String GET_BANNER_PRODUCTS = FIRST_ENDPOINT + "/products";

//    http://kikbac.com/";
//    public static final String FIRST_ENDPOINT = "/demo/quickveggies/api/rest";

    public static final String GET_PRODUCTS_BY_CATEGORY = FIRST_ENDPOINT + "/products/category/{id}";
    public static final String GET_EARNING_VALUES = SECOND_ENDPOINT + "?route=feed/rest_api/lifetimeEarnings";  //  &customer_id={id}


//    public static final String GET_BANNER_PRODUCTS = FIRST_ENDPOINT + "/getBanner";
    public static final String GET_BANNER_PRODUCTS = "/index.php?route=feed/rest_api/getBanner";
//    public static final String GET_BANNER_PRODUCTS = SECOND_ENDPOINT + "?route=feed/rest_api/getBanner";

    public static final String GET_WISHLIST = FIRST_ENDPOINT + "/wishlist";
//    public static final String GET_WISHLIST = SECOND_ENDPOINT + "route=rest/wishlist/wishlist";
    public static final String ADD_PRODUCT_TO_WISHLIST = FIRST_ENDPOINT + "/wishlist/{id}";
    public static final String GET_WISHLIST_1 = SECOND_ENDPOINT;
    public static final String GET_WISHLIST_1_ROUTE_PARAMETER = "feed/rest_api/getWishlist";
    public static final String DELETE_PRODUCT_FROM_WISHLIST = FIRST_ENDPOINT + "/wishlist/{id}";

    public static final String SEARCH_PRODUCTS = FIRST_ENDPOINT + "/products/search/{key}/limit/10/page/1";  //  &customer_id&product_id

    public static final String CHECK_IS_WISHPRODUCT = SECOND_ENDPOINT + "?route=feed/rest_api/checkProductInWishlist";  //  &customer_id&product_id

    public static final String GET_COUNTRIES = FIRST_ENDPOINT + "/countries";
    public static final String GET_COUNTRIES_BY_ID = FIRST_ENDPOINT + "/countries/{id}";

    public static final String ADD_REWARD = FIRST_ENDPOINT + "/reward";

    public static final String REGISTER_USER = FIRST_ENDPOINT + "/register";
    public static final String LOGIN_USER = FIRST_ENDPOINT + "/login";
    public static final String LOGOUT_USER = FIRST_ENDPOINT + "/logout";
    public static final String GET_ACCOUNT_DETAILS = FIRST_ENDPOINT + "/account";
    public static final String SOCIAL_LOGIN = FIRST_ENDPOINT + "/sociallogin";
    public static final String GOOGLE_LOGIN = FIRST_ENDPOINT + "/googlelogin";

    public static final String GET_BANKS = SECOND_ENDPOINT;
    public static final String GET_BANKS_ROUTE_PARAMETER = "feed/rest_api/getBanks";
    public static final String DELETE_BANK = SECOND_ENDPOINT + "?route=feed/rest_api/deleteBank";
    public static final String UPDATE_BANK = SECOND_ENDPOINT + "?route=feed/rest_api/updateBank";
    public static final String ADD_BANK = SECOND_ENDPOINT + "?route=feed/rest_api/addBank";

    public static final String GET_WALLET = SECOND_ENDPOINT;
    public static final String GET_WALLET_ROUTE_PARAMETER = "feed/rest_api/getWallet";

    public static final String UPLOAD_TASK_GROUP_IMAGE = SECOND_ENDPOINT + "?route=feed/rest_api/taskGroupImageUpload";
    public static final String UPLOAD_RECEIPT_IMAGE = SECOND_ENDPOINT + "?route=feed/rest_api/taskReceiptUpload";

    public static final String DELETE_PRODUCT_FROM_CART = SECOND_ENDPOINT + "?route=feed/rest_api/cartRemove";

    public static final String GET_GIFT_CARDS = SECOND_ENDPOINT;
    public static final String GET_GIFT_CARDS_ROUTE_PARAMETER = "feed/rest_api/getGiftcards";
    public static final String SAVE_GIFT_CARD = SECOND_ENDPOINT + "?route=feed/rest_api/savegiftcard";

    public static final String ADD_PRODUCT_TASK_TO_CART = SECOND_ENDPOINT + "?route=feed/rest_api/addTaskInCart";
    public static final String GET_CART_ITEMS = SECOND_ENDPOINT;
    public static final String GET_CART_ITEMS_ROUTE_PARAMETER = "feed/rest_api/getCartProducts";

    public static final String SAVE_CASH_OUT = SECOND_ENDPOINT + "?route=feed/rest_api/savecashout";

    public static final String CREATE_ORDER = SECOND_ENDPOINT + "?route=feed/rest_api/orderPlace";

    public static final String UPDATE_ACCOUNT = FIRST_ENDPOINT + "/account";
}
