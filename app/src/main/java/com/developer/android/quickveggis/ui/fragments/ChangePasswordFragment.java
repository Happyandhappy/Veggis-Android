package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Happyandhappy on 10/26/2017.
 */


public class ChangePasswordFragment extends Fragment {
    String current_passStr;
    String new_passStr;
    String reenter_passStr;

    @Bind(R.id.edittext_changepassword_password_current)
    TextInputLayout current_password;

    @Bind(R.id.edittext_changepassword_enter_newpassword)
    TextInputLayout enter_newpssword;

    @Bind(R.id.edittext_changepassword_reenter_newpassword)
    TextInputLayout reenter_newpassword;


    public static ChangePasswordFragment newInstance(){
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.changepassword);
        ((ProfileActivity)getActivity()).btnSave.setVisibility(View.VISIBLE);

        ((ProfileActivity)getActivity()).btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_passStr = current_password.getEditText().toString();
                new_passStr=enter_newpssword.getEditText().getText().toString();
                reenter_passStr=reenter_newpassword.getEditText().getText().toString();
                //compare the current password with input current_password
                /*if (current_passStr.equals()){
                    Toast.makeText(getContext(),"Current Password is not correct, Please try again",Toast.LENGTH_SHORT).show();
                    return;
                }*/

                //check the length of the new password is more than 4

                if (new_passStr.length()<4 || new_passStr.length()>20){
                    DialogUtils.showAlertDialog(getActivity(), getString(R.string.password_length_error));
                    return;
                }
                //compare the enter_password with reenter_password
                if (!new_passStr.equalsIgnoreCase(reenter_passStr)){
                    DialogUtils.showAlertDialog(getActivity(), getString(R.string.password_reenter_error));
                    return;
                }

                sendLoginRequest();

               // Toast.makeText(getActivity(), "Confirmed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendLoginRequest() {
        final ProgressDialog loginDialog = new ProgressDialog(getActivity());
        loginDialog.setMessage("Changing Password...");
        loginDialog.setCancelable(false);
        loginDialog.show();

        //ServiceAPI.newInstance().changePassword();
    }
}
