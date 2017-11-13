package com.developer.android.quickveggis.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.developer.android.quickveggis.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Happyandhappy on 11/10/2017.
 */

public class ServerErrorFragment extends Fragment{
    @Bind(R.id.server_refresh)
    ImageView refresh_button;

    public static ServerErrorFragment newInstance() {
        Bundle args = new Bundle();
        ServerErrorFragment mFragment = new ServerErrorFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_servererror,container,false);
        ButterKnife.bind((Object) this, view);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getActivity(),getActivity().getClass()));
            }
        });
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }
}
