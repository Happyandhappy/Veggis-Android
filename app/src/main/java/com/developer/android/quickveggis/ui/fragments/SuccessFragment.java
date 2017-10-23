package com.developer.android.quickveggis.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.PayoutActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.felipecsl.gifimageview.library.GifImageView;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SuccessFragment extends Fragment {
    @Bind(R.id.btnNext)
    View btnNext;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SuccessFragment.1 */
    class C03161 implements OnClickListener {
        C03161() {
        }

        public void onClick(View v) {
            //SuccessFragment.this.getActivity().finish();
            if (getActivity() instanceof PayoutActivity) {
                getActivity().finish();//PayoutAcivity
            } else {
                FragmentUtils.addFragmentAtFirst(getActivity(), R.id.content, CategoriesFragment.newInstance(), false);//GiftCardFragment ???
            }
        }
    }

    public static SuccessFragment newInstance() {
        Bundle args = new Bundle();
        SuccessFragment fragment = new SuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        GifImageView gifView = (GifImageView)view.findViewById(R.id.gifView);
        gifView.setBackgroundColor(Color.TRANSPARENT);

        try {
            InputStream is = getActivity().getAssets().open("check_animation2.gif");
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
        this.btnNext.setOnClickListener(new C03161());
    }
}
