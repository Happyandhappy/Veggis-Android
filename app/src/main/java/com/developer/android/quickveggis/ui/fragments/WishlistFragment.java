package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.model.WishProduct;
import com.developer.android.quickveggis.ui.activity.MainActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.App.wishlistChanged;
import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;

public class WishlistFragment extends Fragment implements WishAdapter.ActionListener, MainActivity.MenuController {

    static String STR_KEY_PRODUCT_ID = "wish_product_id";
    WishAdapter adapter;
    ArrayList<WishProduct> data = new ArrayList();
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.wishlistLoadingPage)
    LinearLayout wishListLoadingPage;
    @Bind(R.id.noProudctsLayout)
    RelativeLayout noProudctsLayout;
    ProgressDialog pDialog;
    int productID = -1;  // product ID to be added to Wishlist

    public static WishlistFragment mFragment;

    public WishlistFragment() {

    }

    public static WishlistFragment newInstance() {
        Bundle args = new Bundle();
        mFragment = new WishlistFragment();
//        if (productId > 0)
//            args.putInt(STR_KEY_PRODUCT_ID, productId);
        mFragment.setArguments(args);
        return mFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.wishlist);

        //productID = (int)getArguments().getInt(STR_KEY_PRODUCT_ID);
        wishListLoadingPage.setVisibility(View.VISIBLE);
        if (wishlistChanged) {
            getWishList();
        } else {
            wishListLoadingPage.setVisibility(View.GONE);
            if (data.size() > 0) {
                rv.setVisibility(View.VISIBLE);
            } else {
                noProudctsLayout.setVisibility(View.VISIBLE);
            }
            initRecyclerView();
        }

    }

    private void initRecyclerView(){
        //this.data = TestData.getProducts();
        this.rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.adapter = new WishAdapter(getContext(), this.data, this);
        this.rv.setAdapter(this.adapter);
        //this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_products)).build());
        //this.rv.addItemDecoration(((HorizontalDividerItemDecoration.Builder) ((HorizontalDividerItemDecoration.Builder) new HorizontalDividerItemDecoration.Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_products)).build());

    }

    private void getWishList() {

        ServiceAPI.newInstance().getWishList(new ResponseCallback<ArrayList<WishProduct>>() {
            @Override
            public void onSuccess(ArrayList<WishProduct> data) {
                wishListLoadingPage.setVisibility(View.GONE);
                if (data.size() > 0) {
                    startFadeInAnim(rv);
                    rv.setVisibility(View.VISIBLE);
                } else {
                    noProudctsLayout.setVisibility(View.VISIBLE);
                }
                WishlistFragment.this.data = data;
                if (data.size() == 0)
                    Toast.makeText(getActivity(), "There is no product.", Toast.LENGTH_SHORT).show();
                initRecyclerView();

                wishlistChanged = false;
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
//        OkHttpTask wishTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        try {
//                            if (pDialog.isShowing())
//                                pDialog.dismiss();
//
//                            if (jsonRes.getBoolean("success")) {
//                                JSONObject wishListJson = jsonRes.getJSONObject("data");
//                                if (wishListJson != null){
//                                    JSONArray responseData = wishListJson.getJSONArray("products");
//                                    for (int i = 0; i < responseData.length(); i++){
//                                        JSONObject jsonProduct = responseData.getJSONObject(i);
//                                        WishData wishData = new WishData();
//                                        wishData.setProductId(jsonProduct.getInt("product_id"));
//                                        wishData.setThumbPath(jsonProduct.getString("thumb"));
//                                        wishData.setProductName(jsonProduct.getString("name"));
//                                        wishData.setProductModel(jsonProduct.getString("model"));
//                                        wishData.setStock(jsonProduct.getString("stock"));
//                                        wishData.setPrice(jsonProduct.getDouble("price"));
//                                        wishData.setDiscount(jsonProduct.getDouble("special"));
//
//                                        WishlistFragment.this.data.add(wishData);
//                                    }
//                                    //Toast.makeText(getActivity(), "WishLists available", Toast.LENGTH_LONG).show();
//                                    Config.list_wishData = WishlistFragment.this.data;
//                                    if (Config.list_wishData.size() == 0)
//                                        Toast.makeText(getActivity(), "There is no product.", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(getActivity(), "No Wish List found", Toast.LENGTH_LONG).show();
//                            }
//
//                            initRecyclerView();
//                        } catch (JSONException e) {
//                            //pDialog.cancel();
//                            e.printStackTrace();
//                            initRecyclerView();
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
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        initRecyclerView();
//                        Toast.makeText(getActivity(), "Error connect to Server, make sure you are online", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        initRecyclerView();
//                        Toast.makeText(getActivity(), "Error while trying to get wishlist", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }, OkHttpTask.GET, null);
//
//        //wishTask.execute(Config.API_GET_WISHLIST + UserInfo.getInstance().getId() + "");
//        wishTask.execute(Config.API_GET_WISHLIST1);
//    }

    //ActionListener Callback Method
    public void onSelected(WishProduct product) {
        productID = Integer.parseInt(product.getProductId());
        //5.28
        //FragmentUtils.changeFragment(getActivity(), (int) R.id.content, ProductFragment.newInstance(productID), true);
    }

    public void onRemove(WishProduct product) {
//        this.data.remove(product);
//        this.adapter.notifyDataSetChanged();
        removeProductFromWishList(product);
    }

    private void removeProductFromWishList(final WishProduct product){
        pDialog = new ProgressDialog(WishlistFragment.this.getActivity());
        pDialog.setMessage("Deleting product. Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        ServiceAPI.newInstance().removeProductFromWishList(product.getProductId(), new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                WishlistFragment.this.data.remove(product);
                WishlistFragment.this.adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
//        OkHttpTask deleteProductTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        try {
//                            if (jsonRes.getBoolean("success")) {
//                                Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();
//                                WishlistFragment.this.data.remove(product);
//                                WishlistFragment.this.adapter.notifyDataSetChanged();
//                            } else {
//                                Toast.makeText(getActivity(), "Failed.\nPlease try again later", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        //gotoWishListScreen();
//                    }
//                });
//            }
//
//            @Override
//            public void errorConnect() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//
//                        Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
//                        //gotoWishListScreen();
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (pDialog.isShowing())
//                            pDialog.dismiss();
//                        Toast.makeText(getActivity(), "Error while trying to remove product to wish list", Toast.LENGTH_SHORT).show();
//                        //gotoWishListScreen();
//                    }
//                });
//            }
//        }, OkHttpTask.DELETE, null);
//
//        deleteProductTask.execute(Config.API_DELETE_PRODUCT_WISHLIST + product.getProductId() + "");
    }

    public int getMenuVisibility() {
        return 0;
    }
}
