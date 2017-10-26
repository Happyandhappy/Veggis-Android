package com.developer.android.quickveggis.ui.fragments;
// Add by happyandhappy 10/26/2017

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;

import butterknife.ButterKnife;

public class SecuritySettingsFragment extends Fragment {

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
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.security_settings);
        ((ProfileActivity)getActivity()).btnSave.setVisibility(View.VISIBLE);
    }


}