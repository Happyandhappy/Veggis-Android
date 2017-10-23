package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.SupportListFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupportsActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SupportsActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.actvity_suppports);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, SupportListFragment.newInstance(), false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) { //???
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }
}
