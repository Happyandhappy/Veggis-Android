package com.developer.android.quickveggis.api.model;

import com.developer.android.quickveggis.model.Bank;

import java.util.ArrayList;

/**
 * Created by devmac on 06/09/16.
 */
public class BanksDataChanged {

    ArrayList<Bank> banks;

    public BanksDataChanged(ArrayList<Bank> banks) {
        this.banks = banks;
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }
}
