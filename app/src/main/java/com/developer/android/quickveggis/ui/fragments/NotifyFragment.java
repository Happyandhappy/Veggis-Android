package com.developer.android.quickveggis.ui.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.db.NotificationRepo;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotifyFragment extends Fragment {

    @Bind(R.id.checkSound)
    CheckBox checkBoxSound;
    @Bind(R.id.checkVibrate)
    CheckBox checkBoxVibrate;
    @Bind(R.id.checkLight)
    CheckBox checkBoxLight;

    public static NotifyFragment newInstance() {
        Bundle args = new Bundle();
        NotifyFragment fragment = new NotifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    final NotificationRepo repo = NotificationRepo.getInstance();

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.notify_title);
        ((ProfileActivity)getActivity()).btnSave.setVisibility(View.VISIBLE);

        this.init();

        ((ProfileActivity)getActivity()).btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Confirmed.", Toast.LENGTH_SHORT).show();

//                int note = 0;

                if (checkBoxSound.isChecked()) {
//                    note = Notification.DEFAULT_SOUND;
                    repo.setNotifySound(true, getContext());
                } else {
                    repo.setNotifySound(false, getContext());
                }

                if (checkBoxVibrate.isChecked()) {
//                    note |= Notification.DEFAULT_VIBRATE;
                    repo.setNotifyVibrate(true, getContext());
                } else {
                    repo.setNotifyVibrate(false, getContext());
                }

                if (checkBoxLight.isChecked()) {
//                    note |= Notification.DEFAULT_LIGHTS;
                }

                FragmentUtils.popBackStack(NotifyFragment.this.getActivity());
//
//                setNotificationManager(note);

//                FragmentUtils.popBackStack(NotifyFragment.this.getActivity());
            }
        });

//        NotificationManager manager = new NotificationManager(this);
    }

    private void init() {

        if (repo.getNotifySound(getContext())) {
            checkBoxSound.setChecked(true);
        } else {
            checkBoxSound.setChecked(false);
        }

        if (repo.getNotifyVibrate(getContext())) {
            checkBoxVibrate.setChecked(true);
        } else {
            checkBoxVibrate.setChecked(false);
        }
    }

    private void setNotificationManager(int notificationSet) {

        NotificationManager notificationManager = (NotificationManager)
                ((ProfileActivity)getActivity()).getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder((ProfileActivity)getActivity());

        builder.setDefaults(notificationSet);
        int pid = android.os.Process.myPid();

        notificationManager.notify(pid, builder.build());
    }
}
