package com.developer.android.quickveggis.ui.utils;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AndroidUriUtils {
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int len = inputStream.read(buffer);
            if (len == -1) {
                return byteBuffer.toByteArray();
            }
            byteBuffer.write(buffer, 0, len);
        }
    }
}
