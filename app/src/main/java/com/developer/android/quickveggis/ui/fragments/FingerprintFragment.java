package com.developer.android.quickveggis.ui.fragments;
/*Created by happyandhappy on 11/3/2017*/
import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.ProfileMenu;
import com.developer.android.quickveggis.ui.adapter.FingerprintHandler;
import com.developer.android.quickveggis.ui.dialog.NotifyDialog;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.ui.fragments.TouchIDFragment.FINGERPRINT_ALLOW_STATE;

public class FingerprintFragment extends Fragment {
    private KeyStore keyStore;
    private static final String KEY_NAME="EDMTDev";
    private Cipher cipher;
    private TextView textView;
    private Object key;


    public static FingerprintFragment newInstance() {
        Bundle args = new Bundle();
        FingerprintFragment fragment = new FingerprintFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public FingerprintFragment(){
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.about);

        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        Boolean state=preferences.getBoolean(FINGERPRINT_ALLOW_STATE,false);
        if (!state) {
            Toast.makeText(getActivity(),"Please set Toggle App Lock in Security Settings", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        KeyguardManager keyguardManager = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getContext(),"Allow your Fingerprint permision",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!fingerprintManager.isHardwareDetected())
                Toast.makeText(getActivity(), getResources().getString(R.string.fingerprint_nodevice), Toast.LENGTH_SHORT).show();
            else {
                if (!fingerprintManager.hasEnrolledFingerprints()) Toast.makeText(getActivity(), getResources().getString(R.string.fingerprint_insert_register), Toast.LENGTH_SHORT).show();
                else {
                    if (!keyguardManager.isKeyguardSecure())
                        Toast.makeText(getActivity(), getResources().getString(R.string.fingerprint_lockscreen), Toast.LENGTH_SHORT).show();
                    else getKey();

                    if (cipherInit()){
                        FingerprintManager.CryptoObject cryptoObject=new FingerprintManager.CryptoObject(cipher);
                        FingerprintHandler helper=new FingerprintHandler(getActivity().getBaseContext());
                        helper.startAuthentication(fingerprintManager,cryptoObject);
                    }
                }
            }
        }
    }

    private boolean cipherInit() {
        try {
            cipher=Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
            try {
                keyStore.load(null);
                SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return true;
            }catch (IOException e1){
                e1.printStackTrace();
                return false;
            }catch (NoSuchAlgorithmException e1){
                e1.printStackTrace();
                return false;
            }catch (CertificateException e1){
                e1.printStackTrace();
                return false;
            }catch (UnrecoverableKeyException e1){
                e1.printStackTrace();
                return false;
            }catch (KeyStoreException e1){
                e1.printStackTrace();
                return false;
            }catch (InvalidKeyException e1){
                e1.printStackTrace();
                return false;
            }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getKey() {
        try {
            keyStore=KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {

            e.printStackTrace();
        }

        KeyGenerator keyGenerator=null;
        try {
            keyGenerator=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            );
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }catch (InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }


    }
}
