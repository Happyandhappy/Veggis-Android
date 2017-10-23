package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.ProductFragment;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class ProductActivity extends AppCompatActivity implements MainActivity.MenuController {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static ProductActivity _inst;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_product);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_main_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
//                intent.putExtra("mode", -1);
//                startActivity(intent);
//                finish();
                setResult(513);
                onBackPressed();
            }
        });
        setTitle(R.string.app_name);


        Bundle bundle = new Bundle();
        bundle.putInt("item", getIntent().getIntExtra("item", -1));

        FragmentUtils.changeFragment((FragmentActivity) this, R.id.content, ProductFragment.newInstance(bundle), false);

        _inst = this;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product, menu);
        menu.findItem(R.id.action_share).setEnabled(false);
        menu.findItem(R.id.action_cart1).setEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getTask()) {//back from task
            DialogUtils.showAlertUnLockDialog(this);
            App.setTask(false);
        }
    }

    @Override
    public int getMenuVisibility() {
        return 0;
    }
}
