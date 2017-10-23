package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.OrderActivity;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import java.util.Date;

import picker.ugurtekbas.com.Picker.TimeChangedListener;

public class TimeRangeFragment extends Fragment implements OrderActivity.TabChanger, TimeChangedListener {
    private static final int RANGE_SIZE = 30;
    DateTime dateFinish;
    DateTime dateStart;
    @Bind(R.id.imgMoring)
    ImageView imgMoring;
    @Bind(R.id.imgNoon)
    ImageView imgNoon;
    @Bind(R.id.imgEven)
    ImageView imgEven;
//    @Bind(R.id.amPicker)
//    Picker picker;
//    @Bind(R.id.txtEnd)
//    TextView txtEnd;
//    @Bind(R.id.txtStart)
//    TextView txtStart;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.TimeRangeFragment.1 */
    class C03271 implements OnClickListener {
        C03271() {
        }

        public void onClick(View v) {
//            if (TimeRangeFragment.this.dateStart == null && TimeRangeFragment.this.dateFinish == null)
//            {
//                DialogUtils.showAlertDialog(TimeRangeFragment.this.getContext(), (int) R.string.please_select_range);
//            }
//            else
            {
                FragmentUtils.changeFragment(TimeRangeFragment.this.getActivity(), (int) R.id.content, PaymentOrderFragment.newInstance(), true);
            }
        }
    }

    public static TimeRangeFragment newInstance() {
        Bundle args = new Bundle();
        TimeRangeFragment fragment = new TimeRangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_range, container, false);
//        ButterKnife.bind((Object) this, view);
        imgMoring = (ImageView)view.findViewById(R.id.imgMoring);
        imgNoon = (ImageView)view.findViewById(R.id.imgNoon);
        imgEven = (ImageView)view.findViewById(R.id.imgEven);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        this.btnNext.setOnClickListener(new C03271());
//        this.picker.setTimeChangedListener(this);
        imgMoring.setOnClickListener(mOnClickListener);
        imgNoon.setOnClickListener(mOnClickListener);
        imgEven.setOnClickListener(mOnClickListener);
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentUtils.changeFragment(TimeRangeFragment.this.getActivity(), (int) R.id.content, PaymentOrderFragment.newInstance(), true);
        }
    };

    private void updateTimes(int start, int end) {
        float startF = ((float) (start * RANGE_SIZE)) / 60.0f;
//        this.txtEnd.setText(formatTime(((float) (end * RANGE_SIZE)) / 60.0f));
//        this.txtStart.setText(formatTime(startF));
    }

    private String formatTime(float time) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf((int) time));
        sb.append(".");
        sb.append(String.format("%02.0f", new Object[]{Float.valueOf(60.0f * (time % 1.0f))}));
        return sb.toString();
    }

    public int getTab() {
        return 1;
    }

    @Override
    public void timeChanged(Date date) {

    }

    public void timeChanged(DateTime date, DateTime date2) { ///???
        if (date.plusHours(3).isAfter((ReadableInstant) date2)) {
            DialogUtils.showAlertDialog(getContext(), (int) R.string.please_select_range);
            this.dateStart = null;
            this.dateFinish = null;
            return;
        }
        this.dateStart = date;
        this.dateFinish = date2;
    }
}
