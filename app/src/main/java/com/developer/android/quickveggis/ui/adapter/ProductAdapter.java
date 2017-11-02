package com.developer.android.quickveggis.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.ui.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductAdapter extends Adapter<ProductAdapter.Holder> {
    Context context;
    ArrayList<Product> products;

    public class Holder extends ViewHolder {
        @Bind(R.id.imgIcon)
        ImageView imgIcon;
        @Bind(R.id.topLine)
        View topLine;
        @Bind(R.id.txtPrice)
        TextView txtPrice;
        @Bind(R.id.txtTitle)
        TextView txtTitle;
        @Bind(R.id.txtValietyAndSize)
        TextView txtValietyAndSize;
        @Bind(R.id.quantityText)
        TextView quantity;
        @Bind(R.id.quantityLayout)
        RelativeLayout quantityLayout;
        @Bind(R.id.newBadge)
        TextView newBadge;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ProductAdapter(Context context, ArrayList<Product> data) {
        this.context = context;
        products = new ArrayList<>();
        this.products.addAll(data);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false));
    }

    public void onBindViewHolder(Holder holder, int position) {
        Product product = products.get(position);
        if ((position / 2) % 2 == 0) {
            if (position % 2 == 0) {
                holder.topLine.setBackgroundResource(R.drawable.product_top_yellow);
            } else {
                holder.topLine.setBackgroundResource(R.drawable.product_top_red);
            }
        } else if (position % 2 == 0) {
            holder.topLine.setBackgroundResource(R.drawable.product_top_red);
        } else {
            holder.topLine.setBackgroundResource(R.drawable.product_top_yellow);
        }
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            Picasso.with(getContext()).load(product.getImage().replace(" ", "%20")).fit().centerInside().into(holder.imgIcon);
        } else {
//            Picasso.with(getContext()).load(product.getImageId()).fit().centerInside().into(holder.imgIcon);
//            holder.imgIcon.setImageResource(product.getImageId());
        }
        holder.txtPrice.setText( String.format("%.0f", Float.valueOf(product.getTotalTaskAmount())) + "र Cash Back" ); //  + "Cash Back"
        holder.txtPrice.setSelected(true);
        holder.txtTitle.setText(product.getName());
        holder.txtTitle.setSelected(true);
        holder.txtValietyAndSize.setText(product.getSize() + "-" + product.getVariety());
        holder.txtValietyAndSize.setSelected(true);
        if (Integer.valueOf(product.getMinimum()) > 1) {
            holder.quantity.setText("x" + product.getMinimum());
            holder.quantityLayout.setVisibility(View.VISIBLE);
        }

        try {
            boolean isNew = TimeUtils.isNewProduct(product.getDateAdded(), "yyyy-MM-dd HH:mm:ss");
            if (isNew) {
                holder.newBadge.setVisibility(View.VISIBLE);
            } else {
                holder.newBadge.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return this.context;
    }

    public int getItemCount() {
        return this.products.size();
    }
}
