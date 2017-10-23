package com.developer.android.quickveggis.ui.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.developer.android.quickveggis.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class BitmapUtils {
    public static Bitmap getScaledBitmap(byte[] bytes, int size, int angle) {
        Options o = new Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, o);
        int scale = 1;
        while (o.outWidth / scale >= size && o.outHeight / scale >= size) {
            scale *= 2;
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        return rotateBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, o2), angle);
    }

    private static Bitmap getScaledBitmap(InputStream in, InputStream in2, int size) throws OutOfMemoryError {
        Options o = new Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, o);
        int scale = 1;
        while (o.outWidth / scale >= size && o.outHeight / scale >= size) {
            scale *= 2;
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(in2, null, o2);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap.compress(CompressFormat.JPEG, 80, stream)) {
            return stream.toByteArray();
        }
        return null;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        Matrix mat = new Matrix();
        mat.postRotate((float) angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
    }

    public static void copy(Bitmap bitmap, File file) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 80, stream);
            byte[] byteArray = stream.toByteArray();
            stream.reset();
            OutputStream out = new FileOutputStream(file);
            out.write(byteArray);
            out.close();
        } catch (Exception e) {
        }
    }

    public static void setTaskImage(ImageView imageView, int type, boolean isEnabled){
        switch (type){
            case 1: //TASK_TESTIFY
                imageView.setImageResource(isEnabled?R.drawable.bg_task_testify:R.drawable.bg_grey_task_testify);
                break;
            case 2: //TASK_RECIPE
                imageView.setImageResource(isEnabled?R.drawable.bg_task_recipe:R.drawable.bg_grey_task_recipe);
                break;
            case 3: //TASK_SURVEY
                imageView.setImageResource(isEnabled?R.drawable.bg_task_survey:R.drawable.bg_grey_task_survey);
                break;
            case 4: //TASK_TRIVIA
                imageView.setImageResource(isEnabled?R.drawable.bg_task_trivia:R.drawable.bg_grey_task_trivia);
                break;
            case 5: //TASK_POLL
                imageView.setImageResource(isEnabled?R.drawable.bg_task_poll:R.drawable.bg_grey_task_poll);
                break;
            case 6: //TASK_FACT
                imageView.setImageResource(isEnabled?R.drawable.bg_task_fact:R.drawable.bg_grey_task_fack);
                break;
            case 7: //TASK_NUTRITION
                imageView.setImageResource(isEnabled?R.drawable.bg_task_nutrition_fact:R.drawable.bg_grey_task_nutrition);
                break;
            case 8: //TASK_VIDEO
                imageView.setImageResource(isEnabled?R.drawable.bg_task_video:R.drawable.bg_grey_task_video);
                break;
            case 9: //TASK_LETS_BATTLE
                imageView.setImageResource(isEnabled?R.drawable.bg_task_battle:R.drawable.bg_grey_task_battle);
                break;
            case 10: //TASK_DO_GOODER
                imageView.setImageResource(isEnabled?R.drawable.bg_task_do_gooder:R.drawable.bg_grey_task_gooder);
                break;
            default:
                break;
        }
    }
}
