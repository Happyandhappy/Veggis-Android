package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.LoginUserData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.controller.SessionController;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.SignupActivity;
import com.developer.android.quickveggis.ui.activity.TutorialActivity;
import com.developer.android.quickveggis.ui.utils.DialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupFragment extends Fragment {

    @Bind(R.id.txt_firstName)
    EditText txt_firstname;
    @Bind(R.id.txt_lastName)
    EditText txt_lastname;
    @Bind(R.id.txt_email)
    EditText txt_email;
    @Bind(R.id.txt_userName)
    EditText txt_username;
    @Bind(R.id.txt_password)
    EditText txt_password;
    @Bind(R.id.txt_agree)
    TextView txtAgree;

    boolean isAgree = false;
    ProgressDialog pDialog;

    public static SignupFragment newInstance() {
        Bundle args = new Bundle();
        SignupFragment fragment = new SignupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void login() {
//        startActivity(TutorialActivity.getStartIntent(getContext()));
//        getActivity().finish();
    }

    @OnClick(R.id.txt_agree)
    public void onClickAgree(){
        isAgree = true;
    }

    @OnClick(R.id.txt_agree_layout)
    public void onLayoutClickAgree(){
        isAgree = true;
    }

    @OnClick(R.id.btn_signup)
    public void signup(){

        String firstName = txt_firstname.getText().toString();
        String lastName  = txt_lastname.getText().toString();
        String userName  = txt_username.getText().toString();
        String email     = txt_email.getText().toString();
        String password  = txt_password.getText().toString();

        if (firstName.equalsIgnoreCase("")){
            DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.invalid_firstname));
            return;
        }
        else if (lastName.equalsIgnoreCase("")){
            DialogUtils.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.invalid_lastname));
            return;
        }
        else if (userName.equalsIgnoreCase("")){
            DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.invalid_username));
            return;
        }
        else if (email.equalsIgnoreCase("")){
            DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.invalid_email));
            return;
        }
        else if (password.equalsIgnoreCase("")){
            DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.invalid_password));
            return;
        }else if (!isAgree && false){
            DialogUtils.showAlertDialog(getActivity(), "Do not you agree to terms and conditions?");
            return;
        }
        else {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Signing up...");
            pDialog.setCancelable(false);
            pDialog.show();

            ServiceAPI.newInstance().registerUser("97", email, firstName, lastName, password, password, "1", new ResponseCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    signin();
                }

                @Override
                public void onFailure(String error) {
//                    Toast.makeText(getActivity(), "Sign Up Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            });
        }
    }

    void signin() {
        ServiceAPI.newInstance().login(txt_email.getText().toString(), txt_password.getText().toString(), new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {
                pDialog.cancel();
                CustomerController.getInstance().saveLoginCustomer(data);
                SessionController.getInstance().saveLoginInfo(new LoginUserData(txt_email.getText().toString(), txt_password.getText().toString()));
//                SessionController.getInstance().saveLoginSession(data.getSession());
//                Toast.makeText(getActivity(), "Sign In Success", Toast.LENGTH_LONG).show();
                if (SessionController.getInstance().isExistShownSecondTutorial()) {
                    startActivity(MainActivity.getStartIntent(getActivity()));
                } else {
                    startActivity(TutorialActivity.getStartIntent(getActivity()));
                    SessionController.getInstance().setSecondTutorial(true);
                }
                ((SignupActivity)getActivity()).logInSuccess = true;
                getActivity().finish();
            }

            @Override
            public void onFailure(String error) {
                pDialog.cancel();
                Toast.makeText(getActivity(), "Sign In Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUserAccountDetails(){
        ServiceAPI.newInstance().getAccountDetails(new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {
                pDialog.dismiss();
                CustomerController.getInstance().saveLoginCustomer(data);
                goToNextStep();
            }

            @Override
            public void onFailure(String error) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    void goToNextStep(){

        Toast.makeText(getActivity(), "Sign In Success", Toast.LENGTH_LONG).show();
        if (SessionController.getInstance().isExistShownSecondTutorial()) {
            startActivity(MainActivity.getStartIntent(getActivity()));
        } else {
            startActivity(TutorialActivity.getStartIntent(getActivity()));
            SessionController.getInstance().setSecondTutorial(true);
        }
        getActivity().finish();
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };
}
