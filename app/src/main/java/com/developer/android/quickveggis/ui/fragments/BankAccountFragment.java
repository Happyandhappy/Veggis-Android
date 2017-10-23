package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.BanksDataChanged;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Bank;
import com.developer.android.quickveggis.model.BankAccount;
import com.developer.android.quickveggis.ui.utils.DialogUtils;

import java.util.ArrayList;
//import com.quickveggies.quickveggies.db.BankRepo;

public class BankAccountFragment extends Fragment {
    BankAccount account;
    int bankIndex;

    @Bind(R.id.btnSave)
    View btnSave;

    @Bind(R.id.edit_user_name)
    EditText edit_userName;

    @Bind(R.id.edit_bank_name)
    EditText edit_bankName;

    @Bind(R.id.edit_account_number)
    EditText edit_accountNumber;

    @Bind(R.id.edit_ifsc_code)
    EditText edit_ifsc;

    Bank bank;

//    @Bind(R.id.edZip)
//    EditText edZip;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.BankAccountFragment.1 */
    class C02831 implements OnClickListener {
        C02831() {
        }

        public void onClick(View v) {
            BankAccountFragment.this.saveAddress();
        }
    }

    public static BankAccountFragment newInstance() {
        Bundle args = new Bundle();
        BankAccountFragment fragment = new BankAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BankAccountFragment newInstance(Bank bank) {
        BankAccountFragment fragment = new BankAccountFragment();
        fragment.bank = bank;
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        getActivity().setTitle(R.string.bank_fill_credentials);
        this.btnSave.setOnClickListener(new C02831());
        fillInfo();
    }

    private void fillInfo() {

        if (bank != null){
            this.edit_userName.setText(bank.getName());
            this.edit_bankName.setText(bank.getBranch());
            this.edit_ifsc.setText(bank.getIfsc());
            this.edit_accountNumber.setText(bank.getAccountnumber());
        }
    }

    private void saveAddress() {
        String name          = this.edit_userName.getText().toString();
        String branch        = this.edit_bankName.getText().toString();
        String accountNumber = this.edit_accountNumber.getText().toString();
        String ifsc          = this.edit_ifsc.getText().toString();

        boolean error = false;
        if (TextUtils.isEmpty(name)) {
            error = true;
        }
        if (TextUtils.isEmpty(branch)) {
            error = true;
        }
        if (TextUtils.isEmpty(accountNumber)) {
            error = true;
        }
        if (TextUtils.isEmpty(ifsc)) {
            error = true;
        }

        if (!error) {
            if (bank != null) {
                bank.setName(name);
                bank.setBranch(branch);
                bank.setAccountnumber(accountNumber);
                bank.setIfsc(ifsc);

                addBankAccount(bank);
            }
            else {
                Bank newAccount = new Bank();
                newAccount.setCustomerId(CustomerController.getInstance().getLoggedInCustomer().getCustomerId());
                newAccount.setName(name);
                newAccount.setBranch(branch);
                newAccount.setAccountnumber(accountNumber);
                newAccount.setIfsc(ifsc);

                addBankAccount(newAccount);
            }
        }
    }

    private void addBankAccount(Bank bankAccount) {

        final ProgressDialog loginDialog = new ProgressDialog(getActivity());
        loginDialog.setMessage("Please wait...");
        loginDialog.setCancelable(false);
        loginDialog.show();

        if (bankAccount.getBankId() == null) {
            ServiceAPI.newInstance().addBank(bankAccount, new ResponseCallback<ArrayList<Bank>>() {
                @Override
                public void onSuccess(ArrayList<Bank> data) {
                    loginDialog.dismiss();
                    App.getInstance().getEventBus().post(new BanksDataChanged(data));
                    getActivity().getSupportFragmentManager().popBackStack();
                }

                @Override
                public void onFailure(String error) {
                    loginDialog.dismiss();
                    DialogUtils.showAlertDialog(getActivity(), "Error while trying to add bank account, please try again later");
                }
            });
        } else {
            ServiceAPI.newInstance().updateBank(bankAccount, new ResponseCallback<ArrayList<Bank>>() {
                @Override
                public void onSuccess(ArrayList<Bank> data) {
                    loginDialog.dismiss();
                    App.getInstance().getEventBus().post(new BanksDataChanged(data));
                    getActivity().getSupportFragmentManager().popBackStack();
                }

                @Override
                public void onFailure(String error) {
                    loginDialog.dismiss();
                    DialogUtils.showAlertDialog(getActivity(), "Error while trying to update bank account, please try again later");
                }
            });
        }
    }


//        String requestUrl = "";
//        if (this.bankIndex != -1)
//            requestUrl = Config.API_UPDATE_BANK;
//        else
//            requestUrl = Config.API_ADD_NEW_ACCOUNT;
//
//        //List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//        JSONObject params = new JSONObject();
//        try {
//            params.accumulate("customer_id", bankAccount.getCustomerId());
//            //params.accumulate("customer_id", 3);
//            params.accumulate("name", bankAccount.getUserName());
//            params.accumulate("branch", bankAccount.getBankName());
//            params.accumulate("ifsc", bankAccount.getIfsc());
//            params.accumulate("accountnumber", bankAccount.getAccountNum());
//
//            if (this.bankIndex != -1)
//                params.accumulate("bank_id", bankAccount.getId());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        OkHttpTask addTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        loginDialog.cancel();
//                        try {
//                            if (jsonRes.getBoolean("success")) {
//                                Config.list_banks.clear();
//
//                                Toast.makeText(getActivity(), "Add Success", Toast.LENGTH_LONG).show();
//                                JSONArray jsonBankArray = jsonRes.getJSONArray("data");
//                                for (int i = 0; i < jsonBankArray.length(); i++){
//                                    JSONObject jsonBank = jsonBankArray.getJSONObject(i);
//                                    BankAccount bank = new BankAccount();
//                                    bank.setId(jsonBank.getInt("bank_id"));
//                                    bank.setId(jsonBank.getInt("customer_id"));
//                                    bank.setUserName(jsonBank.getString("name"));
//                                    bank.setBankName(jsonBank.getString("branch"));
//                                    bank.setIfsc(jsonBank.getString("ifsc"));
//                                    bank.setAccountNum(jsonBank.getString("accountnumber"));
//
//                                    Config.list_banks.add(bank);
//                                }
//                                if (Config.list_banks.size() > 0)
//                                    ((PayoutActivity) getActivity()).updateAccountFragment();
//                                else
//                                    DialogUtils.showAlertDialog(getActivity(), "Not found customer id.");
//
//                            } else {
//                                DialogUtils.showAlertDialog(getActivity(), "Error while trying to add bank account, please try again later");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void errorConnect() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        loginDialog.cancel();
//                        DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.connect_error));
//                        //((PayoutActivity) getActivity()).updateAccountFragment();
//
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        loginDialog.cancel();
//                        DialogUtils.showAlertDialog(getActivity(), "Error while trying to add bank account, please try again later");
//                        //((PayoutActivity) getActivity()).updateAccountFragment();
//                    }
//                });
//            }
//        }, OkHttpTask.POST, params);
//
//        addTask.execute(requestUrl);
//    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //login();
        }
    };
}