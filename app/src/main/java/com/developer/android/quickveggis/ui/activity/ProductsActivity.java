package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Category;
import com.developer.android.quickveggis.ui.fragments.AllProductsFragment;
import com.developer.android.quickveggis.ui.fragments.FilterFragment;
import com.developer.android.quickveggis.ui.fragments.ProductsFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.App.categories;

public class ProductsActivity extends AppCompatActivity {
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnFilter)
    ImageView txtFilter;
    @Bind(R.id.categoryMenuLayout)
    RelativeLayout categoryMenuLayout;
    @Bind(R.id.categoryText)
    TextView categoryText;
    @Bind(R.id.categoryRV)
    RecyclerView categoryRV;
    @Bind(R.id.dimmer)
    ImageView dimmer;

    public Category category;
    public List<String> categoryList = new ArrayList<>();
    int index = 0;

    public static ProductsActivity _inst;

    /* renamed from: com.quickveggies.quickveggies.ui.activity.ProductsActivity.1 */
    class C02651 implements OnClickListener {
        C02651() {
        }

        public void onClick(View v) {
            ProductsActivity.this.toggleMenu();
        }
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ProductsActivity.class);
    }

    ProductsFragment    productsFragment;
    AllProductsFragment    allProductsFragment;
    public FilterFragment      filterFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

//        category = new Gson().fromJson(getIntent().getExtras().getString("param"), Category.class);

        if (getIntent().hasExtra("category-list")) {
            categoryList = getIntent().getStringArrayListExtra("category-list");
        }
        if (getIntent().hasExtra("index")) {
            index = getIntent().getIntExtra("index", 0);
        }
        category = categories.get(index);

        ButterKnife.bind(this);

        if (categoryList.size() > 0) {
            categoryText.setText(categoryList.get(index));
        }

        categoryRV.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 3));
        CategoryAdapter adapter = new CategoryAdapter();
        categoryRV.setAdapter(adapter);

        categoryRV.addOnItemTouchListener(new RecyclerItemClickListener(ProductsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                categoryRV.setVisibility(View.GONE);
                dimmer.setVisibility(View.GONE);

                if (!categoryText.getText().equals(categoryList.get(i))) {
                    categoryText.setText(categoryList.get(i));

                    filterFragment = FilterFragment.newInstance();

                    if (categoryList.get(i).equals(getString(R.string.all))) {
                        allProductsFragment = AllProductsFragment.newInstance(categories.get(i));
                        FragmentUtils.popBackStack(ProductsActivity.this);
                        FragmentUtils.changeFragment(ProductsActivity.this, R.id.content, allProductsFragment, false);
                    } else {
                        productsFragment = ProductsFragment.newInstance(categories.get(i));
                        FragmentUtils.popBackStack(ProductsActivity.this);
                        FragmentUtils.changeFragment(ProductsActivity.this, R.id.content, productsFragment, false);
                    }
                    FragmentUtils.changeFragment(ProductsActivity.this, R.id.filter, filterFragment, false);
                }
            }
        }));

        categoryMenuLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryRV.getVisibility() == View.VISIBLE) {
                    categoryRV.setVisibility(View.GONE);
                    dimmer.setVisibility(View.GONE);
                } else {
                    categoryRV.setVisibility(View.VISIBLE);
                    dimmer.setVisibility(View.VISIBLE);
                }
            }
        });

        filterFragment = FilterFragment.newInstance();

        if (categoryList.get(index).equals(getString(R.string.all))) {
            allProductsFragment = AllProductsFragment.newInstance(categories.get(index));
            FragmentUtils.popBackStack(ProductsActivity.this);
            FragmentUtils.changeFragment(ProductsActivity.this, R.id.content, allProductsFragment, false);
        } else {
            productsFragment = ProductsFragment.newInstance(categories.get(index));
            FragmentUtils.popBackStack(ProductsActivity.this);
            FragmentUtils.changeFragment(ProductsActivity.this, R.id.content, productsFragment, false);
        }
        FragmentUtils.changeFragment(ProductsActivity.this, R.id.filter, filterFragment, false);

        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        this.txtFilter.setOnClickListener(new C02651());

        _inst = this;
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

        public class Holder extends RecyclerView.ViewHolder {
            @Bind(R.id.imgIcon)
            ImageView imgIcon;
            @Bind(R.id.txtTitle)
            TextView txtTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public CategoryAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CategoryAdapter.Holder(LayoutInflater.from(ProductsActivity._inst).inflate(R.layout.item_category, parent, false));
        }

        public void onBindViewHolder(CategoryAdapter.Holder holder, int position) {
            Category category = categories.get(position);
            holder.txtTitle.setText(category.getName());

            Picasso.with(ProductsActivity.this).load(category.getImage().replace(" ", "%20")).into(holder.imgIcon);
        }

        public int getItemCount() {
            return categories.size();
        }
    }

    public void toggleMenu() {
        if (this.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            this.drawerLayout.closeDrawers();
        } else {
            this.drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    public void doFilte(String item1, String item2, String item3) {
        if (categoryText.getText().equals(getString(R.string.all))) {
            allProductsFragment.doFilte(item1, item2, item3);
        } else {
            productsFragment.doFilte(item1, item2, item3);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _inst = null;
    }
}
