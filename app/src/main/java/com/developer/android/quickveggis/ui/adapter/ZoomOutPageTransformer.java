package com.developer.android.quickveggis.ui.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by friend on 7/6/16.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    public void transformPage(View page, float position) {

        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1){
            page.setAlpha(0);
        }else if (position <= 1){
            //modify the default slide transition to shrink the page as well
            float scalFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scalFactor) / 2;
            float horzMargin = pageWidth * (1 - scalFactor) / 2;
            if (position < 0){
                page.setTranslationX(horzMargin - vertMargin / 2);

            }else{
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            page.setScaleX(scalFactor);
            page.setScaleY(scalFactor);

            //fade the page relative to its size
            page.setAlpha(MIN_ALPHA + (scalFactor - MIN_SCALE)/(1 - MIN_SCALE) * (1 - MIN_ALPHA));

        }else{
            page.setAlpha(0);
        }
    }
}
