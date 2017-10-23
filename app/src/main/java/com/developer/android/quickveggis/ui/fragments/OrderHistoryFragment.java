package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment implements MainActivity.MenuController {
    ViewPagerAdapter adapter;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.vp)
    ViewPager vp;

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            this.mFragmentList = new ArrayList();
            this.mFragmentTitleList = new ArrayList();
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public String getPageTitle(int position) {
            return ((String) this.mFragmentTitleList.get(position)).toUpperCase();
        }

        public String getToolbarTitle(int pos) {
            return (String) this.mFragmentTitleList.get(pos);
        }
    }

    public static OrderHistoryFragment newInstance() {
        Bundle args = new Bundle();
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.history);
        setupViewPager(this.vp);
        this.tabLayout.setupWithViewPager(this.vp);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(HistoryListFragment.newInstance(1), getString(R.string.redeemeed_tab));
        adapter.addFragment(HistoryListFragment.newInstance(2), getString(R.string.deliveries_tab));
        viewPager.setAdapter(this.adapter);
    }

    public int getMenuVisibility() {
        return 1;
    }
}