package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.fragments.SignInFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

/*     Login info

//      Panchal.aniket1@gmail.com
//      Password : 123456

 */


public class SignInActivity extends AppCompatActivity {

    public boolean logInSuccess = false;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        FragmentUtils.changeFragment(this, R.id.content, SignInFragment.newInstance(), false);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!logInSuccess) {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

    }
}
