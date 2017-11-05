package com.developer.android.quickveggis.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Happyandhappy on 10/26/2017.
 */

public class TouchIDFragment extends Fragment{
    public static final String FINGERPRINT_ALLOW_STATE="Fingerprint_state";
    @Bind(R.id.touchid_btn)
    RelativeLayout touchBtn;
    @Bind(R.id.finger_toggle)
    SwitchCompat finger_toggle;

    @OnClick(R.id.touchid_btn)
    public void onClickTouchset(){
        finger_toggle.setChecked(!finger_toggle.isChecked());
        toggle_Changed_Request();
    }
    @OnClick(R.id.finger_toggle)
    public void onClickToggle(){
        toggle_Changed_Request();
    }

    public void toggle_Changed_Request(){
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (finger_toggle.isChecked()){
            editor.putBoolean(FINGERPRINT_ALLOW_STATE,true);
            Toast.makeText(getActivity(),"Fingerprint Allowed",Toast.LENGTH_SHORT).show();
        }else{
            editor.putBoolean(FINGERPRINT_ALLOW_STATE,false);
            Toast.makeText(getActivity(),"Fingerprint not Allowed",Toast.LENGTH_SHORT).show();
        }
        editor.commit();
        FragmentUtils.changeFragment(getActivity(),R.id.content,FingerprintFragment.newInstance(),true);
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
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        Boolean state=preferences.getBoolean(FINGERPRINT_ALLOW_STATE,false);
        finger_toggle.setChecked(state);
    }
}
