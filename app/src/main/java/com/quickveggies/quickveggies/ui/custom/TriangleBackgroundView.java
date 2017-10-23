package com.quickveggies.quickveggies.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class TriangleBackgroundView extends View {
    Paint bgPaint;
    int color;

    public TriangleBackgroundView(Context context) {
        super(context);
        init(null);
    }

    public TriangleBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TriangleBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.color = getContext().obtainStyledAttributes(attrs, new int[]{16842964}).getColor(0, ContextCompat.getColor(getContext(), 17170444));
        setBackgroundColor(ContextCompat.getColor(getContext(), 17170445));
        this.bgPaint = new Paint();
        this.bgPaint.setStyle(Style.FILL);
        this.bgPaint.setColor(this.color);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        Path path = new Path();
        path.moveTo(0.0f, (float) h);
        path.lineTo((float) w, (float) h);
        path.lineTo(0.0f, 0.0f);
        path.lineTo(0.0f, (float) h);
        path.close();
        canvas.drawPath(path, this.bgPaint);
    }
}
