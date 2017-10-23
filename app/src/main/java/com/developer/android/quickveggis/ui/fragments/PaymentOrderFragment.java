package com.developer.android.quickveggis.ui.fragments;

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
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
//import com.quickveggies.quickveggies.model.PaymentType;
//import com.quickveggies.quickveggies.ui.activity.CheckActivity;
//import com.quickveggies.quickveggies.ui.activity.OrderActivity.TabChanger;
//import com.quickveggies.quickveggies.ui.utils.RecyclerItemClickListener;
//import com.quickveggies.quickveggies.ui.utils.RecyclerItemClickListener.OnItemClickListener;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.PaymentType;
import com.developer.android.quickveggis.ui.activity.CheckActivity;
import com.developer.android.quickveggis.ui.activity.OrderActivity;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;
import java.util.ArrayList;
import java.util.List;

public class PaymentOrderFragment extends Fragment implements OrderActivity.TabChanger {
    PaymentAdapter adapter;
    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.rv)
    RecyclerView rv;
    int selected;
    List<PaymentType> types;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.PaymentOrderFragment.2 */
    class C03072 implements OnClickListener {
        C03072() {
        }

        public void onClick(View v) {
            PaymentOrderFragment.this.startActivity(CheckActivity.getStartIntent(PaymentOrderFragment.this.getContext()));
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.PaymentOrderFragment.1 */
    class C05671 implements RecyclerItemClickListener.OnItemClickListener {
        C05671() {
        }

        public void onItemClick(View view, int position) {
            PaymentOrderFragment.this.selected = position;
            PaymentOrderFragment.this.adapter.notifyDataSetChanged();
        }
    }

    public class PaymentAdapter extends Adapter<PaymentAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.blockSelection)
            View blockSelection;
            @Bind(R.id.img)
            ImageView image;
            @Bind(R.id.imgCards)
            ImageView imgCards;
            @Bind(R.id.txtSubtitle)
            TextView txtSubitle;
            @Bind(R.id.txtTitle)
            TextView txtTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(PaymentOrderFragment.this.getContext()).inflate(R.layout.item_order_payment, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            PaymentType type = (PaymentType) PaymentOrderFragment.this.types.get(position);
            Picasso.with(PaymentOrderFragment.this.getContext()).load(type.getLogo()).into(holder.image);
            holder.txtTitle.setText(type.getTitle());
            holder.txtSubitle.setText(type.getSubtitle());
            if (type.getType() == 2) {
                holder.imgCards.setVisibility(0);
            } else {
                holder.imgCards.setVisibility(8);
            }
            if (PaymentOrderFragment.this.selected == position) {
                holder.blockSelection.setVisibility(0);
            } else {
                holder.blockSelection.setVisibility(8);
            }
        }

        public int getItemCount() {
            return PaymentOrderFragment.this.types.size();
        }
    }

    public PaymentOrderFragment() {
        this.selected = 0;
        this.types = new ArrayList();
    }

    public static PaymentOrderFragment newInstance() {
        Bundle args = new Bundle();
        PaymentOrderFragment fragment = new PaymentOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_survey_image)).build());
        this.adapter = new PaymentAdapter();
        this.rv.setAdapter(this.adapter);
        this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05671()));
        this.btnNext.setOnClickListener(new C03072());
        fillTypes();
    }

    private void fillTypes() {
        this.types.clear();
        this.types.add(new PaymentType(1, R.string.cash_delivery, "Account Email:\nJohnDoe@gmail.com", R.drawable.ic_pay_cash));
        this.types.add(new PaymentType(2, R.string.card_delivery, "Visa, Mastercard, Amex", R.drawable.ic_pay_card));
    }

    public int getTab() {
        return 2;
    }
}
