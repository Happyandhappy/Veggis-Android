package com.developer.android.quickveggis.db;

import android.content.Context;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.model.Address;

import java.util.List;

public class AddressRepo {
    private static AddressRepo instance;
    private DatabaseHelper helper;

    public static AddressRepo getInstance() {
        if (instance == null) {
            instance = new AddressRepo(App.getContext());
        }
        return instance;
    }

    private AddressRepo(Context ctx) {
        this.helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return this.helper;
    }

    public long getCount() {
        try {
            return getHelper().getAddressDao().queryBuilder().countOf();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<Address> getList() {
        try {
            return getHelper().getAddressDao().queryForAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveAddress(Address address) {
        try {
            getHelper().getAddressDao().createOrUpdate(address);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
