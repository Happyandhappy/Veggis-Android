package com.developer.android.quickveggis.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.MainActivity;

public class CartSelectionFragment extends Fragment {
    @Bind(R.id.btnOffline)
    View btnOffline;
    @Bind(R.id.btnWeDeliver)
    View btnWeDeliver;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.CartSelectionFragment.1 */
    class C02921 implements OnClickListener {
        C02921() {
        }

        public void onClick(View v) {
            CartSelectionFragment.this.openMode(CartFragment.MODE_OFFLINE);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.CartSelectionFragment.2 */
    class C02932 implements OnClickListener {
        C02932() {
        }

        public void onClick(View v) {
            CartSelectionFragment.this.openMode(CartFragment.MODE_WE_DELIVER);
        }
    }

    public static CartSelectionFragment newInstance() {
        Bundle args = new Bundle();
        CartSelectionFragment fragment = new CartSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_selection, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.btnOffline.setOnClickListener(new C02921());
        this.btnWeDeliver.setOnClickListener(new C02932());
    }

    public void onResume() {
        super.onResume();
        translate();
    }

    private void translate() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x / 2;
        ObjectAnimator translate1X = ObjectAnimator.ofFloat(this.btnWeDeliver, "translationX", new float[]{(float) (-width), 0.0f});
        ObjectAnimator translate2X = ObjectAnimator.ofFloat(this.btnOffline, "translationX", new float[]{(float) width, 0.0f});
        translate1X.setInterpolator(new DecelerateInterpolator());
        translate2X.setInterpolator(new DecelerateInterpolator());
        AnimatorSet as = new AnimatorSet();
        as.playTogether(new Animator[]{translate1X, translate2X});
        as.setStartDelay(200);
        as.setDuration(600);
        as.start();
    }

    private void openMode(int mode) {
        Intent intent = MainActivity.getStartIntent(getContext(), mode);
        intent.setFlags(AccessibilityNodeInfoCompat.ACTION_PASTE);
        intent.setFlags(268435456);///???
        startActivity(intent);
        getActivity().finish();
    }
}
