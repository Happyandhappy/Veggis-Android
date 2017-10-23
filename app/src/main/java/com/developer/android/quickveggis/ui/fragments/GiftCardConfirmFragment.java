package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.SaveGiftCardData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.GiftCard;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.squareup.picasso.Picasso;

public class GiftCardConfirmFragment extends Fragment implements MainActivity.MenuController {
    @Bind(R.id.img)
    ImageView image;
    @Bind(R.id.txtPrice)
    TextView txtPrice;
    @Bind(R.id.btnNext)
    View btnNext;

    GiftCard currentCard;

    public static GiftCardConfirmFragment newInstance(GiftCard giftCard) {
        GiftCardConfirmFragment fragment = new GiftCardConfirmFragment();
        fragment.currentCard = giftCard;
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giftcard, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (currentCard != null){
            this.txtPrice.setText(String.format("%.0f \u20B9 ", Float.parseFloat(currentCard.getAmount())));
            if(currentCard.getImage() != null)
                Picasso.with(getContext()).load(currentCard.getImage()).fit().centerInside().into(this.image);
        }
        else{

        }

        //Picasso.with(getContext()).load("http://library.corporate-ir.net/library/17/176/176060/mediaitems/93/a.com_logo_RGB.jpg").fit().centerInside().into(this.image);
        //when click "Confirm" button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGiftCardRequest();
            }
        });
    }

    private void sendGiftCardRequest() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        ServiceAPI.newInstance().saveGiftCard(new SaveGiftCardData(CustomerController.getInstance().getLoggedInCustomer().getCustomerId(), currentCard.getGiftcardsId(), currentCard.getAmount()),
                new ResponseCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), "Payment Process Success", Toast.LENGTH_LONG).show();
                        FragmentUtils.changeFragment(getActivity(), R.id.content, SuccessFragment.newInstance(), false);
                    }

                    @Override
                    public void onFailure(String error) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), "Payment Process Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
                    }
                });

//        //List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//        JSONObject params = new JSONObject();
//        try {
//            if (currentCard != null){
//                params.accumulate("customer_id", UserInfo.getInstance().getId());
//                params.accumulate("giftcards_id", currentCard.getId());
//                //for test
//                params.accumulate("amount", currentCard.getAmount());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        OkHttpTask cardTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        pDialog.cancel();
//                        try {
//                            if (jsonRes.has("success")) {
//                                Toast.makeText(getActivity(), "Payment Process Success", Toast.LENGTH_LONG).show();
//                                FragmentUtils.changeFragment(getActivity(), (int) R.id.content, SuccessFragment.newInstance(), false);
//                            } else {
//                                Toast.makeText(getActivity(), "Payment Process Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void errorConnect() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pDialog.cancel();
//                        DialogUtils.showAlertDialog(getActivity(), getActivity().getResources().getString(R.string.connect_error));
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pDialog.cancel();
//                        Toast.makeText(getActivity(), "Error while trying to cash out", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }, OkHttpTask.POST, params);
//
//        cardTask.execute(Config.API_SAVE_GIFTCARD);
    }

    public int getMenuVisibility() {
        return 1;
    }
}