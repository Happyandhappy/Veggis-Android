package com.developer.android.quickveggis.ui.fragments;

import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Category;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.ProductsActivity;
import com.developer.android.quickveggis.ui.adapter.ProductAdapter;
import com.developer.android.quickveggis.ui.utils.PreferenceUtil;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;

public class ProductsFragment extends Fragment {
    ProductAdapter adapter;
    ArrayList<Product> products;
    ArrayList<Product> productsFiltered;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.backToTop)
    RelativeLayout backToTop;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @Bind(R.id.productsLoadingPage)
    LinearLayout productsLoadingPage;
    @Bind(R.id.noProudctsLayout)
    RelativeLayout noProudctsLayout;
    @Bind(R.id.tutorialLayout)
    RelativeLayout tutorialLayout;

    Category category;
    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductsFragment.1 */
    class C05681 implements RecyclerItemClickListener.OnItemClickListener {
        C05681() {
        }

        public void onItemClick(View view, int position) {
            if (position < 0 && position >= productsFiltered.size())    return;
            Intent intent = MainActivity.getStartIntent(getContext(), Config.PRODUCT_MODE);
            intent.putExtra("item", position);

            Bundle bundle = new Bundle();
            bundle.putString("product", new Gson().toJson(productsFiltered.get(position)));
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
        }
    }

    public ProductsFragment() {
        this.products = new ArrayList();
        this.productsFiltered = new ArrayList<>();
    }

    public static ProductsFragment newInstance(Category category) {
        Bundle args = new Bundle();
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        fragment.category = category;
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tutorialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

//        getActivity().setTitle("Food mart");
        products = new ArrayList();
        productsFiltered = new ArrayList<>();
//        this.data = TestData.getNewProducts();
        this.rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.adapter = new ProductAdapter(getContext(), productsFiltered);
        this.rv.setAdapter(this.adapter);
//        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_products)).build());
//        this.rv.addItemDecoration(((HorizontalDividerItemDecoration.Builder) ((HorizontalDividerItemDecoration.Builder) new HorizontalDividerItemDecoration.Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_products)).build());
        this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05681()));

        this.productsLoadingPage.setVisibility(View.VISIBLE);
        ServiceAPI.newInstance().getProductsByCategory(category.getCategoryId(), new ResponseCallback<ArrayList<Product>>() {
            @Override
            public void onSuccess(ArrayList<Product> data) {

                productsLoadingPage.setVisibility(View.GONE);
                if (data.size() > 0) {
                    rv.setVisibility(View.VISIBLE);
                    startFadeInAnim(rv);
                } else {
                    noProudctsLayout.setVisibility(View.VISIBLE);
                }
                ArrayList<String> arrayTemp = ((ProductsActivity) ProductsFragment.this.getActivity()).filterFragment.brandList;
                for ( Product proTemp: data ) {
                    if (proTemp.getManufacturer() != null) {
                        if ( !arrayTemp.contains(proTemp.getManufacturer()) ) {
                            ((ProductsActivity) ProductsFragment.this.getActivity()).filterFragment.brandList.add(proTemp.getManufacturer());
                        }
                    }
                }

                products.addAll(data);
                productsFiltered.addAll(data);
                adapter.setProducts(productsFiltered);
                adapter.notifyDataSetChanged();

                if (PreferenceUtil.getBooleanFromPreference(getActivity(), Config.PRODUCTS_TUTORIAL_VISIBLE, true)) {
                    tutorialLayout.setVisibility(View.VISIBLE);
                    PreferenceUtil.saveBooleanToPreference(getActivity(), Config.PRODUCTS_TUTORIAL_VISIBLE, false);
                } else {
                    tutorialLayout.setVisibility(View.GONE);
                }

                bottomLayout.setVisibility(View.VISIBLE);
                if (productsFiltered.size() >= 12) {
                    backToTop.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String error) {
                productsLoadingPage.setVisibility(View.GONE);
                noProudctsLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });

        backToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0, 0);
                rv.smoothScrollToPosition(0);
            }
        });
    }

    public void doFilte(String item1, String item2, String item3) {

        // sorting item1
        if ( !item1.equals("") ) {
            this.sortProductsByiTem1(item1);
        }

        productsFiltered.clear();
        productsFiltered.addAll(products);
        // Filter by brands
        for (Product item : products) {

            if (item3.equals("")) break;

            if (item.getManufacturer() != null) {
                if (item3.toUpperCase().contains(item.getManufacturer().toUpperCase())) {

                } else {
                    productsFiltered.remove(item);
                }
            } else {
                productsFiltered.remove(item);
            }
        }

        // sorting item3
        adapter.notifyDataSetChanged();
    }

    private void sortProductsByiTem1(final String item1) {
        Comparator<Product> comparator = new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {

                switch (Integer.valueOf(item1)) {
                    case 1:  // New
                        return lhs.getDateAdded().compareTo(rhs.getDateAdded());

                    case 2:   // Propularity
//                        return lhs.getPopularity().compareTo(rhs.getPopularity());
                        return (int)((Float.valueOf(lhs.getPopularity()) - Float.valueOf(rhs.getPopularity())) * 100);
                    case 3: //  Ending soon
                     case 4:  // Price low to high
                        return (int)((Float.valueOf(lhs.getTotalTaskAmount()) - Float.valueOf(rhs.getTotalTaskAmount())) * 100);

                    default:
                        break;
                }
                return 0;

            }
        };

        Collections.sort(products, comparator);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 513){
            getActivity().finish();
        }
    }

}
