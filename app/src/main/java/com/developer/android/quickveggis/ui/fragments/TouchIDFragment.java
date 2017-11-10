package com.developer.android.quickveggis.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.FingerprintActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Happyandhappy on 10/26/2017.
 */

public class TouchIDFragment extends Fragment{
    public static final String FINGERPRINT_ALLOW_STATE="Fingerprint_state";
    public static final String FINGERPRINT_CHECK_STATE="Fingerprint_result";

    @Bind(R.id.touchid_btn)
    RelativeLayout touchBtn;
    @Bind(R.id.finger_toggle)
    SwitchCompat finger_toggle;

    @OnClick(R.id.touchid_btn)
    public void onClickTouchset(){ toggle_Changed_Request();}
    @OnClick(R.id.finger_toggle)
    public void onClickToggle(){
        toggle_Changed_Request();
    }

    public void toggle_Changed_Request(){
        setState(false);
        startActivity(new Intent(getContext(),FingerprintActivity.class));
    }
    public void setState(Boolean value){
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FINGERPRINT_ALLOW_STATE,value);
        editor.commit();
    }
    public static TouchIDFragment newInstance() {
        Bundle args = new Bundle();
        TouchIDFragment fragment = new TouchIDFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_touchid, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.security_settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        Boolean state=preferences.getBoolean(FINGERPRINT_CHECK_STATE,false);
        setState(state);
        finger_toggle.setChecked(state);
    }
}
