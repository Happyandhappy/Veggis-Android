package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.WalkPagerFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class Walk1Activity extends AppCompatActivity {
    public static Intent getStartIntent(Context context) {
        return new Intent(context, Walk1Activity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        ButterKnife.bind(this);
        FragmentUtils.changeFragment(this, R.id.content, WalkPagerFragment.newInstance(), false);
    }
}
