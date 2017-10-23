package com.developer.android.quickveggis.ui.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CheckOrderFragment extends Fragment {
    @Bind(R.id.blockReceipt)
    View blockReceipt;
    @Bind(R.id.imgReceipt)
    ImageView imgReceipt;
    @Bind(R.id.imgTop)
    ImageView imgTop;
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.btnTrack)
    View btnTrack;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.CheckOrderFragment.1 */
    class C05631 implements Callback {
        C05631() {
        }

        public void onSuccess() {
            Log.d("Picasso", "Success");
//            CheckOrderFragment.this.playAnimation();
        }

        public void onError() {
        }
    }

    public static CheckOrderFragment newInstance() {
        Bundle args = new Bundle();
        CheckOrderFragment fragment = new CheckOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_check, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Picasso.with(getContext()).load((int) R.drawable.ic_receipe_top).into(this.imgTop);
        Picasso.with(getContext()).load((int) R.drawable.ic_check).into(this.imgReceipt, new C05631());
    }

    private void playAnimation() {
        ObjectAnimator translateY = ObjectAnimator.ofFloat(this.blockReceipt, "translationY", new float[]{this.blockReceipt.getTranslationY(), 0.0f});
        translateY.setDuration(700);
        translateY.setInterpolator(new LinearInterpolator());
        translateY.setStartDelay(200);
        translateY.start();
    }
}
