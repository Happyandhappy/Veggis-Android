package com.developer.android.quickveggis.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PreviewAdapter extends Adapter<PreviewAdapter.Holder> {
    Context context;
    List<Uri> images;
    PreviewListener previewListener;

    /* renamed from: com.quickveggies.quickveggies.ui.adapter.PreviewAdapter.1 */
    class C02711 implements OnClickListener {
        final /* synthetic */ int val$position;

        C02711(int i) {
            this.val$position = i;
        }

        public void onClick(View v) {
            if (PreviewAdapter.this.previewListener != null) {
                PreviewAdapter.this.previewListener.onDelete((Uri) PreviewAdapter.this.images.get(this.val$position));
            }
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.adapter.PreviewAdapter.2 */
    class C02722 implements OnClickListener {
        final /* synthetic */ int val$position;

        C02722(int i) {
            this.val$position = i;
        }

        public void onClick(View v) {
            if (PreviewAdapter.this.previewListener != null) {
                PreviewAdapter.this.previewListener.onShow((Uri) PreviewAdapter.this.images.get(this.val$position));
            }
        }
    }

    public interface PreviewListener {
        void onDelete(Uri uri);

        void onShow(Uri uri);
    }

    public class Holder extends ViewHolder {
        @Bind(R.id.btnDelete)
        View btnDelete;
        @Bind(R.id.img)
        ImageView image;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind((Object) this, itemView);
        }
    }

    public PreviewAdapter(List<Uri> images, Context context, PreviewListener previewListener) {
        this.images = new ArrayList();
        this.images = images;
        this.context = context;
        this.previewListener = previewListener;
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.item_preview, parent, false));
    }

    public void onBindViewHolder(Holder holder, int position) {
        Picasso.with(this.context).load((Uri) this.images.get(position)).fit().centerCrop().into(holder.image);
        holder.btnDelete.setOnClickListener(new C02711(position));
        holder.itemView.setOnClickListener(new C02722(position));
    }

    public int getItemCount() {
        return this.images.size();
    }
}
