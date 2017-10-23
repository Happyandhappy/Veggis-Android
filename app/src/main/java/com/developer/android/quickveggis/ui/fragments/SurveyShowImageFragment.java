package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.squareup.picasso.Picasso;

public class SurveyShowImageFragment extends Fragment {
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.img)
    ImageView imageView;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyShowImageFragment.1 */
    class C03221 implements OnClickListener {
        C03221() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(SurveyShowImageFragment.this.getActivity(), (int) R.id.content, SurveyShowImageDataFragment.newInstance(), false);
        }
    }

    public static SurveyShowImageFragment newInstance() {
        Bundle args = new Bundle();
        SurveyShowImageFragment fragment = new SurveyShowImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_image, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext()).load("http://weknowyourdreamz.com/images/apple/apple-01.jpg").fit().centerCrop().into(this.imageView);
        this.btnNext.setOnClickListener(new C03221());
    }
}
