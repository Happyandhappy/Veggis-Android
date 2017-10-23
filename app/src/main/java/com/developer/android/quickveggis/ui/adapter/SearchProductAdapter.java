package com.developer.android.quickveggis.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchProductAdapter extends Adapter<SearchProductAdapter.Holder> {
    Context context;
    ArrayList<Product> products;

    public class Holder extends ViewHolder {
        @Bind(R.id.imgIcon)
        ImageView imgIcon;

        @Bind(R.id.txtSize)
        TextView txtSize;

        @Bind(R.id.txtCategory)
        TextView txtCategory;

        @Bind(R.id.txtName)
        TextView txtTitle;

        @Bind(R.id.txtPrice)
        TextView txtPrice;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public SearchProductAdapter(Context context, ArrayList<Product> data) {
        this.context = context;
        products = new ArrayList<>();
        this.products.addAll(data);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.item_search_result, parent, false));
    }

    public void onBindViewHolder(Holder holder, int position) {
        Product product = products.get(position);

        holder.txtSize.setText( "Size: " + product.getSize());

//        if (product.getCategory().size() > 0 ) {
//            holder.txtCategory.setText( "Veriety: " + product.getCategory().get(0).getName());
//        } else {
//            holder.txtCategory.setText("Veriety: ");
//        }
        holder.txtCategory.setText("Veriety: " + product.getVariety());

        holder.txtTitle.setText(product.getName());

        if (product.getImage() != null && !product.getImage().isEmpty()) {
            Picasso.with(getContext()).load(product.getImage()).fit().centerInside().into(holder.imgIcon);
        } else {
//            Picasso.with(getContext()).load(product.getImageId()).fit().centerInside().into(holder.imgIcon);
//            holder.imgIcon.setImageResource(product.getImageId());
        }

        holder.txtPrice.setText(String.format("%.2f", Float.parseFloat(product.getTotalTaskAmount())) + "र Rebate");
    }

    public Context getContext() {
        return this.context;
    }

    public int getItemCount() {
        return this.products.size();
    }
}
