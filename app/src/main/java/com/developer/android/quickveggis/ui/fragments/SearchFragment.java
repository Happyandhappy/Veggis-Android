package com.developer.android.quickveggis.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.db.HistoryRepo;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.adapter.SearchProductAdapter;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.google.gson.Gson;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;

public class SearchFragment extends Fragment implements MainActivity.MenuController{
    SearchProductAdapter adapterSearch;
    SimpleAdapter adapterHistory;
    ArrayList<Product> dataSearch;
    ArrayList<String> dataHistory;
    StickyRecyclerHeadersDecoration decoration;

    @Bind(R.id.searchView)
    AutoCompleteTextView searchTextView;
    @Bind(R.id.rv_search)
    RecyclerView rvSearch;
    @Bind(R.id.rv_history)
    RecyclerView rvHistory;
    @Bind(R.id.emptyLayout)
    LinearLayout emptyLayout;
    @Bind(R.id.mainLayout)
    LinearLayout mainLayout;
    int type;

    class C05681 implements RecyclerItemClickListener.OnItemClickListener {
        C05681() {
        }

        public void onItemClick(View view, int position) {
            if (position < 0 && position >= dataSearch.size())    return;
            Intent intent = MainActivity.getStartIntent(getContext(), Config.PRODUCT_MODE);
            intent.putExtra("item", position);

            Bundle bundle = new Bundle();
            bundle.putString("product", new Gson().toJson(dataSearch.get(position)));
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);

        }
    }

    class C05651 implements RecyclerItemClickListener.OnItemClickListener {
        C05651() {
        }

        public void onItemClick(View view, int position) {
            FragmentUtils.changeFragment(SearchFragment.this.getActivity(), (int) R.id.content, ProductFragment.newInstance(), true);
        }
    }

    public SearchFragment() {
        this.dataSearch = new ArrayList();
        this.dataHistory = new ArrayList<>();
    }

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        dataSearch.clear();
        dataHistory.clear();

        dataHistory.addAll(HistoryRepo.getInstance().getHistorySearchKey());

        this.rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapterSearch = new SearchProductAdapter(getContext(),  this.dataSearch);
        this.rvSearch.setAdapter(this.adapterSearch);
        this.rvSearch.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05681()));

        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (searchTextView.getText().toString().endsWith("")) {
                    rvSearch.setVisibility(View.GONE);
//                    rvHistory.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rvSearch.setVisibility(View.GONE);
                rvHistory.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                searchText(searchTextView.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchTextView.getText().toString().endsWith("")) {
                    rvSearch.setVisibility(View.GONE);
//                    rvHistory.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void searchText(String string) {

        ServiceAPI.newInstance().searchProducts(string, new ResponseCallback<ArrayList<Product>>() {
            @Override
            public void onSuccess(ArrayList<Product> data) {

                if (data.size() > 0) {
                    emptyLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    startFadeInAnim(rvSearch);
                }

                dataSearch.clear();

                dataSearch.addAll(data);
                adapterSearch.setProducts(dataSearch);

                rvSearch.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
                adapterSearch.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeSearchHistory(ArrayList<String> arrayList) {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        String strHistory = arrayList.toString().replace("[", "")
                .replace("]", "").replace(", ", ",");

        editor.putString("product.search.history", strHistory);

        editor.commit();
    }

    private ArrayList<String> getSearchHistory() {
        ArrayList<String> searchHistory = new ArrayList<String >();

        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);

        String strHistory = preferences.getString("product.search.history", "");

        searchHistory = new ArrayList<String>(Arrays.asList(strHistory.split(",")));

        return searchHistory;
    }

    @Override
    public int getMenuVisibility() {
        return 1;
    }
}