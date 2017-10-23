package com.developer.android.quickveggis.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
//import com.quickveggies.quickveggies.ui.adapter.PreviewAdapter;
//import com.quickveggies.quickveggies.ui.adapter.PreviewAdapter.PreviewListener;
//import com.quickveggies.quickveggies.ui.custom.Preview;
//import com.quickveggies.quickveggies.ui.dialog.NotifyDialog;
//import com.quickveggies.quickveggies.ui.utils.BitmapUtils;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.adapter.PreviewAdapter;
import com.developer.android.quickveggis.ui.dialog.NotifyDialog;
import com.developer.android.quickveggis.ui.utils.BitmapUtils;
import com.quickveggies.quickveggies.ui.custom.Preview;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Subscriber;

public class GroupCameraActivity extends AppCompatActivity implements PreviewAdapter.PreviewListener {
    private static final String TAG = "CamTestActivity";
    PreviewAdapter adapter;
    int angle;
    @Bind(R.id.btnActionLeft)
    TextView btnActionLeft;
    @Bind(R.id.btnActionRight)
    TextView btnActionRight;
    @Bind(R.id.btnCapture)
    ImageView btnCapture;
    @Bind(R.id.btnTips)
    View btnTips;
    Camera camera;
    int count;
    int currentCamera;
    String folderID;
    @Bind(R.id.img)
    ImageView image;
    PictureCallback jpegCallback;
    Preview preview;
    List<Uri> previews;
    PictureCallback rawCallback;
    @Bind(R.id.rv)
    RecyclerView rv;
    ShutterCallback shutterCallback;
    Uri uri;

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.1 */
    class C02521 implements OnClickListener {
        C02521() {
        }

        public void onClick(View v) {
            if (GroupCameraActivity.this.camera != null) {
                Parameters params = GroupCameraActivity.this.camera.getParameters();
                params.setFocusMode("auto");
                List<Camera.Size> previewsSizes = params.getSupportedPreviewSizes();
                Camera.Size previewSize = previewsSizes.get(0);
                params.setPreviewSize(previewSize.width, previewSize.height);
                try {
                    GroupCameraActivity.this.camera.setParameters(params);
                }catch (RuntimeException e){
                    e.printStackTrace();
                }
                GroupCameraActivity.this.camera.takePicture(null, null, GroupCameraActivity.this.jpegCallback);
                return;
            }
            if (GroupCameraActivity.this.camera != null) {
                GroupCameraActivity.this.camera.takePicture(null, null, GroupCameraActivity.this.jpegCallback);
                return;
            }
            if (GroupCameraActivity.this.uri != null) {
                GroupCameraActivity.this.previews.add(GroupCameraActivity.this.uri);
                GroupCameraActivity.this.adapter.notifyDataSetChanged();
            }
            GroupCameraActivity.this.image.setImageBitmap(null);
            GroupCameraActivity.this.image.setVisibility(View.GONE);
            GroupCameraActivity.this.startCamera();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.2 */
    class C02542 implements OnTouchListener {

        /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.2.1 */
        class C02531 implements AutoFocusCallback {
            C02531() {
            }

            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.cancelAutoFocus();
                }
            }
        }

        C02542() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (GroupCameraActivity.this.camera != null && GroupCameraActivity.this.camera.getParameters().getSupportedFocusModes().contains("auto")) {
                Parameters params = GroupCameraActivity.this.camera.getParameters();
                params.setFocusMode("auto");
                GroupCameraActivity.this.camera.setParameters(params);
                GroupCameraActivity.this.camera.autoFocus(new C02531());
            }
            return false;
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.3 */
    class C02553 implements OnClickListener {
        C02553() {
        }

        public void onClick(View v) {
            GroupCameraActivity.this.submit();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.4 */
    class C02564 implements OnClickListener {
        C02564() {
        }

        public void onClick(View v) {
            GroupCameraActivity.this.makeVerifyDialog();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.5 */
    class C02575 implements OnClickListener {
        C02575() {
        }

        public void onClick(View v) {
            GroupCameraActivity.this.cancel();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.6 */
    class C02586 implements OnClickListener {
        C02586() {
        }

        public void onClick(View v) {
            GroupCameraActivity.this.retake();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.7 */
    class C02597 implements ShutterCallback {
        C02597() {
        }

        public void onShutter() {
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.8 */
    class C02608 implements PictureCallback {
        C02608() {
        }

        public void onPictureTaken(byte[] data, Camera camera) {
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.GroupCameraActivity.9 */
    class C02619 implements PictureCallback {
        C02619() {
        }

        public void onPictureTaken(byte[] data, Camera camera) {
            GroupCameraActivity groupCameraActivity = GroupCameraActivity.this;
            groupCameraActivity.count++;
            GroupCameraActivity.this.updateTakeState();
            new SaveImageTask().execute(new byte[][]{data});
            GroupCameraActivity.this.stopCamera();
        }
    }

    private class SaveImageTask extends AsyncTask<byte[], Void, File> {
        private SaveImageTask() {
        }

        protected File doInBackground(byte[]... data) {
            FileOutputStream fileOutputStream;
            FileNotFoundException e;
            IOException e2;
            Throwable th;
            Bitmap dstBmp = BitmapUtils.getScaledBitmap(data[0], 800, GroupCameraActivity.this.angle);
            try {
                /*
                Bitmap dstBmp;
                if (srcBmp.getWidth() >= srcBmp.getHeight()) {
                    dstBmp = Bitmap.createBitmap(srcBmp, (srcBmp.getWidth() / 2) - (srcBmp.getHeight() / 2), 0, srcBmp.getHeight(), srcBmp.getHeight());
                } else {
                    dstBmp = Bitmap.createBitmap(srcBmp, 0, (srcBmp.getHeight() / 2) - (srcBmp.getWidth() / 2), srcBmp.getWidth(), srcBmp.getWidth());
                }
                */
                File outFile = GroupCameraActivity.this.getPhotoLink();
                FileOutputStream outStream = new FileOutputStream(outFile);
                try {
                    outStream.write(BitmapUtils.bitmapToBytes(dstBmp));
                    outStream.flush();
                    outStream.close();
                    fileOutputStream = outStream;
                    return outFile;
                } catch (FileNotFoundException e3) {
                    e = e3;
                    fileOutputStream = outStream;
                    e.printStackTrace();
                    return null;
                } catch (IOException e4) {
                    e2 = e4;
                    fileOutputStream = outStream;
                    e2.printStackTrace();
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = outStream;
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                return null;
            } catch (IOException e6) {
                e2 = e6;
                e2.printStackTrace();
                return null;
            } catch (Throwable th3) {
                th = th3;
//                throw th;
                return null;
            }
        }

        protected void onPostExecute(File outFile) {
            super.onPostExecute(outFile);
            GroupCameraActivity.this.uri = Uri.fromFile(outFile);
            GroupCameraActivity.this.setImageState();
        }
    }

    public GroupCameraActivity() {
        this.currentCamera = 0;
        this.angle = 0;
        this.count = 0;
        this.previews = new ArrayList();
        this.shutterCallback = new C02597();
        this.rawCallback = new C02608();
        this.jpegCallback = new C02619();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, GroupCameraActivity.class);
        intent.putExtra("folderId", String.valueOf(System.currentTimeMillis()));
        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.folderID = getIntent().getStringExtra("folderId");
        getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setContentView((int) R.layout.fragment_group_camera);

        ButterKnife.bind((Activity) this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        this.rv.setLayoutManager(lm);
        this.adapter = new PreviewAdapter(this.previews, this, this);
        this.rv.setAdapter(this.adapter);
        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(this).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_preview)).build());

        this.preview = new Preview(this, (SurfaceView) findViewById(R.id.surfaceView));
        this.preview.setLayoutParams(new LayoutParams(-1, -1));
        ((RelativeLayout) findViewById(R.id.layout)).addView(this.preview);
        this.preview.setKeepScreenOn(true);
        this.btnCapture.setOnClickListener(new C02521());
        this.preview.setOnTouchListener(new C02542());
        this.btnActionRight.setOnClickListener(new C02553());
        this.btnTips.setOnClickListener(new C02564());
        setPhotoState();
    }

    private void makeVerifyDialog() {
        NotifyDialog.newInstance(0, R.string.verify_purchases, new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(R.string.item1), Integer.valueOf(R.string.item1), Integer.valueOf(R.string.item1)}))).show(getSupportFragmentManager(), "dialog");
    }

    private File getPhotoLink() {
        File folder = new File(getExternalCacheDir() + this.folderID);
        File file = new File(folder, "group_" + System.currentTimeMillis() + ".jpg");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return file;
    }

    protected void onResume() {
        super.onResume();
        if (!RxPermissions.getInstance(this).isGranted(Manifest.permission.CAMERA)){
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Subscriber<Boolean>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(GroupCameraActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if(aBoolean)
                                startCamera();
                        }
                    });
        }
        else {
            startCamera();
        }
    }


    private void startCamera() {
        if (Camera.getNumberOfCameras() > 0) {
            try {
                this.camera = Camera.open(this.currentCamera);
                if (this.currentCamera == 0) {
                    this.angle = 90;
                } else {
                    this.angle = 270;
                }
                this.camera.setDisplayOrientation(90);
                this.camera.startPreview();
                Parameters params = this.camera.getParameters();

                if (params.getSupportedFocusModes().contains("continuous-picture")) {
                    params.setFocusMode("continuous-picture");
                }

                this.camera.setParameters(params);
                this.preview.setCamera(this.camera);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.camera_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showImage(Uri uri) {
        this.image.setVisibility(View.VISIBLE);
        stopCamera();
        this.uri = null;
        Picasso.with(this).load(uri).fit().centerCrop().into(this.image);
        setImageState();
    }

    private void setPhotoState() {
        this.btnActionLeft.setText(R.string.camera_cancel);
        this.btnActionLeft.setOnClickListener(new C02575());
        this.btnActionRight.setVisibility(View.INVISIBLE);
    }

    private void setImageState() {
        this.btnActionLeft.setText(R.string.camera_retake);
        this.btnActionLeft.setOnClickListener(new C02586());
        this.btnActionRight.setVisibility(View.VISIBLE);
    }

    private void cancel() {
        stopCamera();
        finish();
    }

    private void submit() {
//        setResult(-1);
//        finish();

        //new added
        if (!(this.uri == null || this.previews.contains(this.uri))) {
            this.previews.add(this.uri);
        }
        Intent intent = new Intent();
        ArrayList<String> data = new ArrayList();
        for (Uri uri : this.previews) {
            data.add(uri.toString());
        }
        intent.putStringArrayListExtra("data", data);
        setResult(-1, intent);
        finish();
    }

    private void retake() {
        this.image.setImageBitmap(null);
        setPhotoState();
        startCamera();
    }

    private void stopCamera() {
        if (this.camera != null) {
            this.camera.stopPreview();
            this.preview.setCamera(null);
            this.camera.release();
            this.camera = null;
        }
    }

    protected void onPause() {
        stopCamera();
        super.onPause();
    }

    public void onShow(Uri uri) {
        showImage(uri);
    }

    public void onDelete(Uri uri) {
        this.previews.remove(this.previews.indexOf(uri));
        this.adapter.notifyDataSetChanged();
    }

    private void updateTakeState() {
        if (this.count > 0) {
            this.btnCapture.setImageResource(R.drawable.ic_take_photo);
        } else {
            this.btnCapture.setImageResource(R.drawable.ic_take_group);
        }
    }

    private void resetCam() {
        this.camera.startPreview();
        this.preview.setCamera(this.camera);
    }
}
