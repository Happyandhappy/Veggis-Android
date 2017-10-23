package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.GiftCard;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.PayoutActivity;
import com.developer.android.quickveggis.ui.adapter.CardAdapter;
import com.developer.android.quickveggis.ui.adapter.ZoomOutPageTransformer;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.quickveggies.quickveggies.ui.custom.PagerContainer;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WalletFragment extends Fragment implements MainActivity.MenuController, CardAdapter.Listener{
    CardAdapter adapter;
    @Bind(R.id.btnPayout)
    View btnPayout;

    @Bind(R.id.pagerContainer)
    PagerContainer pagerContainer;

    @Bind(R.id.indicator)
    CirclePageIndicator indicator;

    @Bind(R.id.txt_wallet)
    TextView txtWalletAmount;

    @Bind(R.id.txt_pending)
    TextView txtPendingAmount;

    @Bind(R.id.btnSubmit)
    Button btnSubmit;

    List<GiftCard> cards = new ArrayList<>();

    public static WalletFragment mFragment;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.WalletFragment.1 */

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        mFragment = new WalletFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, view);

        String walletAmount = CustomerController.getInstance().getLoggedInCustomer().getWallet().getWalletTotal();
        String pendingAmount = CustomerController.getInstance().getLoggedInCustomer().getWallet().getPendingEarnings();
        if (walletAmount != null){
            txtWalletAmount.setText(String.format("%s₹", walletAmount));
        }
        if (pendingAmount != null){
            txtPendingAmount.setText(String.format("%s₹", pendingAmount));
        }

        if (Integer.valueOf(walletAmount) >= 100) {
            btnPayout.setEnabled(true);
        } else {
            btnPayout.setEnabled(false);
        }

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.payout);
        final ViewPager viewPager = pagerContainer.getViewPager();

        adapter = new CardAdapter(getContext(), cards, pagerContainer, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, true);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        this.indicator.setViewPager(viewPager);

        if (cards.size() == 0) {
            getGiftCards();
        }

        //when press "Cash Out" button

        btnPayout.setOnClickListener(mOnClickListener);

        //when press "Submit" button
        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(PayoutActivity.getStartIntent(getContext()));
        }
    };

    public int getMenuVisibility() {
        return 0;
    }

//    public void onCardSelected(GiftCard card) {
//        FragmentUtils.changeFragment(getActivity(), R.id.content, GiftCardConfirmFragment.newInstance(), true);
//    }

    private void getGiftCards(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ServiceAPI.newInstance().getGiftCards(new ResponseCallback<ArrayList<GiftCard>>() {
            @Override
            public void onSuccess(ArrayList<GiftCard> data) {
                progressDialog.dismiss();
                cards.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

//        OkHttpTask giftTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        progressDialog.cancel();
//                        try {
//                            if (jsonRes.getBoolean("success")) {
//
//                                JSONArray jsonGiftsArray = jsonRes.getJSONArray("data");
//                                for (int i = 0; i < jsonGiftsArray.length(); i++){
//                                    JSONObject jsonGift = jsonGiftsArray.getJSONObject(i);
//
//                                    GiftCard gift = new GiftCard();
//                                    gift.setId(jsonGift.getInt("giftcards_id"));
//                                    gift.setName(jsonGift.getString("name"));
//                                    gift.setAmount(jsonGift.getDouble("amount"));
//                                    gift.setImage(jsonGift.getString("image"));
//                                    gift.setSort_order(jsonGift.getInt("sort_order"));
//
//                                    cards.add(gift);
//                                }
//                                Config.list_gifts = cards;
//                                adapter.notifyDataSetChanged();
//
//                            } else {
//                                DialogUtils.showAlertDialog(getActivity(), "Error while trying to add bank account, please try again later");
//                            }
//                        } catch (JSONException e) {
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
//                        progressDialog.cancel();
//                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.cancel();
//                        Toast.makeText(getActivity(), "Error while trying to add bank account, please try again later", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }, OkHttpTask.GET, null);
//
//        giftTask.execute(Config.API_GET_GIFTCARDS);
    }

    @Override
    public void onCardSelected(GiftCard card) {
        FragmentUtils.changeFragment(getActivity(), R.id.content, GiftCardConfirmFragment.newInstance(card), true);
    }
}
