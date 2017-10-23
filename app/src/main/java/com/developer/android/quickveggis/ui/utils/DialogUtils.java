package com.developer.android.quickveggis.ui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog.Builder;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.custom.GifImageView;

import org.w3c.dom.Text;

import java.io.InputStream;

public class DialogUtils {

    public static GifImageView gifView;
    public static Dialog gifDialog;

    /* renamed from: com.quickveggies.quickveggies.ui.utils.DialogUtils.1 */
    static class C03361 implements OnClickListener {
        C03361() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public static ProgressDialog showProgressDialog(Activity activity, int message) {
        return showProgressDialog(activity, App.getContext().getString(message));
    }

    public static ProgressDialog showProgressDialog(Activity activity, String message) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static void showAlertDialog(Context context, String message) {
        new Builder(context).setMessage((CharSequence) message).setPositiveButton((CharSequence) "OK", new C03361()).create().show();
    }

    public static void showAlertDialog(Context context, int message) {
        showAlertDialog(context, App.getContext().getString(message));
    }

    public static void showConfirmDialog(Context context, int message) {
    }

    public static void showAlertUnLockDialog(Context context) {
        gifDialog = new Dialog(context);
        gifDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gifDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gifDialog.setContentView(R.layout.unlock_dialog);
        gifDialog.setCancelable(false);
        gifView = (GifImageView) gifDialog.findViewById(R.id.gifView);
        gifView.setBackgroundColor(Color.TRANSPARENT);
        gifDialog.show();
    }

    public static void dismissUnlockDialog(){
        if (gifDialog.isShowing()){
            gifDialog.dismiss();
        }
    }

    public static void showUnlockedTaskTutorialDialog(Context context, String price) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_unlocked_tutorial);
        dialog.setCancelable(false);

        TextView priceText = (TextView) dialog.findViewById(R.id.price);
        priceText.setText(price);

        TextView okGotIt = (TextView) dialog.findViewById(R.id.okGotIt);
        okGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
