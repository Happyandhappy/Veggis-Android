package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.db.AddressRepo;
import com.developer.android.quickveggis.ui.fragments.AddressListFragment;
import com.developer.android.quickveggis.ui.fragments.NoAddressFoundFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class OrderActivity extends AppCompatActivity {
    public static final int TAB_ADDRESS = 0;
    public static final int TAB_PAYMENT = 2;
    public static final int TAB_TIMESLOT = 1;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: com.quickveggies.quickveggies.ui.activity.OrderActivity.3 */
    class C02643 implements OnTouchListener {
        C02643() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    public interface TabChanger {
        int getTab();
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.OrderActivity.1 */
    class C05601 implements OnTabSelectedListener {
        C05601() {
        }

        public void onTabSelected(Tab tab) {
            OrderActivity.this.setTitle(tab.getText());
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.activity.OrderActivity.2 */
    class C05612 implements OnBackStackChangedListener {
        C05612() {
        }

        public void onBackStackChanged() {
            Fragment fragment = FragmentUtils.getCurrent(OrderActivity.this);
            if (fragment instanceof TabChanger) {
                OrderActivity.this.tabLayout.getTabAt(((TabChanger) fragment).getTab()).select();
            }
        }
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, OrderActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_order);
        ButterKnife.bind(this);
        addTab(R.string.address_tab);
        addTab(R.string.timeslot_tab);
        addTab(R.string.payment_tab);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.tabLayout.setOnTabSelectedListener(new C05601());
        this.tabLayout.getTabAt(TAB_ADDRESS).select();
        setTitle(R.string.address_tab);
        updateAddressFragment();
        getSupportFragmentManager().addOnBackStackChangedListener(new C05612());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {///???
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    public void updateAddressFragment() {
        FragmentUtils.popBackStack(this);
        if (AddressRepo.getInstance().getCount() > 0) {
            FragmentUtils.changeFragment(this, R.id.content, AddressListFragment.newInstance(), false);
        } else {
            FragmentUtils.changeFragment(this, R.id.content, NoAddressFoundFragment.newInstance(), false);
        }
    }

    private void addTab(int res) {
        Tab tab = this.tabLayout.newTab();
        tab.setText(getString(res));
        this.tabLayout.addTab(tab);
        LinearLayout tabStrip = (LinearLayout) this.tabLayout.getChildAt(TAB_ADDRESS);
        for (int i = TAB_ADDRESS; i < tabStrip.getChildCount(); i += TAB_TIMESLOT) {
            tabStrip.getChildAt(i).setOnTouchListener(new C02643());
        }
    }
}
