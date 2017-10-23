package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.Walk2PagerFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class TutorialActivity extends AppCompatActivity {
    public static Intent getStartIntent(Context context) {
        return new Intent(context, TutorialActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        ButterKnife.bind((Activity) this);
        FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, Walk2PagerFragment.newInstance(), false);
    }
}
