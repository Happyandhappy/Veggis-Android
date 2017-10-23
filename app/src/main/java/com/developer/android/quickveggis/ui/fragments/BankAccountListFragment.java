package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.BanksDataChanged;
import com.developer.android.quickveggis.api.model.SaveCashOutData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Bank;
import com.developer.android.quickveggis.model.BankAccount;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.quickveggies.quickveggies.db.BankRepo;

public class BankAccountListFragment extends Fragment {
    BankAdapter adapter;
    @Bind(R.id.btnNext)
    View btnNext;

    List<BankAccount> data;
    @Bind(R.id.fab)
    View fab;

    @Bind(R.id.rv)
    RecyclerView rv;

    int selected;
    Bank selectedBank;

    ArrayList<Bank> banks;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.BankAccountListFragment.1
    * when click "Next" button */
    class C02841 implements OnClickListener {
        C02841() {
        }

        public void onClick(View v) {
            if (selectedBank == null)
            {
                Toast.makeText(getContext(), "Please select bank account", Toast.LENGTH_SHORT).show();
                return;
            }
            sendCashoutRequest();
        }
    }

    private void sendCashoutRequest() {

        if (CustomerController.getInstance().getLoggedInCustomer().getWallet().getWalletTotal() == null){
            Toast.makeText(getContext(), "There is no cash out", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        ServiceAPI.newInstance().saveCashOut(new SaveCashOutData(CustomerController.getInstance().getLoggedInCustomer().getCustomerId(), selectedBank.getBankId(),
                CustomerController.getInstance().getLoggedInCustomer().getWallet().getWalletTotal()), new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Payment Process Success", Toast.LENGTH_LONG).show();
                FragmentUtils.changeFragment(BankAccountListFragment.this.getActivity(), R.id.content, SuccessFragment.newInstance(), false);
            }

            @Override
            public void onFailure(String error) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }
        });

        //List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//        JSONObject params = new JSONObject();
//        try {
//            if (selectedBank != null){
//                params.accumulate("customer_id", selectedBank.getCustomerId());
//                params.accumulate("bank_id", selectedBank.getId());
//                //for test
//                params.accumulate("amount", UserInfo.getInstance().getWalletAmount());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        OkHttpTask cashTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        pDialog.cancel();
//                        try {
//                            if (jsonRes.has("success")) {
//                                Toast.makeText(getActivity(), "Payment Process Success", Toast.LENGTH_LONG).show();
//                                FragmentUtils.changeFragment(BankAccountListFragment.this.getActivity(), (int) R.id.content, SuccessFragment.newInstance(), false);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Payment Process Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (Exception e) {
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
//                        pDialog.cancel();
//                        DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.connect_error));
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pDialog.cancel();
//                        Toast.makeText(getActivity(), "Error while trying to cash out", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }, OkHttpTask.POST, params);
//
//        cashTask.execute(Config.API_SAVE_CASHOUT);
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.BankAccountListFragment.2 */
    class C02852 implements OnClickListener {
        C02852() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(BankAccountListFragment.this.getActivity(), R.id.content, BankAccountFragment.newInstance(), true);
        }
    }

    public class BankAdapter extends Adapter<BankAdapter.Holder> {

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.BankAccountListFragment.BankAdapter.1 */
        class C02861 implements OnClickListener {
            //final /* synthetic */ BankAccount val$account;
            int bankIndex;

            C02861(int position) {
                //this.val$account = bankAccount;
                this.bankIndex   = position;
            }

            public void onClick(View v) {
                BankAccountListFragment.this.onEditClick(this.bankIndex);
            }
        }

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.BankAccountListFragment.BankAdapter.2 */
        class C02872 implements OnClickListener {
            final /* synthetic */ Bank val$account;
            final /* synthetic */ int val$position;

            C02872(int i, Bank bankAccount) {
                this.val$position = i;
                this.val$account = bankAccount;
            }

            public void onClick(View v) {
                BankAccountListFragment.this.onItemClicked(this.val$position, this.val$account);
            }
        }

        public class Holder extends ViewHolder {
            @Bind(R.id.blockSelection)
            View blockSelection;

            @Bind(R.id.btnEdit)
            View btnEdit;

            @Bind(R.id.txtHeader)
            TextView txtHeader;

            @Bind(R.id.txtContent)
            TextView txtTitle;

            @Bind(R.id.txtBankName)
            TextView txtBankName;

            @Bind(R.id.txtBranch)
            TextView txtBranch;

            @Bind(R.id.txtAccountNumber)
            TextView txtAccountNumber;

            @Bind(R.id.txtIfsc)
            TextView txtIfsc;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(BankAccountListFragment.this.getContext()).inflate(R.layout.item_bank_account1, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            if (banks.size() <= 0){
                return;
            }
            //BankAccount account = (BankAccount) BankAccountListFragment.this.data.get(position);
            Bank bank = banks.get(position);
            if (bank != null){
                holder.txtHeader.setText(BankAccountListFragment.this.getString(R.string.bank_account, Integer.valueOf(position + 1)));
//                holder.txtTitle.setText(getBankString(bank));
                holder.txtBankName.setText(getBankName(bank));
                holder.txtBranch.setText(getBranch(bank));
                holder.txtAccountNumber.setText(getAccountNumber(bank));
                holder.txtIfsc.setText("IFSC Code : " + getIfsc(bank));
                if (BankAccountListFragment.this.selected == position) {
                    holder.blockSelection.setVisibility(View.VISIBLE);
                } else {
                    holder.blockSelection.setVisibility(View.GONE);
                }
                holder.btnEdit.setOnClickListener(new C02861(position));
                holder.itemView.setOnClickListener(new C02872(position, bank));
            }
        }

        private String getBankString(Bank account) {
            return BankAccountListFragment.this.getString(R.string.bank_item, account.getName(), account.getBranch(), account.getAccountnumber(), account.getIfsc());
        }

        private String getBankName(Bank account) {
            return account.getName();
        }

        private String getBranch(Bank account) {
            return account.getBranch();
        }

        private String getAccountNumber(Bank account) {
            return account.getAccountnumber();
        }

        private String getIfsc(Bank account) {
            return account.getIfsc();
        }

        public int getItemCount() {
            //return BankAccountListFragment.this.data.size();
            return banks.size();
        }
    }

    public BankAccountListFragment() {
        this.selected = -1;
        this.data = new ArrayList();
    }

    public static BankAccountListFragment newInstance(ArrayList<Bank> banks) {
        Bundle args = new Bundle();
        BankAccountListFragment fragment = new BankAccountListFragment();
        fragment.setArguments(args);
        fragment.banks = banks;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getEventBus().register(this);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.bank_accounts);

//        this.rv.addItemDecoration(((VerticalDividerItemDecoration.Builder) ((HorizontalDividerItemDecoration.Builder) new Builder(getActivity()).color(ContextCompat.getColor(getContext(), 17170445))).sizeResId(R.dimen.divider_survey_image)).build());
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new BankAdapter();
        this.rv.setAdapter(this.adapter);

        this.btnNext.setOnClickListener(new C02841());
        this.fab.setOnClickListener(new C02852());
        //getBankAccountList();
    }

    private void onItemClicked(int position, Bank account) {

        selectedBank = account;

        if (position == this.selected) {
            this.selected = -1;
        } else {
            this.selected = position;
        }
        this.adapter.notifyDataSetChanged();
    }

    private void onEditClick(int bankIdx) {
        FragmentUtils.changeFragment(getActivity(), R.id.content, BankAccountFragment.newInstance(banks.get(bankIdx)), true);
    }

    private void updateRecycleView(){
        this.adapter.notifyDataSetChanged();
        //this.adapter = new BankAdapter();
        //this.rv.setAdapter(this.adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().getEventBus().unregister(this);
    }

    @Subscribe
    public void onMessage(BanksDataChanged event){
        banks = event.getBanks();
    }
}