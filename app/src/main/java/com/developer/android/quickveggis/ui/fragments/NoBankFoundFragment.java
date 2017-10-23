package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

public class NoBankFoundFragment extends Fragment {
    @Bind(R.id.btnAddAccount)
    View btnAddAccount;
    @Bind(R.id.img)
    ImageView image;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.NoBankFoundFragment.1 */
    class C03061 implements OnClickListener {
        C03061() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(NoBankFoundFragment.this.getActivity(), (int) R.id.content, BankAccountFragment.newInstance(), false);
        }
    }

    public static NoBankFoundFragment newInstance() {
        Bundle args = new Bundle();
        NoBankFoundFragment fragment = new NoBankFoundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_bank, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.bank_accounts);
//        Picasso.with(getContext()).load((int) R.drawable.ic_no_bank).fit().centerInside().into(this.image);
        image.setImageResource(R.drawable.ic_no_bank);
        this.btnAddAccount.setOnClickListener(new C03061());
    }
}
