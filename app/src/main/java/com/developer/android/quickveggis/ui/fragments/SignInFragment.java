package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.LoginUserData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.controller.SessionController;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.SignInActivity;
import com.developer.android.quickveggis.ui.activity.SignupActivity;
import com.developer.android.quickveggis.ui.activity.TutorialActivity;
import com.developer.android.quickveggis.ui.utils.DialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends Fragment {

    @Bind(R.id.txtCreatAccount)
    View txtCreateAccount;
    @Bind(R.id.btn_sign_in)
    RelativeLayout btnSignIn;
    @Bind(R.id.txt_email)
    EditText txt_email;
    @Bind(R.id.txt_pass)
    EditText txt_password;

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_signin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.txtCreatAccount)
    public void gotoSignUpScreen(){
        startActivity(SignupActivity.getStartIntent(getContext()));
        getActivity().finish();
    }

    @OnClick(R.id.btn_sign_in)
    public void signin(){
        if (txt_email.getText().toString().equalsIgnoreCase("")) {
            DialogUtils.showAlertDialog(getActivity(), getString(R.string.invalid_email));
            return;
        }

        if (txt_password.getText().toString().equalsIgnoreCase("")){
            DialogUtils.showAlertDialog(getActivity(), getString(R.string.invalid_password));
            return;
        }

        sendLoginRequest();

    }
    private void sendLoginRequest() {
        final ProgressDialog loginDialog = new ProgressDialog(getActivity());
        loginDialog.setMessage("Logging In...");
        loginDialog.setCancelable(false);
        loginDialog.show();

        ServiceAPI.newInstance().login(txt_email.getText().toString(), txt_password.getText().toString(), new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {
                loginDialog.cancel();
                CustomerController.getInstance().saveLoginCustomer(data);
                SessionController.getInstance().saveLoginInfo(new LoginUserData(txt_email.getText().toString(), txt_password.getText().toString()));
//                SessionController.getInstance().saveLoginSession(data.getSession());
                Toast.makeText(getActivity(), "Sign In Success", Toast.LENGTH_LONG).show();
                if (SessionController.getInstance().isExistShownSecondTutorial()) {
                    startActivity(MainActivity.getStartIntent(getActivity(), -1));
                }
                else {
                    startActivity(TutorialActivity.getStartIntent(getActivity()));
                    SessionController.getInstance().setSecondTutorial(true);
                }
                ((SignInActivity)getActivity()).logInSuccess = true;
                getActivity().finish();
            }

            @Override
            public void onFailure(String error) {
                loginDialog.cancel();
                Toast.makeText(getActivity(), "Sign In Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login() {
//        startActivity(TutorialActivity.getStartIntent(getContext()));
//        getActivity().finish();
    }

}
