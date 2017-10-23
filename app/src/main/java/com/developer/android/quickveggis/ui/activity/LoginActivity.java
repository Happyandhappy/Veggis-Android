package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.fragments.LoginFragment;


/*
    // Test Login Info

    User name:      Panchal.aniket1@gmail.com
    Password :      123456
 */



public class LoginActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        loginFragment = LoginFragment.newInstance();
        FragmentUtils.changeFragment(this, R.id.content, loginFragment, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (loginFragment != null){
            loginFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
