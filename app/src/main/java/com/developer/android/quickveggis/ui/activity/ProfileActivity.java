package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.ProfileFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class ProfileActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnSave)
    public TextView btnSave;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_check);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentUtils.changeFragment(this, R.id.content, ProfileFragment.newInstance(), false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) { //???
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }
}
