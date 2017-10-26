package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;

import butterknife.ButterKnife;

/**
 * Created by Happyandhappy on 10/26/2017.
 */


public class ChangePasswordFragment extends Fragment {
    public static ChangePasswordFragment newInstance(){
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.changepassword);
        ((ProfileActivity)getActivity()).btnSave.setVisibility(View.VISIBLE);
    }
}
