package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.UnlockedFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.ButterKnife;

public class UnlockedActivity extends AppCompatActivity {
    public static Intent getStartIntent(Context context) {
        return new Intent(context, UnlockedActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_frame);
        ButterKnife.bind((Activity) this);
        FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, UnlockedFragment.newInstance(), false);
    }
}
