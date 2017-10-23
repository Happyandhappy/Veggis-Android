package com.developer.android.quickveggis.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.developer.android.quickveggis.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 10/17/2016.
 */

public class HistoryRepo {

    private static HistoryRepo instance;
    private Context context;
    private static String SEARCHKEY = "historySearch";
    private static String BrandsKey = "historyBrands";

    public static HistoryRepo getInstance() {
        if (instance == null) {
            instance = new HistoryRepo(App.getContext());
        }
        return instance;
    }

    public HistoryRepo(Context ctx) {
        context = ctx;
    }

    public void saveHistorySearchKey(ArrayList<String> arrayList){
        String string = arrayList.toString();

        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SEARCHKEY, string);

        editor.commit();
    }

    public ArrayList<String> getHistorySearchKey(){

        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis.home", Context.MODE_PRIVATE);

        String string = preferences.getString(SEARCHKEY,"");

        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(string.split("||")));

        return arrayList;
    }

    public void saveHistoryBrands(List<String> arrayList){
        String string = "";

        if (arrayList.size() == 0) return;

        string = arrayList.get(0);

        for (int i = 1; i < arrayList.size(); i ++) {
            string = string + "," + arrayList.get(i);
        }

        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BrandsKey, string);

        editor.commit();
    }

    public ArrayList<String> getHistoryBrands(){

        SharedPreferences preferences = context.getSharedPreferences("com.android.steven.veggis", Context.MODE_PRIVATE);

        String string = preferences.getString(BrandsKey,"");

        String[] tokens = string.split(",");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(string.split(",")));

        if (string == "") return new ArrayList<String>();

        return arrayList;
    }
}
