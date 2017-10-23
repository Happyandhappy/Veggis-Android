package com.developer.android.quickveggis.db;

import android.content.Context;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.model.BankAccount;
import java.util.List;

public class BankRepo {
    private static BankRepo instance;
    private DatabaseHelper helper;

    public static BankRepo getInstance() {
        if (instance == null) {
            instance = new BankRepo(App.getContext());
        }
        return instance;
    }

    private BankRepo(Context ctx) {
        this.helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return this.helper;
    }

    public long getCount() {
        try {
            return getHelper().getBankDao().queryBuilder().countOf();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<BankAccount> getList() {
        try {
            return getHelper().getBankDao().queryForAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void save(BankAccount account) {
        try {
            getHelper().getBankDao().createOrUpdate(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
