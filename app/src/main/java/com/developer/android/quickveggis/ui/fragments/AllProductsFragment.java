package com.developer.android.quickveggis.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.CartItem;
import com.developer.android.quickveggis.model.Category;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.ProductsActivity;
import com.developer.android.quickveggis.ui.adapter.ProductAdapter;
import com.developer.android.quickveggis.ui.utils.ConvertDpPx;
import com.developer.android.quickveggis.ui.utils.PreferenceUtil;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;

public class AllProductsFragment extends Fragment {
    ArrayList<Product> products;
    ArrayList<Product> productsFiltered;
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
    @Bind(R.id.mainLayout)
    LinearLayout mainLayout;
    @Bind(R.id.tutorialLayout)
    RelativeLayout tutorialLayout;

    Category category;

    ArrayList<RecyclerView> rvList = new ArrayList<>();
    ArrayList<ProductAdapter> adapterList = new ArrayList<>();
    ArrayList<ArrayList<Product>> categoryProductsFilteredList = new ArrayList<>();

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProductsFragment.1 */
    class C05681 implements RecyclerItemClickListener.OnItemClickListener {
        C05681() {
        }

        public void onItemClick(View view, int position) {

            for (int i = 0; i < rvList.size(); i++) {
                if (view.getParent().equals(rvList.get(i))) {
                    Intent intent = MainActivity.getStartIntent(getContext(), Config.PRODUCT_MODE);
                    Bundle bundle = new Bundle();
                    bundle.putString("product", new Gson().toJson(categoryProductsFilteredList.get(i).get(position)));
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                    break;
                }
            }
        }
    }

    public AllProductsFragment() {
        this.products = new ArrayList();
        this.productsFiltered = new ArrayList<>();
    }

    public static AllProductsFragment newInstance(Category category) {
        Bundle args = new Bundle();
        AllProductsFragment fragment = new AllProductsFragment();
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
        products = new ArrayList();
        productsFiltered = new ArrayList<>();

        tutorialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        mainLayout.removeAllViews();

        for (int i = 0; i < ProductsActivity._inst.categoryList.size(); i++) {
            RecyclerView rv = new RecyclerView(getActivity());
            rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rv.setNestedScrollingEnabled(false);
            ArrayList<Product> categoryProductsFiltered = new ArrayList<>();
            ProductAdapter adapter = new ProductAdapter(getContext(), categoryProductsFiltered);
            rv.setAdapter(adapter);
            rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05681()));

            rvList.add(rv);
            adapterList.add(adapter);
            categoryProductsFilteredList.add(categoryProductsFiltered);
        }

        this.productsLoadingPage.setVisibility(View.VISIBLE);
        ServiceAPI.newInstance().getProductsByCategory(category.getCategoryId(), new ResponseCallback<ArrayList<Product>>() {
            @Override
            public void onSuccess(ArrayList<Product> data) {

                productsLoadingPage.setVisibility(View.GONE);
                if (data.size() > 0) {
                    mainLayout.setVisibility(View.VISIBLE);
                    startFadeInAnim(mainLayout);
                } else {
                    noProudctsLayout.setVisibility(View.VISIBLE);
                }
                ArrayList<String> arrayTemp = ((ProductsActivity) AllProductsFragment.this.getActivity()).filterFragment.brandList;
                for ( Product proTemp: data ) {
                    if (proTemp.getManufacturer() != null) {
                        if ( !arrayTemp.contains(proTemp.getManufacturer()) ) {
                            ((ProductsActivity) AllProductsFragment.this.getActivity()).filterFragment.brandList.add(proTemp.getManufacturer());
                        }
                    }
                }

                products.addAll(data);
                sortProducts();
                productsFiltered.addAll(data);

                dataSetChanged();

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
                if (rvList.size() > 0) {
                    rvList.get(0).smoothScrollToPosition(0);
                }
            }
        });
    }

    public void dataSetChanged() {

        for (int i = 0; i < categoryProductsFilteredList.size(); i++) {
            categoryProductsFilteredList.get(i).clear();
        }

        for (int i = 0; i < adapterList.size(); i++) {
            adapterList.get(i).notifyDataSetChanged();
        }

        for (int i = 0; i < productsFiltered.size(); i++) {
            for (int j = 0; j < ProductsActivity._inst.categoryList.size(); j ++) {
                if (productsFiltered.get(i).getCategory().get(0).getName().replaceAll("&amp;", "&").equals(ProductsActivity._inst.categoryList.get(j))) {
                    categoryProductsFilteredList.get(j).add(productsFiltered.get(i));
                }
            }
        }

        for (int i = 0; i < categoryProductsFilteredList.size(); i++) {
            if (categoryProductsFilteredList.get(i).size() > 0) {
                adapterList.get(i).setProducts(categoryProductsFilteredList.get(i));
                adapterList.get(i).notifyDataSetChanged();
            }
        }

        mainLayout.removeAllViews();

        for (int i = 0; i < categoryProductsFilteredList.size(); i++) {
            if (categoryProductsFilteredList.get(i).size() > 0) {
                TextView textView = new TextView(getActivity());
                textView.setText(ProductsActivity._inst.categoryList.get(i));
                textView.setTextSize(20);
                textView.setTypeface(Typeface.DEFAULT_BOLD);

                RelativeLayout relativeLayout = new RelativeLayout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                relativeLayout.setLayoutParams(params);
                relativeLayout.setPadding(ConvertDpPx.dpToPx(20), ConvertDpPx.dpToPx(12), ConvertDpPx.dpToPx(20), ConvertDpPx.dpToPx(8));
                relativeLayout.addView(textView);

                mainLayout.addView(relativeLayout);
                mainLayout.addView(rvList.get(i));
            }
        }
    }

    private void sortProducts() {
        Comparator<Product> comparator = new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {

                return lhs.getCategory().get(0).getName().compareTo(rhs.getCategory().get(0).getName());

            }
        };

        Collections.sort(productsFiltered, comparator);
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

        dataSetChanged();

        // sorting item3
        for (int i = 0; i < adapterList.size(); i++) {
            adapterList.get(i).notifyDataSetChanged();
        }
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
