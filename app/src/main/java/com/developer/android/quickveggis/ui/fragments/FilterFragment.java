package com.developer.android.quickveggis.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.db.HistoryRepo;
import com.developer.android.quickveggis.model.Brand;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.ProductsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class FilterFragment extends Fragment {
    FIlterAdapter adapter;
    List<String> brands;

    public ArrayList<String> brandList;

    @Bind(R.id.btnDone)
    View btnDone;
    @Bind(R.id.radioAnswers)
    RadioGroup sortOption1;
    @Bind(R.id.edtText)
    EditText sortOption2;
    @Bind(R.id.searchBrand)
    AutoCompleteTextView    searchBrand;
    @Bind(R.id.list_brand)
    LinearLayout    listbrand;
    @Bind(R.id.btnReset)
    View btnReset;
    @Bind(R.id.rv)
    RecyclerView rv;
    Map<String, Boolean> selected;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.FilterFragment.1 */
    class C02951 implements OnClickListener {
        C02951() {
        }

        public void onClick(View v) {
            FilterFragment.this.reset();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.FilterFragment.2 */
    class C02962 implements OnClickListener {
        C02962() {
        }

        public void onClick(View v) {

            //      iTem - 1 -
            String item1 = "";
            switch (sortOption1.getCheckedRadioButtonId()) {
                case R.id.first:
                    item1 = "1";
                    break;
                case R.id.second:
                    item1 = "2";
                    break;
                case R.id.third:
                    item1 = "3";
                    break;
                case R.id.fourth:
                    item1 = "4";
                    break;
                default:
                    break;
            }
            //      iTem - 2 -
            String item2 = "";
            item2 = sortOption2.getText().toString();
            //      iTem - 3 -
            String item3 = "";

            filter(item1, item2, item3);
        }
    }

    private void filter(String item1, String item2, String item3) {

        // New:  dateAdded = "2016-05-20 12:05:43"
        // Popularity:  popularity="8.00", viewed="53", rating=0, reviews.reviewTotal="0", reward=null
        // Ending soon: dateAvailable= "2016-05-20 12:05:43";
        // SavingHighToLow: TotalTaskAmount="8.00"


        for (int i = 0; i < brands.size(); i ++) {
            String item = (String) FilterFragment.this.brands.get( i );
            boolean isSelected = ((Boolean) FilterFragment.this.selected.get(item)).booleanValue();
            if (isSelected) {
                item3 = item3 + " ," + brands.get(i);
            }
        }

        ((ProductsActivity) FilterFragment.this.getActivity()).doFilte(item1, "", item3);

        ((ProductsActivity) FilterFragment.this.getActivity()).toggleMenu();
    }

    public class FIlterAdapter extends Adapter<FIlterAdapter.Holder> {

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.FilterFragment.FIlterAdapter.1 */
        class C02971 implements OnClickListener {
            final /* synthetic */ boolean val$isSelected;
            final /* synthetic */ String val$item;

            C02971(String str, boolean z) {
                this.val$item = str;
                this.val$isSelected = z;
            }

            public void onClick(View v) {
                FilterFragment.this.updateSelected(this.val$item, !this.val$isSelected);
            }
        }

        public class Holder extends ViewHolder {
            @Bind(R.id.text)
            TextView text;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_filter, parent, false);
//            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            itemView.setLayoutParams(lp);
            return new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.item_filter, parent, false));
//            return new Holder(itemView);
        }

        public void onBindViewHolder(Holder holder, int position) {
            String item = (String) FilterFragment.this.brands.get(position);
            boolean isSelected = ((Boolean) FilterFragment.this.selected.get(item)).booleanValue();
            holder.text.setText(item);
            holder.text.setSelected(isSelected);
            holder.text.setOnClickListener(new C02971(item, isSelected));
        }

        public int getItemCount() {
            return FilterFragment.this.brands.size();
        }
    }

    public FilterFragment() {
        this.brands = new ArrayList();
        this.selected = new HashMap();
    }

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        fillBrands();
//        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(ContextCompat.getColor(getContext(), 17170445))).sizeResId(R.dimen.divider_filter_v)).build());
//        this.rv.addItemDecoration(((VerticalDividerItemDecoration.Builder) ((VerticalDividerItemDecoration.Builder) new VerticalDividerItemDecoration.Builder(getActivity()).color(ContextCompat.getColor(getContext(), 17170445))).sizeResId(R.dimen.divider_filter)).build());
        this.adapter = new FIlterAdapter();
        this.btnReset.setOnClickListener(new C02951());
        this.btnDone.setOnClickListener(new C02962());
        this.rv.setAdapter(this.adapter);

        brandList = new ArrayList<String>();

        searchBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (searchBrand.getText().toString().equals("")) {
                    listbrand.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchBrand.getText().toString().equals("")) {
                    listbrand.setVisibility(View.GONE);
                } else {
                    listbrand.setVisibility(View.VISIBLE);
                }
                updateBrandsList(searchBrand.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchBrand.getText().toString().equals("")) {
                    listbrand.setVisibility(View.GONE);
                } else {
                    listbrand.setVisibility(View.VISIBLE);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fillBrands();
                adapter.notifyDataSetChanged();
            }
        });

        this.adapter.notifyDataSetChanged();
    }

    private void updateBrandsList(String strSearch) {

        listbrand.removeAllViews();

        ArrayList<String> arrayTemp = new ArrayList<String>();
        for (String strTemp : brandList  ) {
            if (strTemp.toLowerCase().contains(strSearch.toLowerCase()) ) {
                arrayTemp.add(strTemp);
            }
        }

        for (int i = 0; i < arrayTemp.size(); i ++) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_brand, listbrand, false);
            TextView txtView = (TextView) view.findViewById(R.id.txtTitle);
            txtView.setText(arrayTemp.get(i));

            txtView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    TextView button = (TextView)v;

                    if ( !brands.contains(button.getText().toString()) ) {
                        String tepStr = button.getText().toString();
                        brands.add(0, tepStr);

//                        this.brands = new HistoryRepo(getActivity().getApplicationContext()).getHistoryBrands();
                        new HistoryRepo(getActivity().getApplicationContext()).saveHistoryBrands(brands);

                        selected.put(button.getText().toString(), Boolean.valueOf(true));
                    }

                    searchBrand.setText("");
                    hideKeyboard();

                    adapter.notifyDataSetChanged();

                    listbrand.setVisibility(View.GONE);
                }
            });

            listbrand.addView(view);
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchBrand.getWindowToken(), 0);
    }

    private void reset() {

        sortOption1.setSelected(false);

        for (String item : this.brands) {
            this.selected.put(item, Boolean.valueOf(false));
        }
        this.sortOption2.setText("");
        this.adapter.notifyDataSetChanged();

//        filter("", "", "");
    }

    private void fillBrands() {
        this.brands.clear();
//        this.brands.add("Nestle");
//        this.brands.add("P&G");
//        this.brands.add("Unilever");
//        this.brands.add("Parles");
//        this.brands.add("Patanjali");
//        this.brands.add("Britania");

        this.brands = new HistoryRepo(getActivity().getApplicationContext()).getHistoryBrands();

        for (String item : this.brands) {
            this.selected.put(item, Boolean.valueOf(false));
        }
    }

    private void updateSelected(String item, boolean select) {
        this.selected.put(item, Boolean.valueOf(select));
        this.adapter.notifyDataSetChanged();
    }
}
