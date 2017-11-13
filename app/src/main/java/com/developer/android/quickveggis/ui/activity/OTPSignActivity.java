package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.developer.android.quickveggis.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Happyandhappy on 11/13/2017.
 */

public class OTPSignActivity extends AppCompatActivity{
    @Bind(R.id.otp_firstLayout)
    RelativeLayout firstLayout;
    @Bind(R.id.otp_secondLayout)
    RelativeLayout secondLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpsign);
        ButterKnife.bind((Activity) this);
        setTitle("OTP Sign");
    }


}
