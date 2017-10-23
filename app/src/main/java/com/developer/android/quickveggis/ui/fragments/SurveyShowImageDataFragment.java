package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.squareup.picasso.Picasso;
import java.util.Arrays;
import java.util.List;

public class SurveyShowImageDataFragment extends Fragment {
    @Bind(R.id.blockItems)
    LinearLayout blockItems;
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.img)
    ImageView imageView;
    List<String> items;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyShowImageDataFragment.1 */
    class C03211 implements OnClickListener {
        C03211() {
        }

        public void onClick(View v) {
//            FragmentUtils.changeFragment(SurveyShowImageDataFragment.this.getActivity(), (int) R.id.content, SurveyEditFragment.newInstance(), false);
        }
    }

    public SurveyShowImageDataFragment() {
        this.items = Arrays.asList(new String[]{"Add a table spoon of", "Add a table spoon of"});
    }

    public static SurveyShowImageDataFragment newInstance() {
        Bundle args = new Bundle();
        SurveyShowImageDataFragment fragment = new SurveyShowImageDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_image_data, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext()).load("http://weknowyourdreamz.com/images/apple/apple-01.jpg").fit().centerCrop().into(this.imageView);
        this.btnNext.setOnClickListener(new C03211());
        fillItems();
    }

    private void fillItems() {
        this.blockItems.removeAllViews();
        for (int i = 0; i < this.items.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_survey_data, this.blockItems, false);
            TextView txtContent = (TextView) view.findViewById(R.id.txtTitle);
            ((TextView) view.findViewById(R.id.txtNumber)).setText(String.valueOf(i + 1));
            txtContent.setText((CharSequence) this.items.get(i));
            this.blockItems.addView(view);
        }
    }
}
