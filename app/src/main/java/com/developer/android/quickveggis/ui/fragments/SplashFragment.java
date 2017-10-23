package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.activeandroid.ActiveAndroid;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.model.NotificationData;
import com.developer.android.quickveggis.db.NotificationRepo;
import com.developer.android.quickveggis.db.mycustom.Repo;
import com.developer.android.quickveggis.model.NotificationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SplashFragment extends Fragment {
    @Bind(R.id.imgBg)
    ImageView imgBg;
    @Bind({R.id.imgLogo})
    ImageView imgLogo;

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_splash, container, false);
        ButterKnife.bind(this, view);


        //  this.test();


        return view;
    }


    private void  test() {


        try {

            NotificationModel notification1 = new NotificationModel("1", "test1", "Test description 1", "");
            notification1.save();

            NotificationModel notification2 = new NotificationModel("2", "test2", "Test description 2", "");
            notification2.save();

            NotificationModel notification3 = new NotificationModel("3", "test3", "Test description 3", "");
            notification3.save();

            NotificationModel notification4 = new NotificationModel("4", "test4", "Test description 4", "");
            notification4.save();

        }
        finally {

        }

        //  List<Notification> notifications = Notification.getAll();

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext()).load(R.drawable.logo).fit().centerInside().into(this.imgLogo);
        Picasso.with(getContext()).load(R.drawable.splash).fit().centerCrop().into(this.imgBg);
    }
}
