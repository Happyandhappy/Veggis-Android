package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.developer.android.quickveggis.R;

public class ConfirmActivity extends Activity {

    Button btnYes;
    Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.confirm_dlg_back)));

        btnYes = (Button)(Button)findViewById(R.id.btnYes);
        btnNo = (Button)findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
    }
}
