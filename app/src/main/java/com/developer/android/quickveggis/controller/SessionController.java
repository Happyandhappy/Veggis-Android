package com.developer.android.quickveggis.controller;


import android.util.Log;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.api.model.LoginUserData;
import com.snappydb.DB;
import com.snappydb.SnappydbException;


public class SessionController {

    public static final String TAG = SessionController.class.getSimpleName();
    private static final SessionController instance = new SessionController();

    public static SessionController getInstance() {
        return instance;
    }

    private static final String KEY_LOGGED_IN_SESSION = "logged_in_session";
    private static final String KEY_FIRST_TUTORIAL = "first_tutorial";
    private static final String KEY_SECOND_TUTORIAL = "second_tutorial";
    private static final String KEY_LOGIN_USER_DATA= "login_info";
    private String session;

    private SessionController() {
    }

    public void saveLoginSession(String session) {
        this.session = session;

        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_LOGGED_IN_SESSION, session);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to login");
        }
    }

    public boolean isLoggedInSession() {
        DB db = App.getInstance().getDb();
        try {
            return db.exists(KEY_LOGGED_IN_SESSION);
        } catch (SnappydbException e) {
            Log.d("error", "Failed on find data");
            return false;
        }
    }

    public String getLoggedInSession() {
        if (session == null) {
            if (isLoggedInSession()) {
                DB db = App.getInstance().getDb();
                try {
                    session = db.get(KEY_LOGGED_IN_SESSION);
                } catch (SnappydbException e) {
                    Log.d("error", "Failed on getLoggedInUser");
                }
            }
        }
        return session;
    }

    public void deleteSession() {
        session = null;
        if (isLoggedInSession()) {
            DB db = App.getInstance().getDb();
            try {
                db.del(KEY_LOGGED_IN_SESSION);
            } catch (SnappydbException e) {
                Log.d("error", "Failed on deleting session");
            }
        }
    }

    public void setFirstTutorial(boolean isFirstTutorialShown)
    {
        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_FIRST_TUTORIAL, isFirstTutorialShown);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to save");
        }
    }

    public boolean getFirstTutorial() {
        if (isExistShownFirstTutorial()) {
            DB db = App.getInstance().getDb();
            try {
                return db.getObject(KEY_FIRST_TUTORIAL, Boolean.class);
            } catch (SnappydbException e) {
                Log.d("error", "Failed on getFirstTutorial");
            }
        }
        return false;
    }

    public boolean isExistShownFirstTutorial() {
        DB db = App.getInstance().getDb();
        try {
            return db.exists(KEY_FIRST_TUTORIAL);
        } catch (SnappydbException e) {
            Log.d("error", "Failed on find data");
            return false;
        }
    }

    public void setSecondTutorial(boolean isSecondTutorialShown)
    {
        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_SECOND_TUTORIAL, isSecondTutorialShown);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to save");
        }
    }

    public boolean getSecondTutorial() {
        if (isExistShownSecondTutorial()) {
            DB db = App.getInstance().getDb();
            try {
                return db.getObject(KEY_SECOND_TUTORIAL, Boolean.class);
            } catch (SnappydbException e) {
                Log.d("error", "Failed on getSecondTutorial");
            }
        }
        return false;
    }

    public boolean isExistShownSecondTutorial() {
        DB db = App.getInstance().getDb();
        try {
            return db.exists(KEY_SECOND_TUTORIAL);
        } catch (SnappydbException e) {
            Log.d("error", "Failed on find data");
            return false;
        }
    }

    public void saveLoginInfo(LoginUserData data)
    {
        DB db = App.getInstance().getDb();
        try {
            db.put(KEY_LOGIN_USER_DATA, data);
        } catch (SnappydbException e) {
            Log.d("error", "Unable to save");
        }
    }

    public LoginUserData getLoginUserInfo() {
        if (isExistLoginUserInfo()) {
            DB db = App.getInstance().getDb();
            try {
                return db.getObject(KEY_LOGIN_USER_DATA, LoginUserData.class);
            } catch (SnappydbException e) {
                Log.d("error", "Failed on getSecondTutorial");
            }
        }
        return null;
    }

    public boolean isExistLoginUserInfo() {
        DB db = App.getInstance().getDb();
        try {
            return db.exists(KEY_LOGIN_USER_DATA);
        } catch (SnappydbException e) {
            Log.d("error", "Failed on find data");
            return false;
        }
    }
}
