package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.db.AddressRepo;
import com.developer.android.quickveggis.model.Address;
import com.developer.android.quickveggis.ui.activity.OrderActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;
import java.util.ArrayList;
import java.util.List;

public class AddressListFragment extends Fragment implements OrderActivity.TabChanger {
    AddressAdapter adapter;
    @Bind(R.id.btnNext)
    View btnNext;
    List<Address> data;
    @Bind(R.id.fab)
    View fab;
    @Bind(R.id.rv)
    RecyclerView rv;
    int selected;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.AddressListFragment.1 */
    class C02781 implements OnClickListener {
        C02781() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(AddressListFragment.this.getActivity(), (int) R.id.content, TimeRangeFragment.newInstance(), true);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.AddressListFragment.2 */
    class C02792 implements OnClickListener {
        C02792() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(AddressListFragment.this.getActivity(), (int) R.id.content, AddressOrderFragment.newInstance(), true);
        }
    }

    public class AddressAdapter extends Adapter<AddressAdapter.Holder> {

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.AddressListFragment.AddressAdapter.1 */
        class C02801 implements OnClickListener {
            final /* synthetic */ Address val$address;

            C02801(Address address) {
                this.val$address = address;
            }

            public void onClick(View v) {
                AddressListFragment.this.onEditClick(this.val$address);
            }
        }

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.AddressListFragment.AddressAdapter.2 */
        class C02812 implements OnClickListener {
            final /* synthetic */ Address val$address;
            final /* synthetic */ int val$position;

            C02812(int i, Address address) {
                this.val$position = i;
                this.val$address = address;
            }

            public void onClick(View v) {
                AddressListFragment.this.onItemClicked(this.val$position, this.val$address);
            }
        }

        public class Holder extends ViewHolder {
            @Bind(R.id.blockSelection)
            View blockSelection;
            @Bind(R.id.btnEdit)
            View btnEdit;
            @Bind(R.id.txtContent)
            TextView txtSubitle;
            @Bind(R.id.txtHeader)
            TextView txtTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(AddressListFragment.this.getContext()).inflate(R.layout.item_order_address, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            Address address = (Address) AddressListFragment.this.data.get(position);
            holder.txtTitle.setText(address.getAddress1());
            if (AddressListFragment.this.selected == position) {
                holder.blockSelection.setVisibility(0);
            } else {
                holder.blockSelection.setVisibility(8);
            }
            holder.btnEdit.setOnClickListener(new C02801(address));
            holder.itemView.setOnClickListener(new C02812(position, address));
        }

        public int getItemCount() {
            return AddressListFragment.this.data.size();
        }
    }

    public AddressListFragment() {
        this.selected = -1;
        this.data = new ArrayList();
    }

    public static AddressListFragment newInstance() {
        Bundle args = new Bundle();
        AddressListFragment fragment = new AddressListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_list, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.data = AddressRepo.getInstance().getList();
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new AddressAdapter();
        this.rv.setAdapter(this.adapter);
        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(ContextCompat.getColor(getContext(), 17170445))).sizeResId(R.dimen.divider_survey_image)).build());
        this.btnNext.setOnClickListener(new C02781());
        this.fab.setOnClickListener(new C02792());
    }

    private void onItemClicked(int position, Address address) {
        if (position == this.selected) {
            this.selected = -1;
        } else {
            this.selected = position;
        }
        this.adapter.notifyDataSetChanged();
    }

    private void onEditClick(Address address) {
        FragmentUtils.changeFragment(getActivity(), (int) R.id.content, AddressOrderFragment.newInstance(address), true);
    }

    public int getTab() {
        return 0;
    }
}
