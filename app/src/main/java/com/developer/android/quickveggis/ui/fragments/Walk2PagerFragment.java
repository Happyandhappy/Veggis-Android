package com.developer.android.quickveggis.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Walk2PagerFragment extends Fragment {
    @Bind(R.id.imgBg)
    ImageView bg;
    @Bind(R.id.btnSkip)
    View btnSkip;
    List<Fragment> data;
    Handler handler;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.btnNext)
    View btnNext;
    Timer timer;
    @Bind(R.id.vp)
    ViewPager vp;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.Walk2PagerFragment.2 */
    class C03292 implements OnClickListener {
        C03292() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);

            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.Walk2PagerFragment.3 */
    class C03303 implements Runnable {
        C03303() {
        }

        public void run() {
            Fragment fragment = (Fragment) Walk2PagerFragment.this.data.get(0);
            if (fragment instanceof AnimatedFragment) {
                ((AnimatedFragment) fragment).animate();
            }
        }
    }

    public interface AnimatedFragment {
        void animate();
    }

    public class Task extends TimerTask {

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.Walk2PagerFragment.Task.1 */
        class C03311 implements Runnable {//auto pager

            C03311() {
            }

            public void run() {
                if (Walk2PagerFragment.this.getActivity() != null) {
                    int current = Walk2PagerFragment.this.vp.getCurrentItem();
                    if (current == Walk2PagerFragment.this.vp.getAdapter().getCount() - 1) {
                        Walk2PagerFragment.this.vp.setCurrentItem(0, true);
                    } else {
                        Walk2PagerFragment.this.vp.setCurrentItem(current + 1, true);
                    }
                }
            }
        }

        public void run() {
//            Walk2PagerFragment.this.handler.post(new C03311());  // this prevents auto pager
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.Walk2PagerFragment.1 */
    class C05731 implements OnPageChangeListener {
        C05731() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            if (position == 4){
                btnSkip.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            } else {
                btnSkip.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            }
            if (Walk2PagerFragment.this.getActivity() != null) {
                Fragment fragment = (Fragment) Walk2PagerFragment.this.data.get(position);
                if (fragment instanceof AnimatedFragment) {//for animation 3 pages
                    ((AnimatedFragment) fragment).animate();
                }
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) Walk2PagerFragment.this.data.get(position);
        }

        public int getCount() {
            return Walk2PagerFragment.this.data.size();
        }
    }

    public Walk2PagerFragment() {
        this.data = new ArrayList();
    }

    public static Walk2PagerFragment newInstance() {
        Bundle args = new Bundle();
        Walk2PagerFragment fragment = new Walk2PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk2_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.handler = new Handler();
        fillList();
        Picasso.with(getContext()).load(R.drawable.walkthrogh1).fit().centerCrop().into(this.bg);
        this.vp.setAdapter(new PagerAdapter(getChildFragmentManager()));
        this.indicator.setViewPager(this.vp);
        this.vp.addOnPageChangeListener(new C05731());
        this.btnSkip.setOnClickListener(new C03292());
        this.btnNext.setOnClickListener(new C03292());
        this.handler.postDelayed(new C03303(), 500);
        this.timer = new Timer(true);
        this.timer.scheduleAtFixedRate(new Task(), 3000, 3000);
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.timer != null) {
            this.timer.cancel();
        }
    }

    private void fillList() {
        data.clear();
        //animation for 3 imageIcons
        data.add(Walk2AnimatedFragment.newInstance(R.string.walk2_1_title, R.string.walk2_1_subtitle, R.drawable.ic_walk2_1, R.drawable.ic_walk2_1_icon, R.dimen.walk1_margin, -1));
        data.add(Walk2AnimatedFragment.newInstance(R.string.walk2_2_title, R.string.walk2_1_subtitle, R.drawable.ic_walk2_2, R.drawable.ic_walk2_2_icon, R.dimen.walk3_marginRight, R.dimen.walk3_marginBottom));
        data.add(Walk2AnimatedFragment.newInstance(R.string.walk2_3_title, R.string.walk2_3_subtitle, R.drawable.ic_walk2_3, R.drawable.ic_walk2_3_icon, R.dimen.walk2_margin, -1));

        data.add(Walk2Fragment.newInstance(R.string.walk2_3_title, R.string.walk2_3_subtitle, R.drawable.ic_walk2_4));
        data.add(Walk2Fragment.newInstance(R.string.walk2_3_title, R.string.walk2_3_subtitle, R.drawable.ic_walk2_5));
    }
}
