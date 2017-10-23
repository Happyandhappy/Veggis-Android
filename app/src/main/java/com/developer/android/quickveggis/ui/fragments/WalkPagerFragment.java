package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.LoginActivity;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WalkPagerFragment extends Fragment {
    @Bind(R.id.imgBg)
    ImageView bg;
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.btnSkip)
    TextView btnSkip;
    List<Fragment> data;
    Handler handler;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    Timer timer;
    @Bind(R.id.vp)
    ViewPager vp;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.WalkPagerFragment.1 */
    class C03321 implements OnClickListener {
        C03321() {
        }

        public void onClick(View v) {
            WalkPagerFragment.this.startActivity(LoginActivity.getStartIntent(WalkPagerFragment.this.getContext()));
            WalkPagerFragment.this.getActivity().finish();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.WalkPagerFragment.2 */
    class C03332 implements OnClickListener {
        C03332() {
        }

        public void onClick(View v) {
            WalkPagerFragment.this.startActivity(LoginActivity.getStartIntent(WalkPagerFragment.this.getContext()));
            WalkPagerFragment.this.getActivity().finish();
        }
    }

    public class Task extends TimerTask {

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.WalkPagerFragment.Task.1 */
        class C03341 implements Runnable {
            C03341() {
            }

            public void run() {
                if (WalkPagerFragment.this.getActivity() != null) {
                    int current = WalkPagerFragment.this.vp.getCurrentItem();
                    if (current == WalkPagerFragment.this.vp.getAdapter().getCount() - 1) {
                        WalkPagerFragment.this.vp.setCurrentItem(0, true);
                    } else {
                        WalkPagerFragment.this.vp.setCurrentItem(current + 1, true);
                    }
                }
            }
        }

        public void run() {
            WalkPagerFragment.this.handler.post(new C03341());
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return WalkPagerFragment.this.data.get(position);
        }

        public int getCount() {
            return WalkPagerFragment.this.data.size();
        }
    }

    public WalkPagerFragment() {
        this.data = new ArrayList();
    }

    public static WalkPagerFragment newInstance() {
        Bundle args = new Bundle();
        WalkPagerFragment fragment = new WalkPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new Handler();
        fillList();
        Picasso.with(getContext()).load(R.drawable.walkthrogh1).fit().centerCrop().into(bg);
        vp.setAdapter(new PagerAdapter(getChildFragmentManager()));
        indicator.setViewPager(vp);
        btnSkip.setOnClickListener(new C03321());
        btnNext.setOnClickListener(new C03332());
        indicator.setOnPageChangeListener(mPagerListener);
    }

    ViewPager.OnPageChangeListener mPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 2){
                btnNext.setVisibility(View.VISIBLE);
                btnSkip.setVisibility(View.INVISIBLE);
            } else {
                btnNext.setVisibility(View.INVISIBLE);
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void fillList() {
        data.clear();
        data.add(Walk1Fragment.newInstance(R.drawable.walk1_1, R.string.walk1_cashback, R.string.walk1_groceries));
        data.add(Walk1Fragment.newInstance(R.drawable.walk1_2, R.string.walk1_earn_money, R.string.walk1_not_points));
        data.add(Walk1Fragment.newInstance(R.drawable.walk1_3, R.string.walk1_everywhere, R.string.walk1_upload_receipt));
    }
}
