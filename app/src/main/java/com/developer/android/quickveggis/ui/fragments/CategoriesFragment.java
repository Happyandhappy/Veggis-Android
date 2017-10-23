package com.developer.android.quickveggis.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Category;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.model.ProductBanner;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.ProductActivity;
import com.developer.android.quickveggis.ui.activity.ProductsActivity;
import com.developer.android.quickveggis.ui.activity.SearchActivity;
import com.developer.android.quickveggis.ui.utils.FadeAnim;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.freshdesk.hotline.Hotline;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.developer.android.quickveggis.App.categories;
import static com.developer.android.quickveggis.App.launched;

public class CategoriesFragment extends Fragment implements MainActivity.MenuController {
    public CategoryAdapter adapter;
//    public FragmentStatePagerAdapter adapterBanner;
    public ViewPagerAdapter adapterBanner;
    public ArrayList<ProductBanner> bannerProducts;
    List<Bundle> search_data;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.myPager)
    ViewPager mPager;
    @Bind(R.id.searchLayout)
    RelativeLayout searchLayout;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.firstName)
    TextView firstName;
    @Bind(R.id.helloLayout)
    LinearLayout helloLayout;

    Handler mHandler;
    int updateInterval = 5000;

    public static CategoriesFragment mFragment;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.CategoriesFragment.1 */
    class C05621 implements RecyclerItemClickListener.OnItemClickListener {
        C05621() {
        }

        public void onItemClick(View view, int position) {
            if (position < 0 || position >= categories.size())      return;

            Bundle bundle = new Bundle();
            bundle.putString("param", new Gson().toJson(categories.get(position)));

            ArrayList<String> categoryList = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                categoryList.add(categories.get(i).getName());
            }

            Intent intent = new Intent(getActivity(), ProductsActivity.class);
            intent.putExtras(bundle);
            intent.putStringArrayListExtra("category-list", categoryList);
            intent.putExtra("index", position);

            getActivity().startActivity(intent);
        }
    }

    public void moveIntoSupportFragment() {
//        FragmentUtils.changeFragment(CategoriesFragment.this.getActivity(), (int) R.id.content, SupportListFragment.newInstance(), true);
        Hotline.showConversations(CategoriesFragment.this.getActivity());
    }

    public void moveIntoNotificationFragment() {
        FragmentUtils.changeFragment(CategoriesFragment.this.getActivity(), (int) R.id.content, NotificationListFragment.newInstance(), false);
//        FragmentUtils.changeFragment(CategoriesFragment.this.getActivity(), (int) R.id.content, SupportListFragment.newInstance(), true);
    }

    public void moveIntoCartFragment() {
        FragmentUtils.changeFragment(CategoriesFragment.this.getActivity(), (int) R.id.content, SupportListFragment.newInstance(), true);
    }

    public class CategoryAdapter extends Adapter<CategoryAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.imgIcon)
            ImageView imgIcon;
            @Bind(R.id.txtTitle)
            TextView txtTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(mFragment.getContext()).inflate(R.layout.item_category, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            Category category = categories.get(position);
            holder.txtTitle.setText(category.getName());

            Picasso.with(mFragment.getContext()).load(category.getImage().replace(" ", "%20")).into(holder.imgIcon);
        }

        public int getItemCount() {
            return categories.size();
        }
    }

//    public class BannerAdapter extends Adapter<BannerAdapter.Holder> {
//
//        public class Holder extends ViewHolder {
//            @Bind(R.id.img)
//            ImageView img;
//
//            public Holder(View itemView) {
//                super(itemView);
//                ButterKnife.bind(this, itemView);
//            }
//        }
//
//        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new Holder(LayoutInflater.from(CategoriesFragment.this.getContext()).inflate(R.layout.fragment_categorydetail, parent, false));
//        }
//
//        public void onBindViewHolder(Holder holder, int position) {
//            ProductBanner category = CategoriesFragment.this.categories.get(position);
//            Picasso.with(getActivity()).load(category.getImage()).into(holder.img);
//        }
//
//        public int getItemCount() {
//            return CategoriesFragment.this.categories.size();
//        }
//    }

    public CategoriesFragment() {
        this.search_data = new ArrayList();
        this.bannerProducts = new ArrayList<>();
    }

    public static CategoriesFragment newInstance() {
        Bundle args = new Bundle();
        mFragment = new CategoriesFragment();
        mFragment.setArguments(args);

        return mFragment;
    }

//    public void setFragment() {
//        ((MainActivity) getActivity()).setCategoryFragment(this);
//    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);
        if (getActivity() == null) {
            mPager.getAdapter().notifyDataSetChanged();
        }
        return view;
    }

    public void showGreetings() {
        if (!launched) {
            Customer customer = CustomerController.getInstance().getLoggedInCustomer();

            if (customer.getFirstname() != null) {
                String userName = String.format("%s!", customer.getFirstname());
                firstName.setText(userName);
                helloLayout.setVisibility(View.VISIBLE);
            }

            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
//                    helloLayout.setVisibility(View.GONE);
                    FadeAnim.startSlideAnim(helloLayout, false);
                }
            };

            handler.sendEmptyMessageDelayed(0, 2000);

            launched = true;
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        MainActivity activity = (MainActivity) getActivity();
        activity.setTitle(R.string.kikbac);

        this.adapter = new CategoryAdapter();
//        this.adapterBanner = getPagerAdapter(activity.getSupportFragmentManager());
        this.adapterBanner = new ViewPagerAdapter(getActivity().getApplicationContext());
        this.rv.setNestedScrollingEnabled(false);
        this.rv.setHasFixedSize(false);
        this.rv.setAdapter(this.adapter);
        this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05621()));

        if (categories.size() > 0) {
            adapter.notifyDataSetChanged();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ServiceAPI.newInstance().getCategories(new ResponseCallback<ArrayList<Category>>() {
                @Override
                public void onSuccess(ArrayList<Category> data) {
                    progressDialog.dismiss();
                    categories.addAll(data);
                    adapter.notifyDataSetChanged();

                    showGreetings();
                }

                @Override
                public void onFailure(String error) {
                    progressDialog.dismiss();
                }
            });
        }

        mPager.setAdapter(adapterBanner);

        //  Banner
        if (bannerProducts.size() > 0) {
            for (int i = bannerProducts.size() - 1; i > -1; i--) {
                ProductBanner productBanner = bannerProducts.get(i);
                if (productBanner.getProductInfo().getBanner() == null) {
                    bannerProducts.remove(i);
                    continue;
                }
                productBanner.getProductInfo().setImage(productBanner.getProductInfo().getImage().replace(" ", "%20"));
                productBanner.getProductInfo().setBanner(productBanner.getProductInfo().getBanner().replace(" ", "%20"));
            }
            adapterBanner.notifyDataSetChanged();
        } else {
            ServiceAPI.newInstance().getBannerProducts(new ResponseCallback<ArrayList<ProductBanner>>() {
                @Override
                public void onSuccess(ArrayList<ProductBanner> data) {

                    bannerProducts.addAll(data);
                    for (int i = bannerProducts.size() - 1; i > -1; i--) {
                        ProductBanner productBanner = bannerProducts.get(i);
                        if (productBanner.getProductInfo().getBanner() == null) {
                            bannerProducts.remove(i);
                            continue;
                        }
                        productBanner.getProductInfo().setImage(productBanner.getProductInfo().getImage().replace(" ", "%20"));
                        productBanner.getProductInfo().setBanner(productBanner.getProductInfo().getBanner().replace(" ", "%20"));
                    }

                    adapterBanner.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String error) {
                }
            });
        }

        // Search
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);

                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mPager.getCurrentItem() == mPager.getChildCount()) {
                    mPager.setCurrentItem(0);
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }
                mHandler.sendEmptyMessageDelayed(0, updateInterval);
            }
        };

        mHandler.sendEmptyMessageDelayed(0, updateInterval);
    }

    @Override
    public void onPause() {
        super.onPause();

        mHandler.removeMessages(0);
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private Context mContext;

        public ViewPagerAdapter( Context context ) {
            mContext = context;
        }

        public int getCount() {
            return bannerProducts.size();
        }

        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(mContext) ;
            if (bannerProducts.get(position).getProductInfo().getBanner() != null ) {
                Picasso.with(getActivity()).load(bannerProducts.get(position).getProductInfo().getBanner().replace(" ", "%20")).fit().centerCrop().into(imageView);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.getInstance().mDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentUtils.changeFragment(getActivity(), R.id.content, ProductFragment.newInstance(bannerProducts.get(position).getProductInfo()), false);
                }
            });

            container.addView(imageView, 0);
            return imageView;
        }

        /**
         * Called to remove the page
         */
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View)view);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

    }

    private FragmentStatePagerAdapter getPagerAdapter(FragmentManager fm) { // this must be upgrade with apis
        FragmentStatePagerAdapter adp = new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
//                Fragment frgment = null;
//                switch (position) {
//                    case 0:
//                        frgment = new CategoryDetailPagerFragment();
//                        break;
//                    case 1:
//                        frgment = new CategoryDetailPagerFragment2();
//                        break;
//                    default:
//                        frgment = new CategoryDetailPagerFragment();
//                        break;
//                }
                Fragment frgment = new CategoryDetailPagerFragment(bannerProducts.get(position));

                return frgment;
            }

            @Override
            public int getCount() {

//                return 5;
                return bannerProducts.size();
            }
        };
        return adp;
    }

    public void moveIntoProductDetail(int position) {
        Intent intent = ProductActivity.getStartIntent(getContext());
        intent.putExtra("item", position);

        Bundle bundle = new Bundle();
        //      bundle.putString("product", new Gson().toJson(products.get(position)));
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).setExpanded(false);
    }

    public int getMenuVisibility() {
        return 0;
    }

    public class MySearchAdapter extends ArrayAdapter<String> {

        private LayoutInflater mInflater = null;
        private Activity activity;

        public MySearchAdapter(Activity a, String[] items) {
            super(a, 0, items);
            activity = a;
//            mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInflater = LayoutInflater.from(activity);
        }

        public class ViewHolder {
            public TextView name;
            public TextView category;
            public TextView size;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_search_result,
                        parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.txtName);
                holder.category = (TextView) convertView.findViewById(R.id.txtCategory);
                holder.size = (TextView) convertView.findViewById(R.id.txtSize);

                Bundle dta = search_data.get(0);
                holder.name.setText(dta.getString("name"));
                holder.category.setText(dta.getString("variety"));
                holder.size.setText(dta.getString("size"));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }
    }

}
