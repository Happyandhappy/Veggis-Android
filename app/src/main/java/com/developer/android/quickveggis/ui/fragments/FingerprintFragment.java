package com.developer.android.quickveggis.ui.fragments;
/*Created by happyandhappy on 11/3/2017*/
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;

import com.developer.android.quickveggis.config.Config;
import com.felipecsl.gifimageview.library.GifImageView;
import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;


import static com.developer.android.quickveggis.ui.fragments.TouchIDFragment.FINGERPRINT_ALLOW_STATE;

public class FingerprintFragment extends Fragment implements FingerPrintAuthCallback {
    @Bind(R.id.finger_auth_start)
    LinearLayout auth_start;

    @Bind(R.id.finger_auth_success)
    LinearLayout auth_success;

    @Bind(R.id.auth_state_txt)
    TextView authStatetxt;

    @Bind(R.id.imageFailure)
    ImageView imageFailure;

    GifImageView imageFinger;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    private final int FINGER_START=1;
    private final int FINGER_SUCCESS=2;
    private final int FINGER_NOTRECOG=3;
    private final int FINGER_NOTINITIAL=4;

    public static FingerprintFragment newInstance() {
        Bundle args = new Bundle();
        FingerprintFragment fragment = new FingerprintFragment();
        fragment.setArguments(args);
        return fragment;
    }
    void setState(int state){
         switch (state) {
             case FINGER_START:
                 recogResult(false);
                 imageFinger.setVisibility(View.VISIBLE);
                 imageFailure.setVisibility(View.GONE);
                 auth_start.setVisibility(View.VISIBLE);
                 auth_success.setVisibility(View.GONE);
                 authStatetxt.setText(getResources().getString(R.string.fingerprint_identity));
                 recogResult(false);
                 break;
             case FINGER_SUCCESS:
                 imageFinger.setVisibility(View.VISIBLE);
                 imageFailure.setVisibility(View.GONE);
                 auth_start.setVisibility(View.GONE);
                 auth_success.setVisibility(View.VISIBLE);
                 authStatetxt.setText(getResources().getString(R.string.fingerprint_identity));
                 recogResult(true);
                 break;
             case FINGER_NOTRECOG:
                 imageFinger.setVisibility(View.GONE);
                 imageFailure.setVisibility(View.VISIBLE);
                 auth_start.setVisibility(View.VISIBLE);
                 auth_success.setVisibility(View.GONE);
                 authStatetxt.setText(getResources().getString(R.string.fingerprint_not_recognize));
                 recogResult(false);
                 break;
             case FINGER_NOTINITIAL:
                 imageFinger.setVisibility(View.GONE);
                 imageFailure.setVisibility(View.VISIBLE);
                 auth_start.setVisibility(View.VISIBLE);
                 auth_success.setVisibility(View.GONE);
                 recogResult(false);
                 authStatetxt.setText(getResources().getString(R.string.fingerprint_not_recognize));
                 break;
         }
    }

    void recogResult(Boolean value){
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FINGERPRINT_ALLOW_STATE,value);
        editor.commit();
    }

    protected void startAnim(final int state){
        imageFinger.clearAnimation();
        imageFinger.startAnimation();
        setState(FINGER_START);
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageFinger.stopAnimation();
                    setState(state);
                }
            }, 2500);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        imageFinger = (com.felipecsl.gifimageview.library.GifImageView)view.findViewById(R.id.imageFinger);
        imageFinger.setBackgroundColor(Color.TRANSPARENT);
        try {
            InputStream is = getActivity().getAssets().open("fingerprint.gif");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            imageFinger.setBytes(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ButterKnife.bind((Object) this, view);
        setState(FINGER_START);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.security_settings);
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(getContext(), this);
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(getContext(),getResources().getString(R.string.fingerprint_nodevice),Toast.LENGTH_SHORT);
        getActivity().finish();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        authStatetxt.setText(getResources().getString(R.string.fingerprint_nofinger_registered));
    }

    @Override
    public void onBelowMarshmallow() {
        authStatetxt.setText(getResources().getString(R.string.fingerprint_old_device));
        getActivity().finish();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        startAnim(FINGER_SUCCESS);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                startAnim(FINGER_NOTRECOG);
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //Fix crash error
//                Toast.makeText(getActivity(),getResources().getString(R.string.fingerprint_not_initialize),Toast.LENGTH_SHORT);
//                authStatetxt.setText(getResources().getString(R.string.fingerprint_not_initialize));
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                authStatetxt.setText(errorMessage);
//                Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mFingerPrintAuthHelper.startAuth();
    }
    @Override
    public void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

}
