package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.ProductBanner;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by kimsil3 on 4/26/2016.
 */
public class CategoryDetailPagerFragment extends Fragment {

    ProductBanner productBanner;

    public CategoryDetailPagerFragment() {

    }

    public CategoryDetailPagerFragment (ProductBanner banner) {

        productBanner = banner;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categorydetail, container, false);

        // To set product image
        ImageView imgView = (ImageView)root.findViewById(R.id.img);

        if (productBanner.getProductInfo().getBanner() != null ) {
            Picasso.with(getActivity()).load(productBanner.getProductInfo().getBanner().replace(" ", "%20")).into(imgView);
        }
//
//        // ===================== Working now ===================
//        // to moveinto product detail page with product ID
//
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveIntoProductDetail(productBanner.getProductInfo());
            }
        });

        return root;
    }

    public void moveIntoProductDetail(Product product) {

        FragmentUtils.changeFragment(getActivity(), R.id.content, ProductFragment.newInstance(product), false);

        Toast.makeText(getActivity(), "Please get product info", Toast.LENGTH_SHORT).show();

//        Intent intent = ProductActivity.getStartIntent(getContext());
//        intent.putExtra("item", position);
//
//        Bundle bundle = new Bundle();
//        //      bundle.putString("product", new Gson().toJson(products.get(position)));
//        intent.putExtras(bundle);
//        startActivityForResult(intent, 1);

//        ServiceAPI.newInstance().getBannerProducts(new ResponseCallback<ArrayList<ProductBanner>>() {
//            @Override
//            public void onSuccess(ArrayList<ProductBanner> data) {
////                FragmentUtils.changeFragment(this, R.id.content, ProductFragment.newInstance(product), false);
//            }
//
//            @Override
//            public void onFailure(String error) {
////                progressDialog.dismiss();
//                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

//    public View CategoryDetailPagerFragment(int position) {
//        Fragment frgment = new CategoryDetailPagerFragment();
//        return frgment.getView();
//    }
}
