package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.CartItem;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.model.NotificationModel;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.event.Logout;
import com.developer.android.quickveggis.ui.BadgeDrawable;
import com.developer.android.quickveggis.ui.fragments.CartFragment;
import com.developer.android.quickveggis.ui.fragments.CategoriesFragment;
import com.developer.android.quickveggis.ui.fragments.ConversationListFragment;
import com.developer.android.quickveggis.ui.fragments.MenuFragment;
import com.developer.android.quickveggis.ui.fragments.NotificationListFragment;
import com.developer.android.quickveggis.ui.fragments.ProductFragment;
import com.developer.android.quickveggis.ui.fragments.SupportListFragment;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.joda.time.MutableDateTime;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final int MENU_EDIT_VISIBLE = 4;
    public static final int MENU_PRODUCT_VISIBLE = 3;
    public static final int MENU_CART_VISIBLE = 2;
    public static final int MENU_NOT_VISIBLE = 1;
    public static final int MENU_VISIBLE = 0;

    public static final int FROM_CART = 1;
    public static final int FROM_PRODUCTS_ACTIVITY = 2;

    public int where = 0;

    @Bind(R.id.appBar)
    AppBarLayout appBarLayout;
    private OnBackStackChangedListener backStackListener;
//    @Bind(R.id.collapse_toolbar)
//    CollapsingToolbarLayout collapsingToolbar;
    int currentMenuVisibility;
    @Bind(R.id.drawerLayout)
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtOffline)
    public TextView txtOffline;

    public static MainActivity instance;

    /* renamed from: com.quickveggies.quickveggies.ui.activity.MainActivity.2 */
    class C02632 implements OnClickListener {
        final /* synthetic */ MenuItem menuItem;

        C02632(MenuItem menuItem) {
            this.menuItem = menuItem;
        }

        public void onClick(View v) {
            MainActivity.this.onOptionsItemSelected(menuItem);
        }
    }

    public interface MenuController {
        int getMenuVisibility();
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.MainActivity.3 */
    class C05593 implements OnBackStackChangedListener {
        C05593() {
        }

        public void onBackStackChanged() {
            updateMenu(FragmentUtils.getCurrent(MainActivity.this));
            setNavIcon();
        }
    }

    public MainActivity() {
        backStackListener = new C05593();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static Intent getStartIntent(Context context, int mode) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("mode", mode);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().getEventBus().unregister(this);
    }

    Fragment        currentFragment = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         //     Hotline
         */
        HotlineConfig hlConfig=new HotlineConfig("c0213431-7b89-4235-aea8-6233dadab310","958dca37-374a-4548-92a0-49a7acce482e");
        hlConfig.setVoiceMessagingEnabled(true);
        hlConfig.setCameraCaptureEnabled(true);
        hlConfig.setPictureMessagingEnabled(true);

        Hotline.getInstance(getApplicationContext()).init(hlConfig);

        currentFragment = CategoriesFragment.newInstance();

        App.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);

        instance = this;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportFragmentManager().addOnBackStackChangedListener(backStackListener);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.setToolbarNavigationClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSupportNavigateUp();
            }
        });
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getCustomerInfo();

        int mode = getIntent().getIntExtra("mode", -1);

        if (mode == Config.PRODUCT_MODE) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            setExpanded(false);
//            collapsingToolbar.setEnabled(false);
            Product product = new Gson().fromJson(getIntent().getExtras().getString("product"), Product.class);
            FragmentUtils.changeFragment(this, R.id.content, ProductFragment.newInstance(product), false);
        } else if (mode == -1) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentUtils.changeFragment(this, R.id.content, currentFragment, false);
        } else {//cartFragment - offmode = 2, onmode=1
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            FragmentUtils.changeFragment(this, R.id.content, CartFragment.newInstance(mode), false);
        }
//        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
//        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
    }

//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        int mode = intent.getIntExtra("mode", -1);
//        FragmentUtils.popBackStack(this);
//        FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, CartFragment.newInstance(mode), false);
//    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public boolean onPrepareOptionsMenu(Menu menu) { // because of this, the app crash ???

        setBadge(menu, R.id.action_messages, R.drawable.ic_message, MENU_NOT_VISIBLE); //
        setBadge(menu, R.id.action_cart, R.drawable.ic_shopping, MENU_VISIBLE);
        setBadge(menu, R.id.action_notifications, R.drawable.ic_reminder, MENU_CART_VISIBLE);

        switch (currentMenuVisibility) {
            case MutableDateTime.ROUND_NONE /*0*/:
                menu.findItem(R.id.action_messages).setVisible(true);
                menu.findItem(R.id.action_cart).setVisible(true);
                menu.findItem(R.id.action_notifications).setVisible(true);
                menu.findItem(R.id.action_share).setVisible(false);
                menu.findItem(R.id.action_edit).setVisible(false);
                break;
            case MENU_NOT_VISIBLE /*1*/:
                menu.findItem(R.id.action_messages).setVisible(false);
                menu.findItem(R.id.action_cart).setVisible(false);
                menu.findItem(R.id.action_notifications).setVisible(false);
                menu.findItem(R.id.action_share).setVisible(false);
                menu.findItem(R.id.action_edit).setVisible(false);
                break;
            case MENU_CART_VISIBLE /*2*/:
                menu.findItem(R.id.action_messages).setVisible(false);
                menu.findItem(R.id.action_cart).setVisible(true);
                menu.findItem(R.id.action_notifications).setVisible(false);
                menu.findItem(R.id.action_share).setVisible(false);
                menu.findItem(R.id.action_edit).setVisible(false);
                break;
            case MENU_PRODUCT_VISIBLE:/*3*/
                menu.findItem(R.id.action_messages).setVisible(false);
                menu.findItem(R.id.action_cart).setVisible(true);
                menu.findItem(R.id.action_notifications).setVisible(false);
                menu.findItem(R.id.action_share).setVisible(true);
                menu.findItem(R.id.action_edit).setVisible(false);
                break;
            case MENU_EDIT_VISIBLE:/*4*/
                menu.findItem(R.id.action_messages).setVisible(false);
                menu.findItem(R.id.action_cart).setVisible(false);
                menu.findItem(R.id.action_notifications).setVisible(false);
                menu.findItem(R.id.action_share).setVisible(false);
                menu.findItem(R.id.action_edit).setVisible(true);
                break;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void setBadge(Menu menu, int menuRes, int res, int badgeCount) {

        // To set Notification red dot if there is new Notification
        MenuItem itemNoti = menu.findItem(R.id.action_notifications);
        LayerDrawable iconNoti = (LayerDrawable) itemNoti.getIcon();
        setBadgeCount(this, iconNoti, NotificationModel.isNewNotification(this));

        // To set cart count on Cart Menu
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        this.getCartCount(icon);

        // To click menu
        MenuItem menuItem = menu.findItem(menuRes);
        View view = menuItem.getActionView();
        view.setOnClickListener(new C02632(menuItem));
    }

    public void setExpanded(boolean expanded) {
        appBarLayout.setExpanded(expanded, true);
    }

    public void setTitle(int titleId) {
        super.setTitle(titleId);
//        collapsingToolbar.setTitle(getString(titleId));
    }

    public void updateMenu(Fragment fragment) {
        if (fragment != null && (fragment instanceof MenuController)) {
            currentMenuVisibility = ((MenuController) fragment).getMenuVisibility();
            invalidateOptionsMenu();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void setNavIcon() {
        mDrawerToggle.setDrawerIndicatorEnabled(getSupportFragmentManager().getBackStackEntryCount() == 0);
    }

    public void moveIntoConversationsActivity() {
        FragmentUtils.popBackStack(this);
        FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, ConversationListFragment.newInstance(), false);
    }

    public void moveIntoSupportActivity() {
        FragmentUtils.popBackStack(this);
        FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, SupportListFragment.newInstance(), false);
    }

    public void setCurrentFragment(Fragment fragment) {
        currentFragment = fragment;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.isDrawerIndicatorEnabled() && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_notifications && getSupportFragmentManager().popBackStackImmediate()) {//???
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_messages:

                Hotline.showConversations((FragmentActivity) this);

//                FragmentUtils.popBackStack(this);
//                FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, SupportListFragment.newInstance(), false);

                break;

            case R.id.action_cart:
//                startActivity(CartActivity.getStartIntent(this));

//                currentFragment.startActivity(CartActivity.getStartIntent(getApplicationContext()));

                mDrawerToggle.setDrawerIndicatorEnabled(false);
                FragmentUtils.popBackStack(this);
                if (CartFragment.mFragment == null) {
                    FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, CartFragment.newInstance(2), false);//2 = offlinemode
                } else {
                    FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, CartFragment.mFragment, false);//2 = offlinemode
                }

//                FragmentUtils.changeFragment(currentFragment.getActivity(), R.id.content, CartFragment.newInstance(2), false);//2 = offlinemode

                break;

            case R.id.action_notifications:

                FragmentUtils.popBackStack(this);
                FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, NotificationListFragment.newInstance(), false);
//                FragmentUtils.changeFragment(currentFragment.getActivity(), R.id.content, NotificationListFragment.newInstance(), false);
//                categoriesFragment.moveIntoNotificationFragment();

//                startActivity(NotificationActivity.getStartIntent(this));
                break;
            case R.id.action_share:
                App.getInstance().intentShare(this, "", "");
                break;
            case R.id.action_edit:
                if (item.getTitle().equals(getResources().getString(R.string.action_edit))) {
                    CartFragment.editShopList(true);
                    item.setTitle(getResources().getString(R.string.action_done));
                } else if (item.getTitle().equals(getResources().getString(R.string.action_done))) {
                    CartFragment.editShopList(false);
                    item.setTitle(getResources().getString(R.string.action_edit));
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {

        if (ProductsActivity._inst != null) {
            onBackPressed();
        } else {
            currentFragment = CategoriesFragment.newInstance();
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentUtils.changeFragment(this, R.id.content, currentFragment, false);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        }

        return true;
    }

    public void onBackPressed() {

        super.onBackPressed();
    }

    public void openMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }

    public void toggleMenu() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtOffline.setVisibility(View.INVISIBLE);

        switch (where){
            case FROM_CART:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentUtils.changeFragment(this, R.id.content, currentFragment, false);
                break;

            case FROM_PRODUCTS_ACTIVITY:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentUtils.changeFragment(this, R.id.content, currentFragment, false);
                break;
        }
    }

    void getCustomerInfo() {
        ServiceAPI.newInstance().getAccountDetails(new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {
                CustomerController.getInstance().saveLoginCustomer(data);
                FragmentUtils.changeFragment(MainActivity.this, R.id.menu, MenuFragment.newInstance(), false);
            }

            @Override
            public void onFailure(String error) {
                FragmentUtils.changeFragment(MainActivity.this, R.id.menu, MenuFragment.newInstance(), false);
            }
        });
    }

    void getCartCount(final LayerDrawable icon) {

        setBadgeCount(getApplicationContext(), icon, String.valueOf(0));

        ServiceAPI.newInstance().getCartItems(new ResponseCallback<ArrayList<CartItem>>() {
            @Override
            public void onSuccess(ArrayList<CartItem> data) {

                setBadgeCount(getApplicationContext(), icon, String.valueOf(data.size()));
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Subscribe
    public void onMessage(Logout event){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.REQUEST_CODE_SHARE){
            invalidateOptionsMenu();
        }
    }
}
