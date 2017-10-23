package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.SurveyImageChooserFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class SurveyActivity extends AppCompatActivity {
    @Bind(R.id.btnClose)
    View btnClose;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SurveyActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_survey);
        ButterKnife.bind((Activity) this);
        this.btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyImageChooserFragment.newInstance(), false);
    }
}
