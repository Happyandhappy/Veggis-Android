package com.developer.android.quickveggis.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.developer.android.quickveggis.model.Address;
import com.developer.android.quickveggis.model.BankAccount;
import com.developer.android.quickveggis.model.NotificationModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "air.db";
    private static final int DATABASE_VERSION = 2;
    private Dao<Address, Long> addressDao;
    private Dao<NotificationModel, Long> notificationDao;
    private Dao<BankAccount, Long> bankDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.addressDao = null;
        this.bankDao = null;
        this.notificationDao = null;
    }

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Address.class);
            TableUtils.createTableIfNotExists(connectionSource, BankAccount.class);
            TableUtils.createTableIfNotExists(connectionSource, NotificationModel.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e2) {
            e2.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Address.class, true);
            TableUtils.dropTable(connectionSource, BankAccount.class, true);
            TableUtils.dropTable(connectionSource, NotificationModel.class, true);
            onCreate(db, connectionSource);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Address, Long> getAddressDao() {
        if (this.addressDao == null) {
            try {
                this.addressDao = getDao(Address.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return this.addressDao;
    }

    public Dao<NotificationModel, Long> getNotificationDao() {
        if (this.notificationDao == null) {
            try {
                this.notificationDao = getDao(NotificationModel.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return this.notificationDao;
    }

    public Dao<BankAccount, Long> getBankDao() {
        if (this.bankDao == null) {
            try {
                this.bankDao = getDao(BankAccount.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return this.bankDao;
    }

    public void clearAll() {
        try {
            TableUtils.clearTable(this.connectionSource, Address.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
