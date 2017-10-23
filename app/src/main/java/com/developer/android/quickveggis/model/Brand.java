package com.developer.android.quickveggis.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/19/2016.
 */

public class Brand {

    public  ArrayList<String> brandList;

    public Brand (){
        brandList = new ArrayList<String>();

        brandList.add("P&G");          brandList.add("Unilever");
        brandList.add("Parles");          brandList.add("Patanjali");
        brandList.add("Britania");          brandList.add("Cocoa");
        brandList.add("");          brandList.add("");
    }


}
