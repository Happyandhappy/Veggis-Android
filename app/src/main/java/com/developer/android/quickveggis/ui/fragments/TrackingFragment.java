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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.TrackItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;
import java.util.ArrayList;
import java.util.List;

public class TrackingFragment extends Fragment {
    TrackingAdapter adapter;
    List<TrackItem> data;
    @Bind(R.id.rv)
    RecyclerView rv;

    public class TrackingAdapter extends Adapter<TrackingAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.imgIcon)
            ImageView imgIcon;
            @Bind(R.id.txtDate)
            TextView txtDate;
            @Bind(R.id.txtTime)
            TextView txtTime;
            @Bind(R.id.txtTitle)
            TextView txtTitle;
            @Bind(R.id.txtValue)
            TextView txtValue;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(TrackingFragment.this.getContext()).inflate(R.layout.item_tracking, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            TrackItem item = (TrackItem) TrackingFragment.this.data.get(position);
            holder.imgIcon.setImageResource(item.getIcon());
            holder.txtTitle.setText(item.getTitle());
            holder.txtValue.setText(item.getValue());
        }

        public int getItemCount() {
            return TrackingFragment.this.data.size();
        }
    }

    public TrackingFragment() {
        this.data = new ArrayList();
    }

    public static TrackingFragment newInstance() {
        Bundle args = new Bundle();
        TrackingFragment fragment = new TrackingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillInfo();
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new TrackingAdapter();
        this.rv.setAdapter(this.adapter);
        this.rv.setNestedScrollingEnabled(false);
        this.rv.setHasFixedSize(false);
        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(R.color.divider))).sizeResId(R.dimen.divider_track)).build());
    }

    private void fillInfo() {
        this.data.clear();
        this.data.add(new TrackItem(R.drawable.ic_order_placed, R.string.order_placed, "Bla Bla Bla", R.color.blue));
        this.data.add(new TrackItem(R.drawable.ic_dispatched, R.string.order_dispathched, "Bla Bla Bla", R.color.blue));
        this.data.add(new TrackItem(R.drawable.ic_order_received, R.string.order_received, "Bla Bla Bla", R.color.mainGreen));
    }
}
