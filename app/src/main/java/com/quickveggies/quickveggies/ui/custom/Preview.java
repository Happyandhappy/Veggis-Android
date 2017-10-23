package com.quickveggies.quickveggies.ui.custom;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.util.List;

public class Preview extends ViewGroup implements Callback {
    private final String TAG;
    Camera mCamera;
    SurfaceHolder mHolder;
    Size mPreviewSize;
    List<Size> mSupportedPictureSizes;
    List<Size> mSupportedPreviewSizes;
    SurfaceView mSurfaceView;

    public Preview(Context context, SurfaceView sv) {
        super(context);
        this.TAG = "Preview";
        this.mSurfaceView = sv;
        this.mHolder = this.mSurfaceView.getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
        if (this.mCamera != null) {
            this.mSupportedPreviewSizes = this.mCamera.getParameters().getSupportedPreviewSizes();
            this.mSupportedPictureSizes = this.mCamera.getParameters().getSupportedPictureSizes();
            requestLayout();
            Parameters params = this.mCamera.getParameters();
            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains("auto")) {
                params.setFocusMode("auto");
            }
            if (focusModes.contains("continuous-picture")) {
                params.setFocusMode("continuous-picture");
            }
            this.mCamera.setParameters(params);
            try {
                if (this.mHolder != null) {
                    camera.setPreviewDisplay(this.mHolder);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec), resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        if (this.mSupportedPreviewSizes == null) {
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed && getChildCount() > 0) {
            View child = getChildAt(0);
            int width = r - l;
            int height = b - t;
            int previewWidth = width;
            int previewHeight = height;
            if (this.mPreviewSize != null) {
                previewWidth = this.mPreviewSize.width;
                previewHeight = this.mPreviewSize.height;
            }
            if (width * previewHeight > height * previewWidth) {
                int scaledChildWidth = (previewWidth * height) / previewHeight;
                child.layout((width - scaledChildWidth) / 2, 0, (width + scaledChildWidth) / 2, height);
                return;
            }
            int scaledChildHeight = (previewHeight * width) / previewWidth;
            child.layout(0, (height - scaledChildHeight) / 2, width, (height + scaledChildHeight) / 2);
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (this.mCamera != null) {
                this.mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            Log.e("Preview", "IOException caused by setPreviewDisplay()", exception);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mCamera != null) {
            this.mCamera.stopPreview();
        }
    }

    private Size getBestPreviewSize(int width, int height, Parameters parameters) {
        Size result = null;
        for (Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else if (size.width * size.height > result.width * result.height) {
                    result = size;
                }
            }
        }
        return result;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (this.mCamera != null) {
            try {
                this.mCamera.stopPreview();
            } catch (Exception e) {
            }
            Parameters parameters = this.mCamera.getParameters();
            this.mPreviewSize = getBestPreviewSize(w, h, parameters);
            parameters.setPictureSize(this.mPreviewSize.width, this.mPreviewSize.width);
            parameters.setPreviewSize(this.mPreviewSize.width, this.mPreviewSize.width);
            this.mCamera.setDisplayOrientation(90);
            this.mCamera.startPreview();
        }
    }
}
