package com.quickveggies.quickveggies.ui.custom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.NotificationCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.SeekBar;

import java.lang.ref.WeakReference;

import com.developer.android.quickveggis.R;

@SuppressWarnings("deprecation")
public class SlideButton extends SeekBar {
    private static final int MIN_PROGRESS_VALUE = 50;
    private WeakReference<SlideButtonListener> listener;
    ValueAnimator progressAnim;
    Drawable thumb;

    public interface SlideButtonListener {
        void onTabSelected(int i);
    }

    public SlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        this.thumb = thumb;
    }

    private void createAnimation(int end) {
        this.progressAnim = ObjectAnimator.ofInt(this, NotificationCompat.CATEGORY_PROGRESS, new int[]{getProgress(), end}).setDuration(150);
        this.progressAnim.setInterpolator(new AccelerateInterpolator());
    }

    public void startAnimation(int end) {
        createAnimation(end);
        this.progressAnim.start();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private Drawable expandThumb(int widthComponent) {
        Drawable thumb = getResources().getDrawable(R.drawable.selector_red_selected).getConstantState().newDrawable();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int ingHeight = thumb.getIntrinsicHeight();
        int width = widthComponent / 2;
        double scale = ((double) thumb.getIntrinsicHeight()) / ((double) height);
        new ScaleDrawable(thumb, 0, (float) scale, (float) scale).setBounds(0, 0, width, height);
        Drawable newThumb = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawableToBitmap(thumb, width, height), width, height, true));
        newThumb.setBounds(0, 0, width, height);
        return newThumb;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Drawable thumb = expandThumb(w);
        setPadding(w / 4, 0, w / 4, 0);
        setThumb(thumb);
    }

    public void setSlideButtonListener(SlideButtonListener listener) {
        this.listener = new WeakReference(listener);
    }
}
