package com.developer.android.quickveggis.ui.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.developer.android.quickveggis.ui.activity.MainActivity;

public class FragmentUtils {
    private static final String FRAGMENT_TAG = "Content";

    public static void changeFragment(FragmentActivity activity, int contentFrame, Fragment fr, boolean addToBackStack) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(contentFrame, fr, FRAGMENT_TAG);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
//        ft.commitAllowingStateLoss();
        ft.commit();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).updateMenu(fr);
        }
    }

    public static void addFragmentAtFirst(FragmentActivity activity, int contentFrame, Fragment fr, boolean addToBackStack) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(contentFrame, fr, FRAGMENT_TAG);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
//        ft.commitAllowingStateLoss();
        ft.commit();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).updateMenu(fr);
        }
    }

//    public static void changeFragment(Activity activity, int contentFrame, android.app.Fragment fr, boolean addToBackStack) {
//        android.app.FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
//        ft.replace(contentFrame, fr, FRAGMENT_TAG);
//        if (addToBackStack) {
//            ft.addToBackStack(null);
//        }
//        ft.commitAllowingStateLoss();
//    }

    public static Fragment getCurrent(FragmentActivity activity) {
        return activity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    public static Fragment getCurrent(FragmentActivity activity, int id) {
        return activity.getSupportFragmentManager().findFragmentById(id);
    }

    public static void popBackStack(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }
}
