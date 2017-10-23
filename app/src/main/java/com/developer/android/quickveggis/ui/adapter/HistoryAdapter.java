package com.developer.android.quickveggis.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.OrderHistory;
import com.developer.android.quickveggis.model.OrderHistorySection;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;

public class HistoryAdapter extends SectioningAdapter {

    Context context;
    ArrayList<OrderHistorySection> data;
    private LayoutInflater m_lytInflater;
    HistoryItemClickListener historyItemClickListener;
    
    public HistoryAdapter(Context ctx, ArrayList<OrderHistorySection> data, HistoryItemClickListener historyItemClickListener) {
        super();
        context = ctx;
        m_lytInflater = LayoutInflater.from(context);
        this.data = data;
        this.historyItemClickListener = historyItemClickListener;
    }

    @Override
    public OrderHistoryHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        return new OrderHistoryHeaderViewHolder(m_lytInflater.inflate(R.layout.item_history_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        OrderHistoryHeaderViewHolder holder = (OrderHistoryHeaderViewHolder) viewHolder;
        if (sectionIndex == 0)
            holder.txtHeader.setText("Pending");
        else
            holder.txtHeader.setText("Completed");
    }

    @Override
    public SectioningAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
            return new OrderHistoryViewHolder(m_lytInflater.inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder holder, final int sectionIndex, int itemIndex, int itemType) {

        OrderHistoryViewHolder historyHolder = (OrderHistoryViewHolder) holder;
        final OrderHistory history = this.data.get(sectionIndex).getAryOrders().get(itemIndex);
//        historyHolder.txtTitle.setText(String.format("%s", history.getName()));
        historyHolder.txtTitle.setText(String.format("#%s", history.getOrderId()));
        if (sectionIndex == 1) {
            historyHolder.txtStatus.setText(history.getTotal());
            historyHolder.txtStatus.setTextColor(ContextCompat.getColor(App.getContext(), R.color.mainGreen));
            return;
        }
        historyHolder.txtStatus.setText("Pending");
        historyHolder.txtStatus.setTextColor(ContextCompat.getColor(App.getContext(), R.color.mainRed));
        historyHolder.txtDate.setText(data.get(sectionIndex).getAryOrders().get(itemIndex).getDateAdded());
        historyHolder.cvHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyItemClickListener.onClickHistoryItem(sectionIndex, history);
            }
        });
    }

    @Override
    public int getNumberOfSections() {
        return 2;
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return data.get(sectionIndex).getAryOrders().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
            return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }


    class OrderHistoryViewHolder extends SectioningAdapter.ItemViewHolder {


        @Bind(R.id.txtStatus)
        TextView txtStatus;
        @Bind(R.id.txtTitle)
        TextView txtTitle;
        @Bind(R.id.txtDate)
        TextView txtDate;
        @Bind(R.id.cv_history_item)
        CardView cvHistoryItem;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
    private class OrderHistoryHeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        
        TextView txtHeader;
        public OrderHistoryHeaderViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView)itemView.findViewById(R.id.txtTitle);
        }
        
    }

    public interface HistoryItemClickListener{
        void onClickHistoryItem(int sectionIndex, OrderHistory orderHistory);
    }
}
