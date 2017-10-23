package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.CartFragment;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements MainActivity.MenuController{
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CartActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.app_name);


        Bundle bundle = new Bundle();
        bundle.putInt("item", getIntent().getIntExtra("item", -1));
        FragmentUtils.changeFragment(this, R.id.content, CartFragment.newInstance(2), false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {//???
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getTask()){//back from task
            DialogUtils.showAlertUnLockDialog(this);
            App.setTask(false);
        }
    }

    @Override
    public int getMenuVisibility() {
        return 0;
    }
}
