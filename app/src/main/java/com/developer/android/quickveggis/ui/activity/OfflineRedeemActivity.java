package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.ui.dialog.ActionListener;
import com.developer.android.quickveggis.ui.dialog.NotifyDialog;
import com.developer.android.quickveggis.ui.fragments.SubmitFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class OfflineRedeemActivity extends AppCompatActivity implements ActionListener {
    static final int ACTIVITY_GROUP = 2;
    static final int ACTIVITY_RECEIPT = 1;
    static final int DIALOG_MAKE_SURE = 1;
    static final int DIALOG_NOTIFY = 2;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, OfflineRedeemActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_redeem);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        makeSureDialog();
    }

    private void makeSureDialog() {
        if (NotifyDialog.isShowDialog(this, DIALOG_MAKE_SURE)) {
            NotifyDialog notifyDialog = NotifyDialog.newInstance(DIALOG_MAKE_SURE, R.string.make_sure, new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(R.string.item1), Integer.valueOf(R.string.item1), Integer.valueOf(R.string.item1)})));
            notifyDialog.show(getSupportFragmentManager(), "dialog");
            notifyDialog.setListener(this);
            return;
        }
        onContinueClicked(DIALOG_MAKE_SURE);
    }

    private void verifyDialog() {
        if (NotifyDialog.isShowDialog(this, DIALOG_NOTIFY)) {
            NotifyDialog notifyDialog = NotifyDialog.newInstance(DIALOG_NOTIFY, R.string.verify_purchases, new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(R.string.item1), Integer.valueOf(R.string.item1), Integer.valueOf(R.string.item1)})));
            notifyDialog.show(getSupportFragmentManager(), "dialog");
            notifyDialog.setListener(this);
            return;
        }
        onContinueClicked(DIALOG_NOTIFY);
    }

    public void onContinueClicked(int id) {
        if (id == DIALOG_MAKE_SURE) {
            startActivityForResult(CameraActivity.getStartIntent(this), DIALOG_MAKE_SURE);
        } else {
            startActivityForResult(GroupCameraActivity.getStartIntent(this), DIALOG_NOTIFY);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == -1) {
//            FragmentUtils.changeFragment(this, R.id.content, SubmitFragment.newInstance(null, null), false);
//        }
//        return;///??testestesttest
        if (requestCode == DIALOG_MAKE_SURE) {
            if (resultCode == -1) {
                //ArrayList<String> links = data.getStringArrayListExtra("data");
                if (data != null){
                    Config.list_receipt_images = data.getStringArrayListExtra("data");

                    if (Config.list_receipt_images == null || Config.list_receipt_images.isEmpty()) {

                        finish();
                    } else {
                        //this.toolbar.setVisibility(0);
                        //FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SubmitFragment.newInstance(links, null), false);
                        //startActivityForResult(GroupCameraActivity.getStartIntent(this), DIALOG_NOTIFY);
                        verifyDialog();
                    }
                }
            } else {
                finish();
            }
        }
        else{
            if (resultCode == -1){
                if (data != null){
                    Config.list_group_images = data.getStringArrayListExtra("data");
                    if (Config.list_group_images == null || Config.list_group_images.isEmpty()){
                        if (Config.list_receipt_images != null && Config.list_receipt_images.size() > 0) {
                            this.toolbar.setVisibility(View.VISIBLE);

                            Bitmap bitmap = ServiceAPI.newInstance().getBitmap(Config.list_receipt_images.get(0));

                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();

                            FragmentUtils.changeFragment(this, R.id.content, SubmitFragment.newInstance(), false);
                        }
                        else
                            finish();
                    }
                    else{
                        this.toolbar.setVisibility(View.VISIBLE);
                        FragmentUtils.changeFragment(this, R.id.content, SubmitFragment.newInstance(), false);
                    }
                }
            }
            return;
        }
        if (requestCode != DIALOG_NOTIFY) {
            return;   //testesttes???
        }
//        if (resultCode == -1) {
//            this.toolbar.setVisibility(View.VISIBLE);
//            FragmentUtils.changeFragment(this, R.id.content, SubmitFragment.newInstance(), false);
//            return;
//        }
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }
}
