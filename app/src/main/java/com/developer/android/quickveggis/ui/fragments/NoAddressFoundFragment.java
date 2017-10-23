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
import com.developer.android.quickveggis.ui.activity.OrderActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.squareup.picasso.Picasso;

public class NoAddressFoundFragment extends Fragment implements OrderActivity.TabChanger {
    @Bind(R.id.btnAddAddress)
    View btnAddAddress;
    @Bind(R.id.img)
    ImageView image;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.NoAddressFoundFragment.1 */
    class C03051 implements OnClickListener {
        C03051() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(NoAddressFoundFragment.this.getActivity(), (int) R.id.content, AddressOrderFragment.newInstance(), true);
        }
    }

    public static NoAddressFoundFragment newInstance() {
        Bundle args = new Bundle();
        NoAddressFoundFragment fragment = new NoAddressFoundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_address, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext()).load((int) R.drawable.ic_no_address).fit().centerInside().into(this.image);
        this.btnAddAddress.setOnClickListener(new C03051());
    }

    public int getTab() {
        return 0;
    }
}
