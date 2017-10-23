package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.TrackingFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.squareup.picasso.Picasso;

public class TrackingActivity extends AppCompatActivity {
    @Bind(R.id.headerImage)
    ImageView imgHeader;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TrackingActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(this).load(R.drawable.ic_track_header).fit().centerCrop().into(this.imgHeader);
        FragmentUtils.changeFragment(this, R.id.content, TrackingFragment.newInstance(), false);
        setTitle(R.string.tracking);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
