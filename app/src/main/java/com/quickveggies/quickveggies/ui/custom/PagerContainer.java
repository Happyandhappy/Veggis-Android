package com.quickveggies.quickveggies.ui.custom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import org.joda.time.MutableDateTime;

public class PagerContainer extends FrameLayout implements OnPageChangeListener {
    private Point mCenter;
    private Point mInitialTouch;
    boolean mNeedsRedraw;
    private ViewPager mPager;

    public PagerContainer(Context context) {
        super(context);
        this.mNeedsRedraw = false;
        this.mCenter = new Point();
        this.mInitialTouch = new Point();
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNeedsRedraw = false;
        this.mCenter = new Point();
        this.mInitialTouch = new Point();
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mNeedsRedraw = false;
        this.mCenter = new Point();
        this.mInitialTouch = new Point();
        init();
    }

    private void init() {
        setClipChildren(false);
        setLayerType(1, null);
    }

    protected void onFinishInflate() {
        try {
            this.mPager = (ViewPager) getChildAt(0);
            this.mPager.setOffscreenPageLimit(9999);
            this.mPager.setOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return this.mPager;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mCenter.x = w / 2;
        this.mCenter.y = h / 2;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                this.mInitialTouch.x = (int) ev.getX();
                this.mInitialTouch.y = (int) ev.getY();
                break;
        }
        ev.offsetLocation((float) (this.mCenter.x - this.mInitialTouch.x), (float) (this.mCenter.y - this.mInitialTouch.y));
        return this.mPager.dispatchTouchEvent(ev);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mNeedsRedraw) {
            invalidate();
        }
    }

    public void onPageSelected(int position) {
        int count = this.mPager.getChildCount();
        position = this.mPager.getCurrentItem();
        for (int i = 0; i < count; i++) {
            Integer pos = (Integer) this.mPager.getChildAt(i).getTag();
            View view = this.mPager.getChildAt(i);
            AnimatorSet as;
            ObjectAnimator oa1;
            ObjectAnimator oa2;
            if (pos.intValue() != position) {
                as = new AnimatorSet();
                as.setInterpolator(new DecelerateInterpolator());
                oa1 = ObjectAnimator.ofFloat(view, "scaleX", new float[]{0.8f});
                oa2 = ObjectAnimator.ofFloat(view, "scaleY", new float[]{0.8f});
                oa1.setDuration(300);
                oa2.setDuration(300);
                as.play(oa1).with(oa2);
                as.start();
            } else {
                as = new AnimatorSet();
                as.setInterpolator(new DecelerateInterpolator());
                oa1 = ObjectAnimator.ofFloat(view, "scaleX", new float[]{1.0f});
                oa2 = ObjectAnimator.ofFloat(view, "scaleY", new float[]{1.0f});
                oa1.setDuration(300);
                oa2.setDuration(300);
                as.play(oa1).with(oa2);
                as.start();
            }
        }
    }

    public void onPageScrollStateChanged(int state) {
        this.mNeedsRedraw = state != 0;
    }
}
