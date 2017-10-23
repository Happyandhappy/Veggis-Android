package com.developer.android.quickveggis.ui.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.squareup.picasso.Picasso;

public class Walk2AnimatedFragment extends Fragment implements Walk2PagerFragment.AnimatedFragment {
    @Bind(R.id.img)
    ImageView image;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.txtSubtitle)
    TextView txtSubtitle;
    @Bind(R.id.txtTitle)
    TextView txtTitle;

    public static Walk2AnimatedFragment newInstance(int title, int subtitle, int image, int icon, int marginRightIcon, int marginBottomIcon) {
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("subtitle", subtitle);
        args.putInt("image", image);
        args.putInt("icon", icon);
        args.putInt("marginRightIcon", marginRightIcon);
        args.putInt("marginBottomIcon", marginBottomIcon);
        Walk2AnimatedFragment fragment = new Walk2AnimatedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk2_item, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        this.txtTitle.setText(bundle.getInt("title"));
        this.txtSubtitle.setText(bundle.getInt("subtitle"));
        Picasso.with(getContext()).load(bundle.getInt("image")).fit().centerInside().into(this.image);
        Picasso.with(getContext()).load(bundle.getInt("icon")).fit().centerInside().into(this.imgIcon);
        LayoutParams params = (LayoutParams) this.imgIcon.getLayoutParams();
        int marginRight = getResources().getDimensionPixelSize(bundle.getInt("marginRightIcon"));
        int marginBottom = -1;
        if (bundle.getInt("marginBottomIcon") != -1) {
            marginBottom = getResources().getDimensionPixelSize(bundle.getInt("marginBottomIcon"));
        }
        if (marginBottom == -1) {
            marginBottom = params.bottomMargin;
        }
        params.setMargins(0, 0, marginRight, marginBottom);
    }

    public void animate() {
        zoomIn();
    }

    //adonis
    private int ANIMATION_TIME = 0;

    public void zoomIn() {
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(this.imgIcon, "scaleX", new float[]{0.0f, 1.0f});
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(this.imgIcon, "scaleY", new float[]{0.0f, 1.0f});
        scaleDownY.setDuration(ANIMATION_TIME);
        scaleDownX.setDuration(ANIMATION_TIME);
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.setInterpolator(new DecelerateInterpolator());
        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();
    }
}
