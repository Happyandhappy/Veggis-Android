package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.model.Order;
import com.developer.android.quickveggis.model.OrderHistory;
import com.developer.android.quickveggis.model.OrderTotal;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.TrackingActivity;
import com.squareup.picasso.Picasso;

public class OrderHistoryDetailsFragment extends Fragment implements MainActivity.MenuController {
    @Bind(R.id.blockPaymentAddress)
    View blockAddress;
    @Bind(R.id.blockItems)
    LinearLayout blockItems;
    @Bind(R.id.blockPaymentMethod)
    View blockMethod;
    @Bind(R.id.blockPaymentStatus)
    View blockStatus;
    @Bind(R.id.btnTrack)
    View btnTrack;
    @Bind(R.id.txtTotal)
    TextView txtTotal;
//    @Bind(R.id.txtPaymentMethod)
//    TextView txtPaymentMethod;
    @Bind(R.id.txtPaymentAddress)
    TextView txtPaymentAddress;
    @Bind(R.id.txtPaymentStatus)
    TextView txtPaymentStatus;
    @Bind(R.id.txt_order_id)
    TextView txtOrderId;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.txtDate)
    TextView txtDate;

    int type;
    OrderHistory orderHistory;
    Order order;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.OrderHistoryDetailsFragment.1 */
    class C02981 implements OnClickListener {
        C02981() {
        }

        public void onClick(View v) {
            OrderHistoryDetailsFragment.this.startActivity(TrackingActivity.getStartIntent(OrderHistoryDetailsFragment.this.getContext()));
        }
    }

    public static OrderHistoryDetailsFragment newInstance(int type, OrderHistory orderHistory) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        OrderHistoryDetailsFragment fragment = new OrderHistoryDetailsFragment();
        fragment.setArguments(args);
        fragment.orderHistory = orderHistory;
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history_detail_2, container, false);
        ButterKnife.bind(this, view);
        this.type = getArguments().getInt("type");
        getOrderDetail();
        return view;
    }

    void initWidget(){
        fillAnswers();
        updateUiState();
        generateTotalText();
        if (this.type == 1) {
            this.btnTrack.setVisibility(View.GONE);
        }
        this.btnTrack.setOnClickListener(new C02981());
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void generateTotalText() {

        Float totalPrice = 0.0f;
        Float subTotalPrice = 0.0f;
//        for (int i = 0; i < order.getTotals().size(); i++){
//            OrderTotal total = order.getTotals().get(i);
//            if (total.getCode().equals(total)){
//                totalPrice += Float.parseFloat(total.getValue());
//            }else {
//                subTotalPrice += Float.parseFloat(total.getValue());
//            }
//        }
        for (int i = 0; i < order.getProducts().size(); i++){
            Order.ProductsBean total = order.getProducts().get(i);
//            if (total.getCode().equals(total)){
                totalPrice += Float.parseFloat(total.getTotal());
//            }else {
//                subTotalPrice += Float.parseFloat(total.getValue());
//            }
        }
//        if (this.type == 0) {
//            this.txtTotal.setText(genTotalDeliver("Rs 92.00", "Rs 32.00", String.format("%.2f", subTotalPrice)));
//        } else {
            this.txtTotal.setText(genTotalRedeem(String.format("%.2f", totalPrice)));
//        }
    }

    private SpannableString genTotalDeliver(String subtotal, String shipping, String total) {
//        SpannableString sp = new SpannableString(getString(R.string.history_temp_total_delivery, subtotal, shipping, total)); ///??? - this is origin
        SpannableString sp = new SpannableString(getString(R.string.history_temp_total_delivery, total));
        String result = sp.toString();
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.blue)), result.lastIndexOf(total), result.length(), 0);
        return sp;
    }

    private SpannableString genTotalRedeem(String total) {
        SpannableString sp = new SpannableString(getString(R.string.history_temp_total_redeem, total));
        String result = sp.toString();
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.blue)), result.lastIndexOf(total), result.length(), 0);
        return sp;
    }

    public void updateUiState() {
        txtOrderId.setText(String.format("Order ID: #%s", order.getOrderId()));
        txtDate.setText(order.getDateAdded());
        if (orderHistory.getStatus() == null || orderHistory.getStatus().equals("Pending") || orderHistory.getStatus().equals("pending") || orderHistory.getStatus().equals("processing"))
            txtStatus.setText("Pending");
        else
            txtStatus.setText("Completed");

        if (this.type == 0) {
//            this.blockMethod.setVisibility(View.VISIBLE);
//            this.blockAddress.setVisibility(View.VISIBLE);
//            this.blockStatus.setVisibility(View.GONE);
            txtPaymentAddress.setText(order.getPaymentAddress());
//            txtPaymentMethod.setText(order.getPaymentMethod());
            return;
        }
//        this.blockMethod.setVisibility(View.GONE);
//        this.blockAddress.setVisibility(View.GONE);
        this.blockStatus.setVisibility(View.VISIBLE);
    }

    private void fillAnswers() {
        this.blockItems.removeAllViews();
        for (int i = 0; i < order.getProducts().size(); i++) {
            Order.ProductsBean product = order.getProducts().get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_history_item, this.blockItems, false);
            ImageView icon = (ImageView) view.findViewById(R.id.imgIcon);
            TextView name = (TextView) view.findViewById(R.id.txtName);
            TextView model = (TextView) view.findViewById(R.id.txtModel);
            TextView price = (TextView) view.findViewById(R.id.txtPrice);
//
//            Uri imgUri = Uri.parse(product.getImageIcon());
////
//            icon.setImageURI(imgUri);

            Picasso.with(getContext()).load(product.getImageIcon()).fit().centerInside().into(icon);

            name.setText(product.getName());
            model.setText(product.getModel());
            price.setText(product.getTotal());

            this.blockItems.addView(view);
        }
    }

    void getOrderDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ServiceAPI.newInstance().getOrderById(orderHistory.getOrderId(), new ResponseCallback<Order>() {
            @Override
            public void onSuccess(Order data) {
                order = data;
                initWidget();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getMenuVisibility() {
        return 1;
    }
}
