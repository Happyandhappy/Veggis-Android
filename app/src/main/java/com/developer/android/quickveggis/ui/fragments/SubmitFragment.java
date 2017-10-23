package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.OrderCreateData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.CartItem;
import com.developer.android.quickveggis.model.Order;
import com.developer.android.quickveggis.ui.dialog.ActionListener;
import com.developer.android.quickveggis.ui.dialog.WeGotITDialog;
//import com.quickveggies.quickveggies.api.model.OCRData;
//import com.quickveggies.quickveggies.api.model.RedeemData;
//import com.quickveggies.quickveggies.api.response.OCRResponse;
//import com.quickveggies.quickveggies.ui.dialog.ActionListener;
//import com.quickveggies.quickveggies.ui.dialog.WeGotITDialog;
//import com.quickveggies.quickveggies.ui.utils.AndroidUriUtils;

import java.util.ArrayList;

public class SubmitFragment extends Fragment
        implements ActionListener {

    private final static String TASK_GROUP_IMAGE_UPLOAD_MODE        = "GROUP";
    private final static String TASK_RECEIPT_IMAGES_UPLOAD_MODE     = "RECEIPT";

    static final int DIALOG_GOT_IT = 1;

    boolean isCancel = false;

    @Bind(R.id.btnCancel)
    Button btnCancel;

    ArrayList<String> receiptImagePath = new ArrayList<>();
    ArrayList<String> groupImagePath = new ArrayList<>();

    ArrayList<String> receiptImageUris = new ArrayList<>();
    ArrayList<String> groupImageUris = new ArrayList<>();

    ArrayList<CartItem> cartItems;

    @Bind(R.id.progressBar)
    ProgressBar circleProgressBar;

    @Bind(R.id.txt_percent)
    TextView txtPercent;

    @Bind(R.id.txtProgress)
    TextView txtProgress;

    //MultipartEntity entity;
    int uploadedCount  = 0;
    long totalSize = 0;
    int currentUploadedPercent = 0;
    int totalImageCount = 0;

    int receiptImageCount = 0;
    int groupImageCount   = 0;

    ProgressDialog dialog;
    int currentProgress = 0;

//    AndroidMultiPartEntity entity;
//    ImageUploadTask imageUploadTask;

    int receiptImageIndex = 0;
    int groupImageIndex = 0;
    boolean isSendingRecieptImage = false;

    String orderId = "0";

    private void gotItDialog() {
        if (isCancel){
            return;
        }
        setSentState();
        WeGotITDialog localWeGotITDialog = WeGotITDialog.newInstance(0);
        localWeGotITDialog.show(getChildFragmentManager(), "dialog");
        localWeGotITDialog.setListener(this);
    }

    public static SubmitFragment newInstance() {
        Bundle localBundle = new Bundle();
//        localBundle.putStringArrayList("checks", paramArrayList1);
//        localBundle.putStringArrayList("group", paramArrayList2);
        SubmitFragment localSubmitFragment = new SubmitFragment();
//        localSubmitFragment.setArguments(localBundle);
        return localSubmitFragment;
    }

    private void setSendingState() {
        this.btnCancel.setVisibility(View.VISIBLE);
        //this.txtProgress.setVisibility(View.VISIBLE);
        //this.progressBar.setVisibility(0);
    }

    private void setSentState() {
        this.btnCancel.setVisibility(View.GONE);
        this.txtProgress.setVisibility(View.GONE);
        //this.progressBar.setVisibility(8);
    }

    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        getActivity().setTitle(R.string.submit);

//        Bundle localBundle = getArguments();
//        if (getArguments() != null){
        if (Config.list_receipt_images != null) {
            this.receiptImagePath = getImagePathsfromUri(Config.list_receipt_images);

            receiptImageCount = this.receiptImagePath.size();
            receiptImageUris = Config.list_receipt_images;

            Bitmap bitmap = ServiceAPI.newInstance().getBitmap(this.receiptImagePath.get(0).toString());

            if (bitmap != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
            }
        }

        if (Config.list_group_images != null) {
            this.groupImagePath = getImagePathsfromUri(Config.list_group_images);
            groupImageCount = this.groupImagePath.size();
            groupImageUris = Config.list_group_images;
        }

        totalImageCount = receiptImageCount + groupImageCount;
//        }

        circleProgressBar.setMax(100);
        circleProgressBar.setProgress(0);
        //circleProgressBar.setRotation((-currentProgress / 100f * 360f) - 90f);

        txtPercent.setText("0%");
//        imageUploadTask = new ImageUploadTask();

        sendData();
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                isCancel = true;
//                if (imageUploadTask != null && imageUploadTask.getStatus() == AsyncTask.Status.RUNNING)
//                    imageUploadTask.cancel(isCancel);
                ServiceAPI.newInstance().cancelUpload(isSendingRecieptImage);
                SubmitFragment.this.getActivity().finish();
            }
        });
    }

    private ArrayList<String > getImagePathsfromUri(ArrayList<String> imgUris){
        ArrayList<String > realPaths = new ArrayList<>();
        for (int i = 0; i < imgUris.size(); i++){
            String imgPath = getRealPathFromUri(getActivity(),Uri.parse(imgUris.get(i)));
            realPaths.add(imgPath);
        }

        return realPaths;
    }

    public void onContinueClicked(int paramInt) {
        if (paramInt == DIALOG_GOT_IT)
            getActivity().finish();
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.fragment_submit, paramViewGroup, false);
        ButterKnife.bind(this, localView);
        return localView;
    }

    private void testest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotItDialog();
            }
        }, 3000);
    }

    public void sendData() {
        setSendingState();
        //for test
        //testest();
//        if (this.receiptImagePath.size() > 0)
//            imageUploadTask.execute(uploadedCount + "", "receipt_" + uploadedCount + ".jpg", TASK_RECEIPT_IMAGES_UPLOAD_MODE);

        // get products from cart
        ServiceAPI.newInstance().getCartItems(new ResponseCallback<ArrayList<CartItem>>() {
            @Override
            public void onSuccess(ArrayList<CartItem> data) {
                cartItems = data;
                createOrder();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    void createOrder(){
        OrderCreateData data = new OrderCreateData();
        data.createTotalAndProducts(cartItems);
        data.createCustomerInfo(CustomerController.getInstance().getLoggedInCustomer());
        data.createOtherValues();

        ServiceAPI.newInstance().createOrder(data, new ResponseCallback<Order>() {
            @Override
            public void onSuccess(Order data) {

                orderId = data.getOrderId();
                //sending image after creating order
                if (receiptImageUris.size() > 0)
                {
                    receiptImageIndex = 0;
                    sendReceiptImage();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void sendReceiptImage(){
        isSendingRecieptImage = true;
        ServiceAPI.newInstance().uploadReceiptImage(orderId, Uri.parse(receiptImageUris.get(receiptImageIndex)), new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                receiptImageIndex++;
                int process = (int) ((double)receiptImageIndex/totalImageCount * 100);
                setCurrentProgress(process);
                if (receiptImageIndex < receiptImageUris.size()) {
                    sendReceiptImage();
                }
                else {
                    groupImageIndex = 0;
                    sendGroupImage();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void sendGroupImage(){
        isSendingRecieptImage = false;
        ServiceAPI.newInstance().uploadTaskGroupImage(orderId, Uri.parse(groupImageUris.get(groupImageIndex)), new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                groupImageIndex ++;
                int process = (int) ((double)(groupImageIndex + receiptImageUris.size())/totalImageCount * 100);
                setCurrentProgress(process);
                if (groupImageIndex < groupImageUris.size())
                    sendGroupImage();
                else {
                    setSentState();
                    showGotItDialog();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setCurrentProgress(final int progress){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                circleProgressBar.setProgress(progress);
                txtPercent.setText(String.format("%d", progress) + "%");
            }
        });
    }

    private String getRealPathFromUri(Context context, Uri contentUri){

        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
            cursor = loader.loadInBackground();
            if (cursor == null){
                return contentUri.getPath();
            }else{
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        }finally {
            if (cursor != null)
                cursor.close();
        }
    }

//    class ImageUploadTask extends AsyncTask<String, Integer, String> {
//
//        String sResponse = null;
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
////            dialog = ProgressDialog.show(getActivity(), "Uploading",
////                    "Please wait...", true);
////            dialog.show();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            currentProgress = currentUploadedPercent + (int)(values[0] / totalImageCount);
//            //circleProgressBar.setRotation((-currentProgress / 100f * 360f) - 90f);
//            if (uploadedCount == totalImageCount - 1 && values[0] == 100){
//                circleProgressBar.setProgress(100);
//                txtPercent.setText(String.valueOf(100) + "%");
//            }else{
//                circleProgressBar.setProgress(currentProgress);
//                txtPercent.setText(String.valueOf(currentProgress) + "%");
//            }
//            //super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            JSONObject resultJson;
//            if (isCancelled()) return null;
//            try {
//
//                String url;
//                if (params[2].equalsIgnoreCase(TASK_GROUP_IMAGE_UPLOAD_MODE))
//                    url = Config.API_UPLOAD_RECEIPT_IMAGES;
//                else
//                    url = Config.API_UPLOAD_GROUP_IMAGE;
//
//                int i = Integer.parseInt(params[0]);
//                Bitmap bitmap = null;
//                if (uploadedCount < receiptImageCount)
//                    bitmap = BitmapUtils.decodeFile(receiptImagePath.get(i));
//                else{
//                    if (uploadedCount < totalImageCount)
//                        bitmap = BitmapUtils.decodeFile(groupImagePath.get(i - receiptImageCount));
//                }
//
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpContext localContext = new BasicHttpContext();
//                HttpPost httpPost = new HttpPost(url);
//
//                //entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//                entity  = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {
//                    @Override
//                    public void transferred(long num) {
//                        publishProgress((int)(num / (float)totalSize) * 100);
//                    }
//                });
//
//                if (bitmap != null){
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                    byte[] data = bos.toByteArray();
//
//
//                    entity.addPart("order_id", new StringBody("25"));
//                    entity.addPart("receipt_images[]", new ByteArrayBody(data,
//                            "image/jpeg", params[1]));
//
//                    httpPost.addHeader(Config.KEY_API_HEADER_SECURITY, "12345");
//                    httpPost.addHeader(Config.KEY_API_HEADER_SESSION, Config.SESSION_ID);
//
//                    totalSize = entity.getContentLength();
//
//                    httpPost.setEntity(entity);
//                    HttpResponse response = httpClient.execute(httpPost,
//                            localContext);
//
//                    if (response.getStatusLine().getStatusCode() == 200){
//                        HttpEntity entity = response.getEntity();
//                        resultJson = getJsonRes((InputStream)entity.getContent());
//                        if (resultJson != null){
//                            sResponse = resultJson.getString("success");
//                        }
//                    }
//
//                    //sResponse = EntityUtils.getContentCharSet(response.getEntity());
//
//                    System.out.println("sResponse : " + sResponse);
//                }
//
//            } catch (Exception e) {
////                if (dialog.isShowing())
////                    dialog.dismiss();
//                Log.e(e.getClass().getName(), e.getMessage(), e);
//
//            }
//            return sResponse;
//        }
//
//        @Override
//        protected void onPostExecute(String sResponse) {
//            try {
////                if (dialog.isShowing())
////                    dialog.dismiss();
//                if (sResponse != null) {
//                    //Toast.makeText(getActivity(),sResponse + " Photo uploaded successfully", Toast.LENGTH_SHORT).show();
//                    uploadedCount++;
//                    currentUploadedPercent += 100 / totalImageCount;
//                    if (uploadedCount < receiptImagePath.size()) {
//                        imageUploadTask = new ImageUploadTask();
//                        imageUploadTask.execute(uploadedCount + "", "receipt_" + uploadedCount
//                                + ".jpg", TASK_RECEIPT_IMAGES_UPLOAD_MODE);
//                    }
//                    else if (uploadedCount < totalImageCount){
//                        imageUploadTask = new ImageUploadTask();
//                        imageUploadTask.execute(uploadedCount + "", "group_" + uploadedCount
//                                + ".jpg", TASK_GROUP_IMAGE_UPLOAD_MODE);
//                    }
//                    else{
//                        //showGotItDialog();
//                        SubmitFragment.this.getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //Toast.makeText(SubmitFragment.this.getActivity(), "Photos uploaded successfully.", Toast.LENGTH_SHORT).show();
//                                showGotItDialog();
//                            }
//                        });
//                    }
//                }
//
//            } catch (Exception e) {
////                SubmitFragment.this.getActivity().runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        Toast.makeText(SubmitFragment.this.getActivity(), "Upload failed. Please try again later.", Toast.LENGTH_SHORT).show();
////                        SubmitFragment.this.getActivity().finish();
////
////                    }
////                });
//                Log.e(e.getClass().getName(), e.getMessage(), e);
//            }
//
//        }
//
//        private String readRes(InputStream is) throws Exception{
//            BufferedReader r = new BufferedReader(new InputStreamReader(is));
//            String res = "";
//            String line;
//            while ((line = r.readLine()) != null) {
//                res += line;
//            }
//            return res;
//        }
//
//        private JSONObject getJsonRes(InputStream is) throws Exception{
//            return new JSONObject(readRes(is));
//        }
//    }

    private void showGotItDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WeGotITDialog notifyDialog = WeGotITDialog.newInstance(DIALOG_GOT_IT);
                notifyDialog.setListener(SubmitFragment.this);
                notifyDialog.show(SubmitFragment.this.getActivity().getSupportFragmentManager(), "dialog");
            }
        });
    }

}