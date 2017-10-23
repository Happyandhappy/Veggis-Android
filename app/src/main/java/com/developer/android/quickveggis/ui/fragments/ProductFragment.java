package com.developer.android.quickveggis.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.api.response.UnlockedTaskCartData;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.controller.SessionController;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.Task;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.ProductActivity;
import com.developer.android.quickveggis.ui.activity.SurveyVideoActivity;
import com.developer.android.quickveggis.ui.activity.TaskActivity;
import com.developer.android.quickveggis.ui.utils.BitmapUtils;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.PreferenceUtil;
import com.quickveggies.quickveggies.ui.custom.SlideButton;
import com.quickveggies.quickveggies.ui.custom.SlideButton.SlideButtonListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.App.shoppingListChanged;
import static com.developer.android.quickveggis.App.wishlistChanged;
import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;
//import xyz.hanks.library.SmallBang;

public class ProductFragment extends Fragment implements SlideButtonListener , MainActivity.MenuController{
    static final int TAB_DESCRIPTION = 2;
    static final int TAB_TC = 1;
    static final int UNLOCKED_TASK_REQUEST_CODE = 200;
    @Bind(R.id.blockDescription)
    View blockDescription;
    @Bind(R.id.blockTC)
    View blockTC;
    @Bind(R.id.btnLike)
    ImageView btnLike;
    @Bind(R.id.btn_slide)
    SlideButton btnSlide;
    @Bind(R.id.img)
    ImageView image;
    //    SmallBang mSmallBang;
    View prevTab;
    @Bind(R.id.tabDescription)
    View tabDescription;
    @Bind(R.id.tabTC)
    View tabTc;
    @Bind(R.id.txtTitle)
    TextView txtTitle;
    @Bind(R.id.txt_date_available)
//    TextView txtAvailable;
//    @Bind(R.id.txt_description)
    TextView txtDescription;

    @Bind(R.id.txtValietyAndSize)
    TextView txtVarietyAndSize;

    @Bind(R.id.txt_validityRestSec)
    TextView txtValidityRestSec;
    @Bind(R.id.txt_validity)
    TextView txtValidity;
    @Bind(R.id.txt_validon)
    TextView txtValidon;
    @Bind(R.id.txt_quantity)
    TextView txtQuantity;
    @Bind(R.id.txt_note)
    TextView txtNote;
    @Bind(R.id.lay_stores)
    LinearLayout lay_storeslist;



    @Bind(R.id.txt_meta_title)
    TextView txtMetaTitle;
    @Bind(R.id.task1_layout)
    RelativeLayout layout_firstTask;
    @Bind(R.id.task2_layout)
    RelativeLayout layout_secondTask;
    @Bind(R.id.task3_layout)
    RelativeLayout layout_thirdTask;

    @Bind(R.id.task1Button)
    RelativeLayout task1Button;
    @Bind(R.id.task2Button)
    RelativeLayout task2Button;
    @Bind(R.id.task3Button)
    RelativeLayout task3Button;

    @Bind(R.id.btn_task2)
    ImageView btnTask2;

    @Bind(R.id.btn_task1)
    ImageView btnTask1;

    @Bind(R.id.btn_task3)
    ImageView btnTask3;

    @Bind(R.id.txt_task1_price)
    TextView txtTask1Price;

    @Bind(R.id.txt_task2_price)
    TextView txtTask2Price;

    @Bind(R.id.txt_task3_price)
    TextView txtTask3Price;

    @Bind(R.id.emptyLayout)
    ScrollView emptyLayout;

    @Bind(R.id.mainLayout)
    LinearLayout mainLayout;

    @Bind(R.id.tutorialLayout)
    RelativeLayout tutorialLayout;

    @Bind(R.id.unlockedPopup)
    RelativeLayout unlockedPopup;

    int selectedTaskNumber = 0;

    Product product;
    ArrayList<Task> tasks;
    ArrayList<String> orderedTasksId;
    ProgressDialog pDialog;
    Task selectedTask;

    boolean unlockSuccess = false;

    public static ProductFragment mFragment;

    @Override
    public int getMenuVisibility() {
        return 3;
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductFragment.1 */
    class C03081 implements OnClickListener {
        C03081() {
        }

        /*public void onClick(View v) {
//            ProductFragment.this.startActivity(SurveyActivity.getStartIntent(ProductFragment.this.getContext())); // origin
//            startActivity(TaskActivity.getStartIntent(getContext())); // new
        }*/

        public void onClick(View v) {

//            Intent intent = TaskActivity.getStartIntent(ProductFragment.this.getContext());
//            v.setAlpha(0.5f);
            switch (v.getId()){
                case R.id.task1Button:
                    selectedTask = (Task)btnTask1.getTag();
                    if (!btnTask1.isEnabled()) {
                        DialogUtils.showUnlockedTaskTutorialDialog(getActivity(), String.format("%s₹", selectedTask.getAmount()));
                        return;
                    }
                    selectedTaskNumber = 1;
                    break;
                case R.id.task2Button:
                    selectedTask = (Task)btnTask2.getTag();
                    if (!btnTask2.isEnabled()) {
                        DialogUtils.showUnlockedTaskTutorialDialog(getActivity(), String.format("%s₹", selectedTask.getAmount()));
                        return;
                    }
                    selectedTaskNumber = 2;
                    break;
                case R.id.task3Button:
                    selectedTask = (Task)btnTask3.getTag();
                    if (!btnTask3.isEnabled()) {
                        DialogUtils.showUnlockedTaskTutorialDialog(getActivity(), String.format("%s₹", selectedTask.getAmount()));
                        return;
                    }
                    selectedTaskNumber = 3;
                    break;
            }

            if (selectedTask != null){
//                Config.selectedTask = selectedTask;
                try {
                    Intent intent;
                    //intent.putExtra("selected_task", selectedTask);
                    if ((Integer.parseInt(selectedTask.getType()) == Config.TASK_DO_GOODER && selectedTask.getTaskData().getVideoUrl() != null && !selectedTask.getTaskData().getVideoUrl().isEmpty())
                            || Integer.parseInt(selectedTask.getType()) == Config.TASK_VIDEO) {

                        intent = SurveyVideoActivity.getStartIntent(ProductFragment.this.getContext());
                        intent.putExtra("task", selectedTask);
                        ProductFragment.this.startActivityForResult(intent, UNLOCKED_TASK_REQUEST_CODE);
                    }
                    else{
                        intent = TaskActivity.getStartIntent(ProductFragment.this.getContext());
                        intent.putExtra("task", selectedTask);
                        ProductFragment.this.startActivityForResult(intent, UNLOCKED_TASK_REQUEST_CODE); // origin
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //startActivity(TaskActivity.getStartIntent(getContext())); // new
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductFragment.2 */
    class C03092 implements OnClickListener {
        C03092() {
        }

        public void onClick(View v) {
            ProductFragment.this.startActivity(SurveyVideoActivity.getStartIntent(ProductFragment.this.getContext()));
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductFragment.3 */
    class C03103 implements OnClickListener {
        C03103() {
        }

        public void onClick(View v) {
            ProductFragment.this.onTabSelected(ProductFragment.TAB_TC);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductFragment.4 */
    class C03114 implements OnClickListener {
        C03114() {
        }

        public void onClick(View v) {
            ProductFragment.this.onTabSelected(ProductFragment.TAB_DESCRIPTION);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductFragment.5 */
    class C03125 implements OnClickListener {
        C03125() {
        }

        public void onClick(View v) {
//            ProductFragment.this.mSmallBang.bang(ProductFragment.this.btnLike);

            addProductToWishList();
        }
    }

    public static ProductFragment newInstance() {
        Bundle args = new Bundle();
        mFragment = new ProductFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    public static ProductFragment newInstance(Bundle args) {
        mFragment = new ProductFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    public static ProductFragment newInstance(Product product) {
        mFragment = new ProductFragment();
        mFragment.product = product;
        return mFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        tasks = new ArrayList<>();
        orderedTasksId = new ArrayList<>();

        getTaskList();

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.kikbac);

        tutorialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        unlockedPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                unlockSuccess = false;
            }
        });

        Picasso.with(getContext()).load(product.getImage()).fit().centerInside().into(this.image);
//        this.image.setImageResource(R.drawable.img_back);

        updateTab(TAB_TC);
        this.tabTc.setSelected(true);
        this.prevTab = this.tabTc;
        this.tabTc.setOnClickListener(new C03103());
        this.tabDescription.setOnClickListener(new C03114());
//        setLockedState(true, this.btnShare, this.txtPriceShare);
        this.btnLike.setOnClickListener(new C03125());

        this.task1Button.setOnClickListener(new C03081());
        this.task2Button.setOnClickListener(new C03081());
        this.task3Button.setOnClickListener(new C03081());

        txtMetaTitle.setText(product.getMetaTitle());
        txtTitle.setText(product.getName());
        /*
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM, dd");
        String validDate = "Offer Valid Till   " + simpleDateFormat.format(product.getAvailableDate());
        txtAvailable.setText(validDate);
        */

        txtDescription.setText(product.getDescription());

        txtVarietyAndSize.setText( product.getVariety() + " - " + product.getSize() );

        this.updateTimeSec();
        txtValidity.setText(product.getDateAvailable());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy, hh:mm a");
        String validDate = simpleDateFormat.format(product.getAvailableDate());
        txtValidity.setText(validDate);

        txtValidon.setText(  "Valid on : " + product.getVariety() + " , " + product.getSize() );
//        Valid stores: Big Bazzar , Moree , Spar

        txtQuantity.setText( "Quantity : " + product.getMinimum() + " Product");

        txtNote.setText( "Note : " + product.getNote());

        // To get store lists
        String stores = product.getValidon().replace("Valid stores:", "");
//        stores = stores.replace(", ", "\n");
        String[] tokens = stores.split(", ");

//        txt_storeslist.setText(stores);

        for (int i = 0; i < tokens.length; i ++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_product_store, lay_storeslist, false);
            TextView txtView = (TextView) view.findViewById(R.id.txtStore);
            txtView.setText(tokens[i]);

            lay_storeslist.addView(view);
        }

        checkIsLiked();
    }

    private void updateTimeSec() {

        new CountDownTimer(3000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Date date = product.getAvailableDate();

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());

                long compareTime = c.getTime().getTime() / 1000;

                long compareTime1 = date.getTime() / 1000;

                compareTime = (compareTime1 - compareTime);

                if (Math.abs(compareTime) < 60) {
                    txtValidityRestSec.setText(compareTime +"s");
                } else if (Math.abs(compareTime) < 3600) {
                    txtValidityRestSec.setText((compareTime / 60) + "m " + compareTime % 60 +"s");
                } else if (Math.abs(compareTime) < 3600 * 24) {
                    long h = compareTime / 3600;
                    long m = (compareTime % 3600) / 60;
                    //  txtValidityRestSec.setText(h + "h " + m + "m " + (compareTime % 60) +"s");
                    txtValidityRestSec.setText(h + "h " + m + "m");
                } else {
                    long d = compareTime / (3600 * 24);
                    long h = (compareTime % (3600 * 24)) / 3600;
                    //  long m = (compareTime % 3600) / 60;
                    //  txtValidityRestSec.setText(d + "d " + h + "h " + m + "m " + (compareTime % 60) +"s");
                    txtValidityRestSec.setText(d + "d " + h + "h");
                }
            }

            public void onFinish() {

            }

        }.start();

    }

    private void setLockedState(boolean state, View btn, TextView txt) {
        btn.setSelected(state);
        txt.setSelected(state);
    }

    public void onTabSelected(int i) {
        if (i == TAB_TC) {
            updateTab(TAB_TC);
        } else {
            updateTab(TAB_DESCRIPTION);
        }
    }

    private void updateTab(int tab) {
        if (tab == TAB_TC) {
            this.btnSlide.startAnimation(TAB_TC);
            this.tabTc.setSelected(true);
            this.tabDescription.setSelected(false);
            this.blockTC.setVisibility(View.VISIBLE);
            this.blockDescription.setVisibility(View.GONE);
            return;
        }
        this.btnSlide.startAnimation(100);
        this.tabTc.setSelected(false);
        this.tabDescription.setSelected(true);
        this.blockDescription.setVisibility(View.VISIBLE);
        this.blockTC.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (App.getTask()){
//            DialogUtils.showAlertUnLockDialog(getActivity());
//            App.setTask(true);
//        }
    }

    private void getTaskList(){

        ServiceAPI.newInstance().getProductTasksById(product.getId(), new ResponseCallback<ArrayList<Task>>() {
            @Override
            public void onSuccess(ArrayList<Task> data) {
                if (data != null && data.size() > 0)
                    tasks.addAll(data);
//                initTaskViews();
                getTaskWalletStatus();
            }

            @Override
            public void onFailure(String error) {
//                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getTaskWalletStatus() {
        String customerID = CustomerController.getInstance().getLoggedInCustomer().getCustomerId();

        String productID = product.getId();

        ServiceAPI.newInstance().getProductOrderedTasksById(customerID, productID, new ResponseCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                emptyLayout.setVisibility(View.GONE);
                startFadeInAnim(mainLayout);
                if (data != null && data.size() > 0) {

                    orderedTasksId.clear();
                    orderedTasksId.addAll(data);

                    initTaskViews();
                } else {
                    initTaskViews();
                }

                if (PreferenceUtil.getBooleanFromPreference(getActivity(), Config.PRODUCT_TUTORIAL_VISIBLE, true)) {
                    tutorialLayout.setVisibility(View.VISIBLE);
                    PreferenceUtil.saveBooleanToPreference(getActivity(), Config.PRODUCT_TUTORIAL_VISIBLE, false);
                } else {
                    tutorialLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(String error) {
//                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                initTaskViews();
            }
        });
    }

    private void checkIsLiked() {
        String customerID = CustomerController.getInstance().getLoggedInCustomer().getCustomerId();

        String productID = product.getId();

        ServiceAPI.newInstance().checkIsWishProduct(customerID, productID, new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {

//                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

                if (data.contains("not")) {
                    btnLike.setImageResource(R.drawable.icon_unliked_count);
                } else {
                    btnLike.setImageResource(R.drawable.icon_liked_count);
                }
            }

            @Override
            public void onFailure(String error) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isDisabledTask(String taskId) {

        for (String strTemp : orderedTasksId) {
            if (strTemp.equals(taskId)) {
                return true;
            }
        }

        return false;
    }

    private void initTaskViews(){

        String task1Price;
        String task2Price;
        String task3Price;

        if (tasks.size() == 0) {
            layout_firstTask.setVisibility(View.GONE);
        } else

        if (tasks.size() == 1){
            layout_secondTask.setVisibility(View.GONE);
            layout_thirdTask.setVisibility(View.GONE);
            layout_firstTask.setVisibility(View.VISIBLE);

            task1Price = String.format("%s₹", tasks.get(0).getAmount());
            btnTask1.setTag(tasks.get(0));
            txtTask1Price.setText(task1Price);

            if (tasks.get(0).getDisableTask() == 1 || this.isDisabledTask(tasks.get(0).getId())){
                btnTask1.setEnabled(false);
                BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), false);
                txtTask1Price.setEnabled(false);
//                btnTask1.setAlpha(0.5f);
            }
            else{
                BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), true);
                btnTask1.setEnabled(true);
                txtTask1Price.setEnabled(true);
//                btnTask1.setAlpha(1.0f);
            }
        }
        else if (tasks.size() == 2){
            layout_thirdTask.setVisibility(View.GONE);
            btnTask1.setTag(tasks.get(0));
            btnTask2.setTag(tasks.get(1));
            layout_firstTask.setVisibility(View.VISIBLE);
            layout_secondTask.setVisibility(View.VISIBLE);

            task1Price = String.format("%s₹", tasks.get(0).getAmount());
            task2Price = String.format("%s₹", tasks.get(1).getAmount());
            txtTask1Price.setText(task1Price);
            txtTask2Price.setText(task2Price);

            if (tasks.get(0).getDisableTask() == 1 || this.isDisabledTask(tasks.get(0).getId())){
                btnTask1.setEnabled(false);
                BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), false);
                txtTask1Price.setEnabled(false);
//                btnTask1.setAlpha(0.5f);
            }
            else{
                BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), true);
                btnTask1.setEnabled(true);
                txtTask1Price.setEnabled(true);
//                btnTask1.setAlpha(1.0f);
            }

            if (tasks.get(1).getDisableTask()==1 || this.isDisabledTask(tasks.get(1).getId())){
                btnTask2.setEnabled(false);
                BitmapUtils.setTaskImage(btnTask2, Integer.parseInt(tasks.get(1).getType()), false);
                txtTask2Price.setEnabled(false);
//                btnTask2.setAlpha(0.5f);
            }
            else{
                btnTask2.setEnabled(true);
                BitmapUtils.setTaskImage(btnTask2, Integer.parseInt(tasks.get(1).getType()), true);
                txtTask2Price.setEnabled(true);
//                btnTask2.setAlpha(1.0f);
            }
        }
        else if (tasks.size() == 3){
            btnTask1.setTag(tasks.get(0));
            btnTask2.setTag(tasks.get(1));
            btnTask3.setTag(tasks.get(2));
            layout_firstTask.setVisibility(View.VISIBLE);
            layout_secondTask.setVisibility(View.VISIBLE);
            layout_thirdTask.setVisibility(View.VISIBLE);

            task1Price = String.format("%s₹", tasks.get(0).getAmount());
            task2Price = String.format("%s₹", tasks.get(1).getAmount());
            task3Price = String.format("%s₹", tasks.get(2).getAmount());
            txtTask1Price.setText(task1Price);
            txtTask2Price.setText(task2Price);
            txtTask3Price.setText(task3Price);

            if (tasks.get(0).getDisableTask() == 1 || this.isDisabledTask(tasks.get(0).getId())){
                btnTask1.setEnabled(false);
                BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), false);
                txtTask1Price.setEnabled(false);
//                btnTask1.setAlpha(0.5f);
            }
            else{
                BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), true);
                btnTask1.setEnabled(true);
                txtTask1Price.setEnabled(true);
//                btnTask1.setAlpha(1.0f);
            }

            if (tasks.get(1).getDisableTask() == 1 || this.isDisabledTask(tasks.get(1).getId())){
                btnTask2.setEnabled(false);
                BitmapUtils.setTaskImage(btnTask2, Integer.parseInt(tasks.get(1).getType()), false);
                txtTask2Price.setEnabled(false);
//                btnTask2.setAlpha(0.5f);
            }
            else{
                btnTask2.setEnabled(true);
                BitmapUtils.setTaskImage(btnTask2, Integer.parseInt(tasks.get(1).getType()), true);
                txtTask2Price.setEnabled(true);
//                btnTask2.setAlpha(1.0f);
            }

            if (tasks.get(2).getDisableTask() == 1 || this.isDisabledTask(tasks.get(2).getId())){
                btnTask3.setEnabled(false);
                BitmapUtils.setTaskImage(btnTask3, Integer.parseInt(tasks.get(2).getType()), false);
                txtTask3Price.setEnabled(false);
//                btnTask3.setAlpha(0.5f);
            }
            else{
                btnTask3.setEnabled(true);
                BitmapUtils.setTaskImage(btnTask3, Integer.parseInt(tasks.get(2).getType()), true);
                txtTask3Price.setEnabled(true);
//                btnTask3.setAlpha(1.0f);
            }
        }
        else{
            layout_secondTask.setVisibility(View.GONE);
            layout_thirdTask.setVisibility(View.GONE);
            layout_firstTask.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNLOCKED_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            Toast.makeText(getActivity(), "Unlocked task", Toast.LENGTH_SHORT).show();
            if (data != null){
                if (selectedTask.getId().equals(data.getStringExtra(Config.KEY_UNLOCKED_TASK_ID))) {
//                    Config.list_cart.add(currentProduct);
                    addUnlockedTaskToCart();
//                    isExistCartList(currentProduct);
                }
            }
        }
    }

    private void addUnlockedTaskToCart(){
//        final ProgressDialog loginDialog = new ProgressDialog(getActivity());
//        loginDialog.setMessage("Adding unlocked task to your cart...");
//        loginDialog.setCancelable(false);
//        loginDialog.show();

        unlockSuccess = false;
        showGifAnimator();

        UnlockedTaskCartData data = new UnlockedTaskCartData(CustomerController.getInstance().getLoggedInCustomer().getCustomerId(), product.getId(), selectedTask.getId(),
                selectedTask.getAmount(), SessionController.getInstance().getLoggedInSession(), "1");
        ServiceAPI.newInstance().addProductTaskToCart(data, new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
//                loginDialog.dismiss();
                switch (selectedTaskNumber){
                    case 1:
                        btnTask1.setEnabled(false);
                        BitmapUtils.setTaskImage(btnTask1, Integer.parseInt(tasks.get(0).getType()), false);
                        txtTask1Price.setEnabled(false);
                        break;
                    case 2:
                        btnTask2.setEnabled(false);
                        BitmapUtils.setTaskImage(btnTask2, Integer.parseInt(tasks.get(1).getType()), false);
                        txtTask2Price.setEnabled(false);
                        break;
                    case 3:
                        btnTask3.setEnabled(false);
                        BitmapUtils.setTaskImage(btnTask3, Integer.parseInt(tasks.get(2).getType()), false);
                        txtTask3Price.setEnabled(false);
                        break;
                }
                shoppingListChanged = true;
                unlockSuccess = true;
            }

            @Override
            public void onFailure(String error) {
//                loginDialog.dismiss();
                DialogUtils.dismissUnlockDialog();
            }
        });
    }

    public void showGifAnimator(){
        InputStream is = getResources().openRawResource(R.drawable.unlock_to_redeem);
        Movie movie = Movie.decodeStream(is);
        int duration = movie.duration();
        DialogUtils.showAlertUnLockDialog(getActivity());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                initTaskViews();
                DialogUtils.dismissUnlockDialog();
                if (unlockSuccess) {
                    unlockedPopup.setVisibility(View.VISIBLE);
                }

            }
        }, duration);
    }

    private void addProductToWishList(){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        ServiceAPI.newInstance().addProductToWishList(product.getId(), new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Successfully added to Wishlist!", Toast.LENGTH_SHORT).show();
                btnLike.setImageResource(R.drawable.icon_liked_count);
                wishlistChanged = true;
            }

            @Override
            public void onFailure(String error) {
                pDialog.dismiss();
//                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
