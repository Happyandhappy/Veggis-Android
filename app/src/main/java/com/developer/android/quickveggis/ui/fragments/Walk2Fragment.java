package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.squareup.picasso.Picasso;

public class Walk2Fragment extends Fragment {
    @Bind(R.id.img)
    ImageView image;
    @Bind(R.id.imgIcon)
    ImageView imgIcon;
    @Bind(R.id.txtSubtitle)
    TextView txtSubtitle;
    @Bind(R.id.txtTitle)
    TextView txtTitle;

    public static Walk2Fragment newInstance(int title, int subtitle, int image) {
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("subtitle", subtitle);
        args.putInt("image", image);
        Walk2Fragment fragment = new Walk2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk2_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        this.txtTitle.setText(bundle.getInt("title"));
        this.txtSubtitle.setText(bundle.getInt("subtitle"));
        Picasso.with(getContext()).load(bundle.getInt("image")).fit().centerInside().into(this.image);
        this.imgIcon.setVisibility(View.GONE);
    }
}
