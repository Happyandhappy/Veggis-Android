package com.developer.android.quickveggis.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Bank;
import com.developer.android.quickveggis.ui.fragments.BankAccountListFragment;
import com.developer.android.quickveggis.ui.fragments.NoBankFoundFragment;
import com.developer.android.quickveggis.ui.utils.DialogUtils;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import java.util.ArrayList;

public class PayoutActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<Bank> banks;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PayoutActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_check);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.order);

        banks = new ArrayList<>();

        getBankAccountList();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    public void updateAccountFragment() {
        FragmentUtils.popBackStack(this);
        //if (BankRepo.getInstance().getCount() > 0) {
        if (banks.size() > 0) {
            FragmentUtils.changeFragment(this, R.id.content, BankAccountListFragment.newInstance(banks), false);
        } else {
            FragmentUtils.changeFragment(this, R.id.content, NoBankFoundFragment.newInstance(), false);
        }
    }


    public void getBankAccountList(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ServiceAPI.newInstance().getBanks(CustomerController.getInstance().getLoggedInCustomer().getCustomerId(), new ResponseCallback<ArrayList<Bank>>() {
            @Override
            public void onSuccess(ArrayList<Bank> data) {
                progressDialog.dismiss();
                banks = data;
                updateAccountFragment();
            }

            @Override
            public void onFailure(String error) {
                progressDialog.dismiss();
                DialogUtils.showAlertDialog(PayoutActivity.this, "Error while trying to get bank list");
            }
        });

//        OkHttpTask bankTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                PayoutActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        //loginDialog.cancel();
//                        try {
//                            if (jsonRes.getBoolean("success")) {
//                                Config.list_banks.clear();
//
//                                JSONArray bankJsonArray = jsonRes.getJSONArray("data");
//                                for (int i = 0; i < bankJsonArray.length(); i++) {
//                                    JSONObject bankJson = bankJsonArray.getJSONObject(i);
//                                    BankAccount bankAccount = new BankAccount();
//                                    bankAccount.setId(bankJson.getInt("bank_id"));
//                                    bankAccount.setCustomerId(bankJson.getInt("customer_id"));
//                                    bankAccount.setUserName(bankJson.getString("name"));
//                                    bankAccount.setBankName(bankJson.getString("branch"));
//                                    bankAccount.setAccountNum(bankJson.getString("accountnumber"));
//                                    bankAccount.setIfsc(bankJson.getString("ifsc"));
//
//                                    Config.list_banks.add(bankAccount);
//                                }
//                                //Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
//                            } else {
//                                DialogUtils.showAlertDialog(PayoutActivity.this, "Error while trying to get bank list");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        updateAccountFragment();
//                    }
//                });
//            }
//
//            @Override
//            public void errorConnect() {
//                PayoutActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DialogUtils.showAlertDialog(PayoutActivity.this, PayoutActivity.this.getResources().getString(R.string.connect_error));
//                        updateAccountFragment();
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                PayoutActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(PayoutActivity.this, "Error while trying to get bank list", Toast.LENGTH_LONG).show();
//                        updateAccountFragment();
//                    }
//                });
//            }
//        }, OkHttpTask.GET, null);
//
//        bankTask.execute(Config.API_GET_BANKACCOUNT + UserInfo.getInstance().getId() + "");
//        //bankTask.execute(Config.API_GET_BANKACCOUNT + "3");
    }
}
