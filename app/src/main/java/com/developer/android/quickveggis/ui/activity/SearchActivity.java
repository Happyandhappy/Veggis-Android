package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.SearchFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.btnClose)
    View btnClose;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_search );
        ButterKnife.bind((Activity) this);
        this.btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SearchFragment.newInstance(), false);
    }
}
