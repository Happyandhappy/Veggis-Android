package com.developer.android.quickveggis.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.SocialLoginData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.model.Wallet;
import com.developer.android.quickveggis.ui.activity.ConversationActivity;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.activity.TutorialActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.PreferenceUtil;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment {
    MenuAdapter adapter;
    @Bind(R.id.blockUser)

    View blockUser;
    List<Menu> data;

    @Bind(R.id.imgAvatar)
    ImageView imgAvatar;

    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.txtName)
    TextView txtName;

    @Bind(R.id.txtFirstCharacterName)
    TextView txtFirstCharacterName;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.MenuFragment.2 */
    class C03032 implements OnClickListener {
        C03032() {
        }

        public void onClick(View v) {
            MenuFragment.this.onItemClicked(Menu.Setting);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.MenuFragment.3 */
    static /* synthetic */ class MenuItems {
        static final /* synthetic */ int[] values;

        static {
            values = new int[Menu.values().length];
            try {
                values[Menu.Wallet.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                values[Menu.Wishlist.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                values[Menu.Category.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                values[Menu.Setting.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                values[Menu.History.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
//            try {
//                values[Menu.Service.ordinal()] = 6;
//            } catch (NoSuchFieldError e6) {
//            }
            try {
                values[Menu.Help.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    enum Menu {
        Category(R.string.categories, R.drawable.menu_cat),
        History(R.string.history, R.drawable.menu_history),
        Wallet(R.string.wallet, R.drawable.menu_wallet),
        Wishlist(R.string.wishlist, R.drawable.menu_whishlist),
        Setting(R.string.settings, R.drawable.menu_setting),
        Bonuses(R.string.bonuses, R.drawable.menu_bonuses),
        Service(R.string.service, R.drawable.menu_service),
        Help(R.string.help, R.drawable.menu_tutorial);

        int icon;
        int title;

        private Menu(int title, int icon) {
            this.title = title;
            this.icon = icon;
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.MenuFragment.1 */
    class C05661 implements RecyclerItemClickListener.OnItemClickListener {
        C05661() {
        }

        public void onItemClick(View view, int position) {
            MenuFragment.this.onItemClicked(MenuFragment.this.data.get(position));
        }
    }

    public class MenuAdapter extends Adapter<MenuAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.imgIcon)
            ImageView imgIcon;
            @Bind(R.id.txt_badge)
            TextView txtBadge;
            @Bind(R.id.txtTitle)
            TextView txtTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(MenuFragment.this.getContext()).inflate(R.layout.item_menu, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            Menu menu = MenuFragment.this.data.get(position);
            holder.imgIcon.setImageResource(menu.icon);
            holder.txtTitle.setText(menu.title);
            String wallAmount = CustomerController.getInstance().getLoggedInCustomer().getWallet().getWalletTotal();
            if (wallAmount != null)
                holder.txtBadge.setText(wallAmount);
            else
                holder.txtBadge.setText("0");

            if (menu.equals(Menu.Wallet)) {
                holder.txtBadge.setVisibility(View.VISIBLE);
            } else {
                holder.txtBadge.setVisibility(View.GONE);
            }
        }

        public int getItemCount() {
            return MenuFragment.this.data.size();
        }
    }

    public MenuFragment() {
        this.data = Arrays.asList(new Menu[]{Menu.Category, Menu.History, Menu.Wallet, Menu.Wishlist, Menu.Setting, Menu.Help});    //  Menu.Service,
    }

    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);

        Customer customer = CustomerController.getInstance().getLoggedInCustomer();

        if (customer.getFirstname() != null && customer.getLastname()!= null) {
            //this.txtName.setText(UserInfo.getInstance().getFirstName() + " " + UserInfo.getInstance().getLastName());
            String userName = String.format("Hi, %s %s", customer.getFirstname(), customer.getLastname());
            this.txtName.setText(userName);
            String capUsername = customer.getFirstname().toUpperCase();

            this.txtFirstCharacterName.setText(String.valueOf(capUsername.charAt(0)));
        }

        SocialLoginData socialCustomer ; // = CustomerController.getInstance().getLoggedInSocialCustomer();
        socialCustomer = this.readSocialData();

        if ( !socialCustomer.getProvider().contentEquals("") ) {  // socialCustomer != null

            if (socialCustomer.getProvider().contentEquals("facebook")) {

                new DownloadImageTask().execute(socialCustomer.getProfileimg());
            }
        }

        return view;
    }

    private SocialLoginData readSocialData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);

        String profileImg = preferences.getString("profileImg","");
        String provider = preferences.getString("provider","");

        SocialLoginData data = new SocialLoginData("", "", provider, profileImg);

        return data;
    }

    private class DownloadImageTask extends AsyncTask {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            imgAvatar.setImageBitmap(result);
            txtFirstCharacterName.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            return loadImageFromNetwork((String) params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {

            if (result != null) {
                Bitmap bitmap = (Bitmap)result;

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);

                if (circleBitmap != null ) {
                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);

                    imgAvatar.setImageBitmap(circleBitmap);
                    txtFirstCharacterName.setVisibility(View.GONE);
                }
            }
        }
    }

    private Bitmap loadImageFromNetwork(String strUrl) {

        Bitmap fb_img = null;
        try {
            URL fb_url = new URL(strUrl);//small | noraml | large
            HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
            HttpsURLConnection.setFollowRedirects(true);
            conn1.setInstanceFollowRedirects(true);
            fb_img = BitmapFactory.decodeStream(conn1.getInputStream());

        }catch (Exception ex) {
            ex.printStackTrace();

            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

        return fb_img;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateUserInfo();
        getWalletAmount();
    }

    private void initWidget(){
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new MenuAdapter();
        this.rv.setAdapter(this.adapter);
        this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05661()));
        this.blockUser.setOnClickListener(new C03032());
    }

    private void initRecycleView(){
        this.adapter.notifyDataSetChanged();
    }

    private void onItemClicked(Menu menu) {
        ((MainActivity) getActivity()).closeMenu();
        Fragment fragment = null;
        switch (MenuItems.values[menu.ordinal()]) {
            case 1   /*Wallet*/:
                if (WalletFragment.mFragment == null) {
                    fragment = WalletFragment.newInstance();
                } else {
                    fragment = WalletFragment.mFragment;
                }
                break;
            case 2 /*Wishlist*/:
                if (WishlistFragment.mFragment == null) {
                    fragment = WishlistFragment.newInstance();
                } else {
                    fragment = WishlistFragment.mFragment;
                }
                break;
            case 3 /*Categories*/:
                if (CategoriesFragment.mFragment == null) {
                    fragment = CategoriesFragment.newInstance();
                } else {
                    fragment = CategoriesFragment.mFragment;
                }
                break;
            case 4 /*Setting - Profile*/:
                startActivity(ProfileActivity.getStartIntent(getContext()));
                break;
            case 5 /*RedeemHistory*/:
//                fragment = OrderHistoryFragment.newInstance();    //show tablayout
                fragment = HistoryListFragment.newInstance(1);
                break;
            case 6 /*Help*/:
//                fragment = ConversationListFragment.newInstance();
//                startActivity(ConversationActivity.getStartIntent(getContext()));
                showHelpPopupDialog();
                break;
        }

        ((MainActivity) getActivity()).setCurrentFragment(fragment);

        if (fragment != null) {
            FragmentUtils.popBackStack(getActivity());
            FragmentUtils.changeFragment(getActivity(), R.id.content, fragment, false);
        }
    }

    private void showHelpPopupDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_help);

        TextView tutorial = (TextView) dialog.findViewById(R.id.tutorial);
        TextView howToUse = (TextView) dialog.findViewById(R.id.howToUse);
        TextView helpCenter = (TextView) dialog.findViewById(R.id.helpCenter);

        tutorial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.saveBooleanToPreference(getActivity(), Config.PRODUCTS_TUTORIAL_VISIBLE, true);
                PreferenceUtil.saveBooleanToPreference(getActivity(), Config.PRODUCT_TUTORIAL_VISIBLE, true);
                PreferenceUtil.saveBooleanToPreference(getActivity(), Config.CART_TUTORIAL_VISIBLE, true);
                dialog.dismiss();
            }
        });

        howToUse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        helpCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updateUserInfo() {
        Picasso.with(getContext()).load(R.drawable.img_green_background).placeholder(R.drawable.img_green_background).fit().centerCrop().into(this.imgAvatar);
    }

    private void getWalletAmount(){

        ServiceAPI.newInstance().getWallet(CustomerController.getInstance().getLoggedInCustomer().getCustomerId(), new ResponseCallback<Wallet>() {
            @Override
            public void onSuccess(Wallet data) {
                Customer customer = CustomerController.getInstance().getLoggedInCustomer();
                customer.setWallet(data);
                CustomerController.getInstance().saveLoginCustomer(customer);
                initWidget();
//                initRecycleView();
            }

            @Override
            public void onFailure(String error) {
                Customer customer = CustomerController.getInstance().getLoggedInCustomer();
                customer.setWallet(new Wallet("0", "0"));
                CustomerController.getInstance().saveLoginCustomer(customer);
            }
        });
//        OkHttpTask walletTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                MenuFragment.this.getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        //loginDialog.cancel();
//                        try {
//                            if (jsonRes.getBoolean("success")) {
//                                JSONObject walletJson = jsonRes.getJSONObject("data");
//                                UserInfo.getInstance().setWalletAmount(walletJson.getInt("wallet_total"));
//                                UserInfo.getInstance().setPendingAmount(walletJson.getInt("pending_earnings"));
//
//                                //Toast.makeText(getActivity(), "Got Wallet Amount", Toast.LENGTH_LONG).show();
//                            } else {
//                                //Toast.makeText(getActivity(), "Failed to get Wallet", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        initRecycleView();
//                    }
//                });
//            }
//
//            @Override
//            public void errorConnect() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //loginDialog.cancel();
//                        initRecycleView();
//                        //DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.connect_error));
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //loginDialog.cancel();
//                        initRecycleView();
//                        Toast.makeText(getActivity(), "Error while trying to get Wallet Amount", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }, OkHttpTask.GET, null);
//
//        //walletTask.execute(Config.API_GET_WALLET_AMOUNT + UserInfo.getInstance().getId());
//        walletTask.execute(Config.API_GET_WALLET_AMOUNT + "3");
    }
}