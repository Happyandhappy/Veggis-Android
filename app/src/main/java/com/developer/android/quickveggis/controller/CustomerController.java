package com.developer.android.quickveggis.controller;


import android.util.Log;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.api.model.SocialLoginData;
import com.developer.android.quickveggis.model.Customer;
import com.snappydb.DB;
import com.snappydb.SnappydbException;

public class CustomerController {

    public static final String TAG = CustomerController.class.getSimpleName();
    private static final CustomerController instance = new CustomerController();

    public static CustomerController getInstance() {
        return instance;
    }

    private static final String KEY_LOGGED_IN_USER = "logged_in_user";
    private static final String KEY_LOGGED_IN_SOCIAL_USER = "logged_in_social_user";
    private Customer customer;
    private SocialLoginData socialCustomer;

    private CustomerController() {
    }

    public void saveLoginCustomer(Customer customer) {
        this.customer = customer;

        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_LOGGED_IN_USER, customer);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to login");
        }
    }

    public void saveLoginSocialCustomer(SocialLoginData socialCustomer) {
        this.socialCustomer = socialCustomer;

        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_LOGGED_IN_SOCIAL_USER, socialCustomer);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to login");
        }
    }

    public void saveUpdatedCustomer(Customer customer) {
        saveLoginCustomer(customer);
    }

    public boolean isCustomerLoggedIn() {
        DB db = App.getInstance().getDb();
        try {
            return db.exists(KEY_LOGGED_IN_USER);
        } catch (SnappydbException e) {
            Log.d("error", "Failed on find data");
            return false;
        }
    }

    public Customer getLoggedInCustomer() {
        if (customer == null) {
            if (isCustomerLoggedIn()) {
                DB db = App.getInstance().getDb();
                try {
                    customer = db.getObject(KEY_LOGGED_IN_USER, Customer.class);
                } catch (SnappydbException e) {
                    Log.d("error", "Failed on getLoggedInUser");
                }
            }
        }
        return customer;
    }

    public SocialLoginData getLoggedInSocialCustomer() {
        if (socialCustomer == null) {
            //  if (isCustomerLoggedIn()) {
                DB db = App.getInstance().getDb();
                try {
                    socialCustomer = db.getObject(KEY_LOGGED_IN_SOCIAL_USER, SocialLoginData.class);
                } catch (SnappydbException e) {
                    Log.d("error", "Failed on getLoggedInUser");
                }
            //  }
        }
        return socialCustomer;
    }

    public void logoutUser() {
        customer = null;
        if (isCustomerLoggedIn()) {
            DB db = App.getInstance().getDb();
            try {
                db.del(KEY_LOGGED_IN_USER);
            } catch (SnappydbException e) {
                Log.d("error", "Failed on logoutUser");
            }
        }
    }
}
