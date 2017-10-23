package com.developer.android.quickveggis.model.sort;

import com.developer.android.quickveggis.model.CartItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 10/16/2016.
 */

public class CompareModel {

    public ArrayList<CartItem> compareCartList(ArrayList<CartItem> arrayList) {

        Comparator<CartItem> comparator = new Comparator<CartItem>() {
            @Override
            public int compare(CartItem lhs, CartItem rhs) {

                return lhs.getCategoryInfo().get(0).getCategoryname().compareTo(rhs.getCategoryInfo().get(0).getCategoryname());

            }
        };

        Collections.sort(arrayList, comparator);

        return arrayList;
    }


}
