package com.developer.android.quickveggis.ui.fragments;
// Add by happyandhappy 10/26/2017
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SecuritySettingsFragment extends Fragment {
    @Bind(R.id.btn_security_changepassword)
    RelativeLayout btn_security_changepassword;
    @Bind(R.id.btn_security_finger)
    RelativeLayout btn_security_finger;

    @OnClick(R.id.btn_security_changepassword)
    public void onClickChangePassword(){
        FragmentUtils.changeFragment(getActivity(),R.id.content,ChangePasswordFragment.newInstance(), true);
    }

    @OnClick(R.id.btn_security_finger)
    public void onClickTouchID(){
        FragmentUtils.changeFragment(getActivity(),R.id.content,TouchIDFragment.newInstance(),true);
    }
    public static SecuritySettingsFragment newInstance() {
        Bundle args = new Bundle();
        SecuritySettingsFragment fragment = new SecuritySettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security_settings, container, false);
        ButterKnife.bind((Object) this, view);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.security_settings);

    }
}
