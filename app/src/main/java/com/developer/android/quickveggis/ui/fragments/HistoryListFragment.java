package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.model.OrderHistory;
import com.developer.android.quickveggis.model.OrderHistorySection;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.adapter.HistoryAdapter;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.App.historyChanged;
import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;

public class HistoryListFragment extends Fragment implements MainActivity.MenuController, HistoryAdapter.HistoryItemClickListener{
    HistoryAdapter adapter;
    ArrayList<OrderHistorySection> orderHistorySections;
    StickyRecyclerHeadersDecoration decoration;

    public static final int ORDER_PENDING = 1;
    public static final int ORDER_COMPLETED = 2;
    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.emptyLayout)
    ScrollView emptyLayout;

    @Bind(R.id.redeemHistorySymbolLayout)
    LinearLayout redeemHistorySymbolLayout;
    int type;

    public static HistoryListFragment mFragment;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.HistoryListFragment.1 */
    class C05651 implements RecyclerItemClickListener.OnItemClickListener {
        C05651() {
        }

        public void onItemClick(View view, int position) {
//            FragmentUtils.changeFragment(HistoryListFragment.this.getActivity(), R.id.content, OrderHistoryDetailsFragment.newInstance(HistoryListFragment.this.type), true);
        }
    }

    public HistoryListFragment() {
        this.orderHistorySections = new ArrayList();
    }

    public static HistoryListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        mFragment = new HistoryListFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.history);
        if (historyChanged) {
            getCartOrderHistorySections();
        } else {
            if (orderHistorySections.size() > 0) {
                emptyLayout.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
            } else {
                redeemHistorySymbolLayout.setVisibility(View.VISIBLE);
            }
            setRvAdapter();
        }
//        rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(R.color.divider))).sizeResId(R.dimen.divider_history)).build());
    }

    void getCartOrderHistorySections() {

        ServiceAPI.newInstance().getOrdersByCustomerId(new ResponseCallback<ArrayList<OrderHistory>>() {
            @Override
            public void onSuccess(ArrayList<OrderHistory> orderHistorySections) {
                if (orderHistorySections.size() > 0) {
                    emptyLayout.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    startFadeInAnim(rv);
                } else {
                    redeemHistorySymbolLayout.setVisibility(View.VISIBLE);
                }
                createAdapterOrderHistorySections(orderHistorySections);
                setRvAdapter();
                historyChanged = false;
            }

            @Override
            public void onFailure(String error) {
                redeemHistorySymbolLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                historyChanged = false;
            }
        });

    }

    void createAdapterOrderHistorySections(ArrayList<OrderHistory> data){

        boolean pendingSectionAdded = false;
        boolean completeSectionAdded = false;

        for (int i = 0; i < data.size(); i ++){
            OrderHistory orderHistory = data.get(i);

            if (orderHistory.getStatus() == null || orderHistory.getStatus().equals("Pending") || orderHistory.getStatus().equals("pending") || orderHistory.getStatus().equals("processing")){
                if (!pendingSectionAdded) {
                    orderHistorySections.add(new OrderHistorySection(ORDER_PENDING));
                    pendingSectionAdded = true;
                }
                orderHistorySections.get(0).getAryOrders().add(orderHistory);
            }else {
                if (!completeSectionAdded) {
                    orderHistorySections.add(new OrderHistorySection(ORDER_COMPLETED));
                    completeSectionAdded = true;
                }
                orderHistorySections.get(1).getAryOrders().add(orderHistory);
            }
        }
    }

    void setRvAdapter(){
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter(getContext(), orderHistorySections, this);
        rv.setAdapter(this.adapter);
//        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05651()));
    }

    @Override
    public int getMenuVisibility() {
        return 1;
    }

    @Override
    public void onClickHistoryItem(int sectionIndex, OrderHistory orderHistory) {
        FragmentUtils.changeFragment(HistoryListFragment.this.getActivity(), R.id.content, OrderHistoryDetailsFragment.newInstance(HistoryListFragment.this.type, orderHistory), true);
    }
}
