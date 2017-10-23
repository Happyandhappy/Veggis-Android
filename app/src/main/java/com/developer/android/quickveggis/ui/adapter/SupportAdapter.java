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
import com.developer.android.quickveggis.model.Support;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupportAdapter extends Adapter<ViewHolder> {
    Context context;
    List<Support> data;

    public class SupportHolder extends ViewHolder {
        @Bind(R.id.imgIcon)
        ImageView imgIcon;
        @Bind(R.id.txtName)
        TextView txtName;
        @Bind(R.id.txtDesc)
        TextView txtDesc;

        public SupportHolder(View itemView) {
            super(itemView);
            ButterKnife.bind((Object) this, itemView);
        }
    }

    public SupportAdapter(List<Support> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SupportHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        SupportHolder SupportHolder = (SupportHolder) holder;
        Support Support = (Support) this.data.get(position);
        ((SupportHolder) holder).imgIcon.setImageResource(context.getResources().getIdentifier(Support.getImgIcon(),"drawable", context.getPackageName()));
        ((SupportHolder) holder).txtName.setText(Support.getTitle());
        ((SupportHolder) holder).txtDesc.setText(Support.getContent());
    }

    public int getItemCount() {
        return this.data.size();
    }
}
