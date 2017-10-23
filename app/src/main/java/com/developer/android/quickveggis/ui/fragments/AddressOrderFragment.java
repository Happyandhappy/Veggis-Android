package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.db.AddressRepo;
import com.developer.android.quickveggis.model.Address;
import com.developer.android.quickveggis.ui.activity.OrderActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressOrderFragment extends Fragment implements OrderActivity.TabChanger {
    Address address;
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.edAddress1)
    EditText edAddress1;
    @Bind(R.id.edAddress2)
    EditText edAddress2;
    @Bind(R.id.edMobile)
    EditText edMobile;
    @Bind(R.id.edPincode)
    EditText edPhoneCode;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.AddressOrderFragment.1 */
    class C02821 implements OnClickListener {
        C02821() {
        }

        public void onClick(View v) {
            AddressOrderFragment.this.saveAddress();
        }
    }

    public static AddressOrderFragment newInstance() {
        Bundle args = new Bundle();
        AddressOrderFragment fragment = new AddressOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddressOrderFragment newInstance(Address address) {
        Bundle args = new Bundle();
        args.putSerializable("address", address);
        AddressOrderFragment fragment = new AddressOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.address = (Address) getArguments().getSerializable("address");
        this.btnNext.setOnClickListener(new C02821());
        fillInfo();
    }

    private void fillInfo() {
        if (this.address != null) {
            this.edAddress1.setText(this.address.getAddress1());
            this.edAddress2.setText(this.address.getAddress2());
            this.edPhoneCode.setText(this.address.getPhoneCode());
            this.edMobile.setText(this.address.getPhoneNumber());
        }
    }

    private void saveAddress() {
        String address1 = this.edAddress1.getText().toString();
        String address2 = this.edAddress2.getText().toString();
        String phoneCode = this.edPhoneCode.getText().toString();
        String mobile = this.edMobile.getText().toString();
        boolean error = false;
        if (TextUtils.isEmpty(address1)) {
            error = true;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            error = true;
        }
        if (TextUtils.isEmpty(mobile)) {
            error = true;
        }
        if (!error) {
            if (this.address == null) {
                this.address = new Address();
            }
            this.address.setAddress1(address1);
            this.address.setAddress2(address2);
            this.address.setPhoneCode(phoneCode);
            this.address.setPhoneNumber(mobile);
            AddressRepo.getInstance().saveAddress(this.address);
            ((OrderActivity) getActivity()).updateAddressFragment();
        }
    }

    public int getTab() {
        return 0;
    }
}
