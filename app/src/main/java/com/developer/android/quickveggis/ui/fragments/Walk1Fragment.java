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

public class Walk1Fragment extends Fragment {
    @Bind(R.id.img)
    ImageView image;
    int img;
    int subtitle;
    int title;
    @Bind(R.id.txtSubtitle)
    TextView txtSubtitle;
    @Bind(R.id.txtTitle)
    TextView txtTitle;

    public static Walk1Fragment newInstance(int img, int title, int subtitle) {
        Bundle args = new Bundle();
        args.putInt("image", img);
        args.putInt("title", title);
        args.putInt("subtitle", subtitle);
        Walk1Fragment fragment = new Walk1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walk1_item, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        this.img = bundle.getInt("image");
        this.title = bundle.getInt("title");
        this.subtitle = bundle.getInt("subtitle");
        Picasso.with(getContext()).load(this.img).fit().centerInside().into(this.image);
        this.txtTitle.setText(this.title);
        this.txtSubtitle.setText(this.subtitle);
    }
}
