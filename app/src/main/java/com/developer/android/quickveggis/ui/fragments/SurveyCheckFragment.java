package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

public class SurveyCheckFragment extends Fragment {
    List<String> answers;
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.checkAnswers)
    LinearLayout checkAnswers;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyCheckFragment.1 */
    class C03171 implements OnClickListener {
        C03171() {

        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(SurveyCheckFragment.this.getActivity(), (int) R.id.content, SurveyShowImageFragment.newInstance(), false);
        }
    }

    public SurveyCheckFragment() {
        this.answers = new ArrayList();
    }

    public static SurveyCheckFragment newInstance() {
        Bundle args = new Bundle();
        SurveyCheckFragment fragment = new SurveyCheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_check, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.btnNext.setOnClickListener(new C03171());
        fillTest();
        fillAnswers();
    }

    private void fillTest() {
        this.answers.clear();
        for (int i = 0; i < 4; i++) {
            this.answers.add("Checkbox Answer " + (i + 1));
        }
    }

    private void fillAnswers() {
        this.checkAnswers.removeAllViews();
        for (String answer : this.answers) {
            CheckBox radioButton = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_survey_checkbox, this.checkAnswers, false);
            radioButton.setText(answer);
            this.checkAnswers.addView(radioButton);
        }
        getView().invalidate();
        this.checkAnswers.requestLayout();
    }
}
