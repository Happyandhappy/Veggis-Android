package com.developer.android.quickveggis.config;

import com.developer.android.quickveggis.model.BankAccount;
import com.developer.android.quickveggis.model.GiftCard;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.TaskData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 4/21/2016.
 */
public final class Config {
    public static final int SPLASH_DELAY_TIME = 1000;
    public static final int ONLINE_MODE = 1;
    public static final int OFFLINE_MODE = 2;
    public static final int PRODUCT_MODE = 3;
    public static final int PRODUCT_RESULT = 513;

    public static final String MyPreferences                = "VeggisPrefs";
    public static final String KEY_FB_LOGGEDIN              = "fb_login";
    public static final String KEY_GOOGLE_LOGGEDIN          = "google_login";
    public static final String KEY_FB_ACCESS_TOKEN          = "fb_access_token";
    public static final String KEY_GOOGLE_ACCESS_TOKEN      = "google_access_token";
    public static final String KEY_FB_ACCOUNT_EMAIL         = "fb_email";
    public static final String KEY_GOOGLE_ACCOUNT_EMAIL     = "google_email";
    public static final String KEY_TASK_TYPE                = "task_type";
    public static final String KEY_UNLOCKED_TASK_ID         = "unlocked_task_id";
    public static final String PRODUCTS_TUTORIAL_VISIBLE    = "products_tutorial_visible";
    public static final String PRODUCT_TUTORIAL_VISIBLE     = "product_tutorial_visible";
    public static final String CART_TUTORIAL_VISIBLE        = "cart_tutorial_visible";

    public static final String KEY_SESSION_ID               = "session_id";
    public static final String KEY_SELECTED_CATEGORY_ID     = "selected_catId";
    public static final String KEY_API_HEADER_LANGUAGE      = "X-Oc-Merchant-Language";
    public static String SESSION_ID;

    public static final String KEY_API_HEADER_SECURITY      = "X-Oc-Merchant-Id";
    public static final String KEY_API_HEADER_SESSION       = "X-Oc-Session";
    public static final String KEY_API_HEADER_CONTENT_TYPE  = "Content-Type";

    public static final String API_BASE_URL                 = "http://dev.kikbac.com/api/rest";
    public static final String API_GET_CATEGORY_LIST        = "/categories";
    public static final String API_LOGIN                    = "/login";
    public static final String API_GET_SESSION              = "/session";
    public static final String API_SOCIAL_LOGIN             = "/sociallogin";
    public static final String API_LOGOUT                   = "/logout";
    public static final String API_SIGNUP                   = "/register";
    public static final String API_GET_PRODUCT_LIST         = "/products/category/";
    public static final String API_GET_WISHLIST             = "http://dev.kikbac.com/index.php?route=feed/rest_api/getWishlist&customer_id=";
    public static final String API_GET_WISHLIST1            = "http://dev.kikbac.com/api/rest/wishlist";
    public static final String API_GET_TASKS                = "http://dev.kikbac.com/index.php?route=feed/rest_api/getProductTasks&product_id=";
    public static final String API_GET_WALLET_AMOUNT        = "http://dev.kikbac.com/index.php?route=feed/rest_api/getWallet&customer_id=";
    public static final String API_GET_BANKACCOUNT          = "http://dev.kikbac.com/index.php?route=feed/rest_api/getBanks&customer_id=";
    public static final String API_GET_GIFTCARDS            = "http://dev.kikbac.com/index.php?route=feed/rest_api/getGiftcards";

    public static final String API_ADD_NEW_ACCOUNT          = "http://dev.kikbac.com/index.php?route=feed/rest_api/addBank";
    public static final String API_UPDATE_BANK              = "http://dev.kikbac.com/index.php?route=feed/rest_api/updateBank";

    public static final String API_GET_ORDER_LIST           = "http://dev.kikbac.com/api/rest/customerorders";
    public static final String API_SAVE_CASHOUT             = "http://dev.kikbac.com/index.php?route=feed/rest_api/savecashout";
    public static final String API_SAVE_GIFTCARD            = "http://dev.kikbac.com/index.php?route=feed/rest_api/savegiftcard";

    public static final String API_UPLOAD_RECEIPT_IMAGES    = "http://dev.kikbac.com/index.php?route=feed/rest_api/taskReceiptUpload";
    public static final String API_UPLOAD_GROUP_IMAGE       = "http://dev.kikbac.com/index.php?route=feed/rest_api/taskGroupImageUpload";

    public static final String API_ADD_PRODUCT_WISHLIST     = "http://dev.kikbac.com/api/rest/wishlist/";
    public static final String API_DELETE_PRODUCT_WISHLIST  = "http://dev.kikbac.com/api/rest/wishlist/";

    //cart API
    public static final String API_ADD_TASK_TO_CART         = "http://dev.kikbac.com/index.php?route=feed/rest_api/addTaskInCart";
    public static final String API_GET_CART_LIST            = "http://dev.kikbac.com/index.php?route=feed/rest_api/getCartProducts&customer_id=";
    public static final String API_REMOVE_PRODUCT_TO_CART   = "http://dev.kikbac.com/index.php?route=feed/rest_api/cartRemove";

    public static String FB_ACCESS_TOKEN;
    public static String GOOGLE_ACCESS_TOKEN;

    public static String FB_ACCOUNT_EMAIL;
    public static String GOOGLE_ACCOUNT_EMAIL;

    public static String[] taskTitleArray = {"Testify", "Recipe", "Survey", "Trivia", "Poll", "Fact", "Nutrition Fact", "Let's Battle", "Do Gooder"};


    public static final int TASK_LETS_BATTLE = 9;
    public static final int TASK_TRIVIA      = 4;
    public static final int TASK_FACT        = 6;
    public static final int TASK_POLL        = 5;
    public static final int TASK_NUTRITION   = 7;
    public static final int TASK_RECIPE      = 2;
    public static final int TASK_SURVEY      = 3;
    public static final int TASK_DO_GOODER   = 10;
    public static final int TASK_VIDEO       = 8;
    public static final int TASK_TESTIFY     = 1;

    public static List<Product> list_products       = new ArrayList<>();
    public static List<TaskData>              list_tasks          = new ArrayList<>();
//    public static List<CartProductIdentifier> list_Identifiers    = new ArrayList<>();
//    public static List<WishData>              list_wishData       = new ArrayList<>();
    public static List<BankAccount>           list_banks          = new ArrayList<>();
    public static List<GiftCard>              list_gifts          = new ArrayList<>();

    public static List<Product>               list_cart           = new ArrayList<>();

    public static ArrayList<String>           list_receipt_images = new ArrayList<>();
    public static ArrayList<String>           list_group_images   = new ArrayList<>();

    public static TaskData selectedTask;

    public static final String GOOGLE_DEVELOPER_KEY = "AIzaSyCo376cBKvX2UJqEPHFSgeLF_Cdj0HCO7Y";

    public static final int REQUEST_CODE_SHARE = 1001;

}
