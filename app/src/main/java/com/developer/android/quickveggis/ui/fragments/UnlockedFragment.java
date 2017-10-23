package com.developer.android.quickveggis.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.CartSelectionActivity;
import com.felipecsl.gifimageview.library.GifImageView;

import java.io.InputStream;

public class UnlockedFragment extends Fragment {
    @Bind(R.id.btnNext)
    View btnNext;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.UnlockedFragment.1 */

    public static UnlockedFragment newInstance() {
        Bundle args = new Bundle();
        UnlockedFragment fragment = new UnlockedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unlocked, container, false);
        GifImageView gifView = (GifImageView)view.findViewById(R.id.gifView);
        gifView.setBackgroundColor(Color.TRANSPARENT);

        try {
            InputStream is = getActivity().getAssets().open("drawable/unlock_to_redeem.gif");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();

            gifView.setBytes(bytes);
            gifView.startAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UnlockedFragment.this.getActivity().startActivity(CartSelectionActivity.getStartIntent(UnlockedFragment.this.getContext()));
                UnlockedFragment.this.getActivity().finish();
            }
        });
    }
}
