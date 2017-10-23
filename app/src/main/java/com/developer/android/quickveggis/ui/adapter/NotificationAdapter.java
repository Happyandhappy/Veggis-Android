package com.developer.android.quickveggis.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.NotificationModel;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationAdapter extends Adapter<ViewHolder> {
    Context context;
    List<NotificationModel> data;

    public class NotificationHolder extends ViewHolder {

        @Bind(R.id.redDot)
        ImageView redDot;
        @Bind(R.id.img_icon)
        ImageView imgIcon;
        @Bind(R.id.txtDesc)
        TextView txtDesc;
        @Bind(R.id.txtDateTime)
        TextView txtTime;
        @Bind(R.id.imgIndicator)
        ImageView imgIndicator;

        public NotificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind((Object) this, itemView);
        }
    }

    public NotificationAdapter(List<NotificationModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationHolder notificationHolder = (NotificationHolder) holder;
        NotificationModel notification = (NotificationModel) this.data.get(position);

        String description = notification.getContent();

        JSONObject obj = null;
        String titleObj = "";
        String alertObj = "";
        String iconImg = "";
        try {

            obj = new JSONObject(description);

            titleObj = obj.getString("title");
            alertObj = obj.getString("alert");
            iconImg = obj.getString("licon");
            if ( !iconImg.equals("") ) {
                if ( !iconImg.contains("http")) {
                    iconImg = "http://" + iconImg;
                }
                Picasso.with( this.context).load(iconImg).fit().centerInside().into(((NotificationHolder) holder).imgIcon);
            }


            String customStr = obj.getString("custom");
            JSONObject customObj0 = new JSONObject(customStr);

            if (customObj0 != null) {
                String urlObj = customObj0.getString("u");
                if (urlObj != null) {
                    ((NotificationHolder) holder).txtDesc.setText(alertObj);
                    ((NotificationHolder) holder).imgIndicator.setVisibility(View.VISIBLE);
                }
            }

            Log.d("My App", obj.toString());

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + description + "\"");

            ((NotificationHolder) holder).txtDesc.setText(alertObj);
        }

        ((NotificationHolder) holder).txtTime.setText(notification.getDateTime());

    }

    public int getItemCount() {
        return this.data.size();
    }
}
