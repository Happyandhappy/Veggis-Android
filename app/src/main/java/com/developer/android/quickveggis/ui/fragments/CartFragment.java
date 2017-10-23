package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.CartItem;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.OfflineRedeemActivity;
import com.developer.android.quickveggis.ui.activity.OrderActivity;
import com.developer.android.quickveggis.ui.adapter.SimpleSectionedRecyclerViewAdapter;
import com.developer.android.quickveggis.ui.utils.FadeAnim;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.PreferenceUtil;
import com.developer.android.quickveggis.ui.utils.PriceFormat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.developer.android.quickveggis.App.shoppingListChanged;
import static com.developer.android.quickveggis.ui.activity.MainActivity.MENU_EDIT_VISIBLE;
import static com.developer.android.quickveggis.ui.utils.FadeAnim.startFadeInAnim;

public class CartFragment extends Fragment implements MainActivity.MenuController {
    public static int MODE_OFFLINE;
    public static int MODE_WE_DELIVER;
    static ProductAdapter adapter;
    SimpleSectionedRecyclerViewAdapter mSectionedAdapter;
    List<SimpleSectionedRecyclerViewAdapter.Section> sections;
    //CartListAdapter adapter;
    String tempCategoryName = "";
    @Bind(R.id.blockCartTotal)
    View blockCartTotal;

    @Bind(R.id.header)
    View blockHeader;

    @Bind(R.id.blockShippingTotal)
    View blockShippingTotal;

    @Bind(R.id.btnNext)
    Button btnNext;
    Map<CartItem, Integer> counts;

    float deliveryPrice;
    @Bind(R.id.imgSponsor)
    ImageView imgSponsor;

    int mode;
    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.totalLabel)
    TextView totalLabel;

    @Bind(R.id.txtCartTotal)
    TextView txtCartTotal;

    @Bind(R.id.txtShipping)
    TextView txtShipping;

    @Bind(R.id.txtTotal)
    TextView txtTotal;

    @Bind(R.id.fullfilled)
    TextView txtFull;

    @Bind(R.id.imgHandle)
    ImageView imgHandle;

    @Bind(R.id.btnTop)
    View btnTop;

    public static AbsoluteLayout absoluteLayout;

    @Bind(R.id.addFromFavoritesLayout)
    RelativeLayout addFromFavoritesLayout;

    @Bind(R.id.empty_page)
    LinearLayout emptyPage;

    @Bind(R.id.unlockedImageLayout)
    LinearLayout unlockedImageLayout;

    @Bind(R.id.tutorialLayout)
    RelativeLayout tutorialLayout;

    ArrayList<CartItem> data = new ArrayList<>();
    ProgressDialog pDialog;
    PtrClassicFrameLayout mPtrFrame;
    boolean startMove = false;
    boolean startAnim = false;
    float mStartY = 0.0f;

    public static CartFragment mFragment;

//    List<CartProductIdentifier> list_productIdentifier = new ArrayList<>();

    public class ProductAdapter extends Adapter<ProductAdapter.Holder> {

        boolean mEditList = false;

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.CartFragment.ProductAdapter.1 */
        class C02901 implements OnClickListener {
            final /* synthetic */ CartItem val$product;

            C02901(CartItem cartItem) {
                this.val$product = cartItem;
            }

            public void onClick(View v) {
                CartFragment.this.onMinusCount(this.val$product);
            }
        }

        /* renamed from: com.quickveggies.quickveggies.ui.fragment.CartFragment.ProductAdapter.2 */
        class C02912 implements OnClickListener {
            final /* synthetic */ CartItem val$product;

            C02912(CartItem product) {
                this.val$product = product;
            }

            public void onClick(View v) {
                CartFragment.this.onAddCount(this.val$product);
            }
        }

        public class ProductItemClickListener implements OnClickListener {
            @Override
            public void onClick(View v) {
//                int itemPosion = CartFragment.this.rv.getChildLayoutPosition(v);
//                CartItem product = CartFragment.this.data.get(itemPosion);
//                CartItem sproduct = CartFragment.this.data.get(itemPosion);
//                data.get(itemPosion).setSelected(!data.get(itemPosion).isSelected());
                //                Config.list_Identifiers.get(itemPosion).setIsSelected(!Config.list_Identifiers.get(itemPosion).isSelected);
//                notifyDataSetChanged();
            }
        }


        public class Holder extends ViewHolder {
            @Bind(R.id.blockCount)
            View blockCount;

            @Bind(R.id.imgIcon)
            ImageView imgIcon;

            @Bind(R.id.txtCount)
            TextView txtCount;

            @Bind(R.id.txtMinus)
            TextView txtMinus;

            @Bind(R.id.txtName)
            TextView txtName;

            @Bind(R.id.txtPlus)
            TextView txtPlus;

            @Bind(R.id.txtPrice)
            TextView txtPrice;

            @Bind(R.id.txt_brand_value)
            TextView txtBrand;

            @Bind(R.id.txt_quantity_value)
            TextView txtQuantity;

            @Bind(R.id.txt_brand_title)
            TextView txtBrandLabel;

            @Bind(R.id.txt_category_value)
            TextView txtCategoryLabel;

            @Bind(R.id.txt_quantity_title)
            TextView txtQuantityLabel;

            @Bind(R.id.btn_unselect_product)
            ImageButton btnDeselectProduct;

            @Bind(R.id.btn_select_product)
            ImageButton btnSelectProduct;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (mode == MODE_WE_DELIVER) {
                view = LayoutInflater.from(CartFragment.this.getContext()).inflate(R.layout.item_cart_item, parent, false);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_item_new, parent, false);
            }
            view.setOnClickListener(new ProductItemClickListener());
            return new Holder(view);
        }

        public void onBindViewHolder(Holder holder, final int position) {
            final CartItem cartItem = CartFragment.this.data.get(position);
//            Picasso.with(CartFragment.this.getContext()).load(product.getImage()).fit().centerInside().into(holder.imgIcon);

            //for test
            //holder.imgIcon.setImageResource(product.getImageId());
//            holder.imgIcon.setImageResource(R.drawable.product_nivea_cart);
            Picasso.with(CartFragment.this.getContext()).load(cartItem.getImage()).fit().centerInside().into(holder.imgIcon);

//            holder.txtPrice.setText(PriceFormat.format(cartItem.getPrice() * Integer.parseInt(cartItem.getQuantity())));
//            holder.txtPrice.setText(PriceFormat.format(Float.parseFloat(cartItem.getTaskInfo().get(0).getTaskAmount())));

            float totalPrice = 0;
            for (int i = 0; i < cartItem.getTaskInfo().size(); i++) {
                float tempFlo = Float.parseFloat(cartItem.getTaskInfo().get(i).getTaskAmount());
                totalPrice = totalPrice + tempFlo;
            }
            holder.txtPrice.setText(PriceFormat.format(totalPrice));

            holder.txtName.setText(cartItem.getName());
            holder.txtCategoryLabel.setText(String.valueOf(cartItem.getWeight()));
            if (mode == MODE_OFFLINE) {
                //holder.txtWeight.setText(product.getDescription());
                holder.txtQuantity.setText(cartItem.getQuantity());
                if (cartItem.isSelected()) {
                    holder.btnSelectProduct.setBackgroundResource(R.drawable.ic_checker_pressed);
                    holder.btnDeselectProduct.setBackgroundResource(R.drawable.ic_uncheck_unpressed);
                } else {
                    holder.btnSelectProduct.setBackgroundResource(R.drawable.ic_checker_unpressed);
                    holder.btnDeselectProduct.setBackgroundResource(R.drawable.ic_uncheck_unpressed);
                }
            }
            if (CartFragment.this.mode == CartFragment.MODE_WE_DELIVER) {
                holder.txtCount.setText(String.valueOf(CartFragment.this.counts.get(cartItem)));
                holder.txtMinus.setOnClickListener(new C02901(cartItem));
                holder.txtPlus.setOnClickListener(new C02912(cartItem));
                //holder.txtWeight.setText("500g");
                return;
            } else {
                //in case use taps cancel button("x")
                holder.btnDeselectProduct.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // To delete the card selected
                        confirmAlertView(cartItem);
                    }
                });

                holder.btnSelectProduct.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartItem.setSelected(!cartItem.isSelected());
                        notifyDataSetChanged();
                    }
                });
            }
            holder.blockCount.setVisibility(View.GONE);

            if (mEditList) {
                holder.btnDeselectProduct.setVisibility(View.VISIBLE);
            } else {
                holder.btnDeselectProduct.setVisibility(View.GONE);
            }
        }

        public int getItemCount() {
            return CartFragment.this.data.size();
        }

//        public boolean doesSectionHaveHeader(int sectionIndex) {
//            return true;
//        }

        public void setEditable(boolean editable) {
            mEditList = editable;
        }

    }

    void confirmAlertView(final CartItem cartItem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // set title
        alertDialogBuilder.setTitle("Confirm");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to delete Cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        ServiceAPI.newInstance().deleteCart(cartItem.getProductId(), new ResponseCallback<String>() {
                            @Override
                            public void onSuccess(String respoonse) {

//                                DialogUtils.showAlertDialog(getActivity(), "You have delveted the product from this cart list successfully.");
                                Toast.makeText(getActivity(), "Product has beed deleted from the list", Toast.LENGTH_SHORT).show();
                                CartFragment.this.data.remove(cartItem);
                                updateProducts();
                                adapter.notifyDataSetChanged();
                                shoppingListChanged = true;
                            }

                            @Override
                            public void onFailure(String error) {
                                pDialog.dismiss();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public CartFragment() {
        this.deliveryPrice = 20.0f;
        this.data = new ArrayList();
        this.counts = new HashMap();
    }

    static {
        MODE_WE_DELIVER = 1;
        MODE_OFFLINE = 2;
    }

    public static CartFragment newInstance(int mode) {
        Bundle args = new Bundle();
        args.putInt("mode", mode);
        mFragment = new CartFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);

        absoluteLayout = (AbsoluteLayout) view.findViewById(R.id.absoluteLayout);
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.store_house_ptr_frame);

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                boolean ret = PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                if (ret) {
                    if (!startAnim && addFromFavoritesLayout.getVisibility() == View.GONE) {
                        FadeAnim.startSlideAnim(addFromFavoritesLayout, true);
                    }
                }
                return ret;
            }
        });

        this.rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        if (!startMove) {
                            mStartY = event.getY();
                            startMove = true;
                        } else {
                            if (mStartY - event.getY() > 100) {
                                if (!startAnim && addFromFavoritesLayout.getVisibility() == View.VISIBLE) {
                                    FadeAnim.startSlideAnim(addFromFavoritesLayout, false);
                                    startAnim = true;
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        startMove = false;
                        startAnim = false;
                        break;
                }
                return false;
            }
        });

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mode = getArguments().getInt("mode");

        tutorialLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        //this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        this.rv.setLayoutManager(llm);

        this.adapter = new ProductAdapter();
        //this.adapter = new CartListAdapter(CartFragment.this.getActivity(), this.data, this.list_productIdentifier, MODE_OFFLINE);
        this.rv.setAdapter(this.adapter);
//        This is the code to provide a sectioned list
        sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        updatePrice();

        if (shoppingListChanged) {
            getCartItems();
        } else {
            if (data.size() > 0) {
                emptyPage.setVisibility(View.GONE);
            } else {
                unlockedImageLayout.setVisibility(View.VISIBLE);
            }
            updateProducts();
            adapter.notifyDataSetChanged();
        }

//        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_cart)).build());
        if (this.mode == MODE_WE_DELIVER) {
//            Picasso.with(getContext()).load("http://cdn.corporate.walmart.com/resource/assets-bsp3/images/corp/walmart-logo.64968e7648c4bbc87f823a1eff1d6bc7.png").fit().centerInside().into(this.imgSponsor);
            Picasso.with(getContext()).load("http://cdn.corporate.walmart.com/resource/assets-bsp3/images/corp/walmart-logo.64968e7648c4bbc87f823a1eff1d6bc7.png").fit().centerInside().into(this.imgSponsor);

            getActivity().setTitle(R.string.we_deliver);
            this.btnNext.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(OrderActivity.getStartIntent(CartFragment.this.getContext()));
                }
            });
        } else {
            this.deliveryPrice = 0.0f;
            this.blockCartTotal.setVisibility(View.GONE);
            this.blockShippingTotal.setVisibility(View.GONE);
            this.totalLabel.setText(R.string.total_savings);
            this.blockHeader.setVisibility(View.VISIBLE);
            this.txtFull.setTextColor(getResources().getColor(R.color.mainGreen));
            this.txtFull.setText(R.string.cart_desc);
            this.imgHandle.setImageResource(R.drawable.img_handle);
            this.btnTop.setVisibility(View.GONE);
            getActivity().setTitle(R.string.cart);
            this.btnNext.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(OfflineRedeemActivity.getStartIntent(CartFragment.this.getContext()));
//                    FragmentUtils.changeFragment(getActivity(), R.id.content, SubmitFragment.newInstance(null, null), false);
                }
            });
            this.blockHeader.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUtils.changeFragment(getActivity(), R.id.content, CartFragment.newInstance(1), false);//2 = offlinemode
                }
            });

//            ((MainActivity)getActivity()).txtOffline.setVisibility(View.VISIBLE);
        }

        addFromFavoritesLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.popBackStack(getActivity());
                FragmentUtils.changeFragment(getActivity(), R.id.content, WishlistFragment.newInstance(), false);
            }
        });

    }

    void getCartItems() {

        ServiceAPI.newInstance().getCartItems(new ResponseCallback<ArrayList<CartItem>>() {
            @Override
            public void onSuccess(ArrayList<CartItem> data) {
                CartFragment.this.data.clear();
                if (data.size() > 0) {
                    emptyPage.setVisibility(View.GONE);
                    startFadeInAnim(rv);
                } else {
                    unlockedImageLayout.setVisibility(View.VISIBLE);
                }
                CartFragment.this.data.addAll(data);
                updateProducts();
                adapter.notifyDataSetChanged();
                shoppingListChanged = false;

                if (PreferenceUtil.getBooleanFromPreference(getActivity(), Config.CART_TUTORIAL_VISIBLE, true)) {
                    tutorialLayout.setVisibility(View.VISIBLE);
                    PreferenceUtil.saveBooleanToPreference(getActivity(), Config.CART_TUTORIAL_VISIBLE, false);
                } else {
                    tutorialLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String error) {
                unlockedImageLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortProducts() {
        Comparator<CartItem> comparator = new Comparator<CartItem>() {
            @Override
            public int compare(CartItem lhs, CartItem rhs) {

                return lhs.getCategoryInfo().get(0).getCategoryname().compareTo(rhs.getCategoryInfo().get(0).getCategoryname());

            }
        };

        Collections.sort(this.data, comparator);
    }

    private String decode(String url) {
        return url.replace("&amp;", "&");
    }

    private void updateProducts() {

        //      this.data = TestData.getCartProducts();
        for (CartItem cartItem : this.data) {
            this.counts.put(cartItem, Integer.valueOf(1));
            cartItem.getCategoryInfo().get(0).setCategoryname(this.decode(cartItem.getCategoryInfo().get(0).getCategoryname()));
        }

        this.sortProducts();

        float finalPrice = 0.0f;
        for (int i = 0; i < this.data.size(); i++) {
            int count = Integer.parseInt(data.get(i).getQuantity());

            if (count > 0) {
//                finalPrice = (float) (finalPrice + (((double) count) * (Double.parseDouble(data.get(i).getTaskInfo().get(0).getTaskAmount()))));

                float tempPrice = 0.0f;
                for (int j = 0; j < data.get(i).getTaskInfo().size(); j++) {
                    tempPrice = (float) (Double.parseDouble(data.get(i).getTaskInfo().get(j).getTaskAmount()));
                    finalPrice = finalPrice + tempPrice;
                }
            }
        }
        this.txtTotal.setText(PriceFormat.format(finalPrice));
        addSections();
    }

    private void addSections() {
        this.sections.clear();
        this.tempCategoryName = "";
        for (int i = 0; i < this.data.size(); i++) {
            final CartItem cartItem = CartFragment.this.data.get(i);
            String categoryName = cartItem.getCategoryInfo().get(0).getCategoryname();
            if (!this.tempCategoryName.equals(categoryName)) {
                this.tempCategoryName = categoryName;
                this.sections.add(new SimpleSectionedRecyclerViewAdapter.Section(i, this.tempCategoryName));
            }
        }

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(this.getContext(), R.layout.section, R.id.section_text, adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

//Apply this adapter to the RecyclerView
        rv.setAdapter(mSectionedAdapter);
    }

    private void updatePrice() {
        float finalPrice = 0.0f;
//        for (Entry<CartItem, Integer> entry : this.counts.entrySet()) {
//            int count = (entry.getValue()).intValue();
//            if (count > 0) {
//                finalPrice = (float) (((double) finalPrice) + (((double) count) * ((CartItem) entry.getKey()).getPrice()));
//            }
//        }

        this.txtCartTotal.setText(PriceFormat.format(finalPrice));
        this.txtShipping.setText(PriceFormat.format(this.deliveryPrice));
        this.txtTotal.setText(PriceFormat.format(this.deliveryPrice + finalPrice));
    }

    public void onMinusCount(CartItem cartItem) {
        Integer count = Integer.parseInt(cartItem.getQuantity());
        onChangeCount(cartItem, count - 1);
    }

    public void onAddCount(CartItem cartItem) {
        Integer count = Integer.parseInt(cartItem.getQuantity());
        onChangeCount(cartItem, count + 1);
    }

    public void onChangeCount(CartItem cartItem, int count) {
        cartItem.setQuantity(Integer.toString(count));
        this.adapter.notifyDataSetChanged();
        updatePrice();
    }

    @Override
    public int getMenuVisibility() {
        return MENU_EDIT_VISIBLE;
    }

    public static void editShopList(boolean editable) {
        adapter.setEditable(editable);
        adapter.notifyDataSetChanged();
        if (editable) {
            absoluteLayout.setVisibility(View.GONE);
        } else {
            absoluteLayout.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<float[]> getPointList() {
        // this point is taken from https://github.com/cloay/CRefreshLayout
        List<Point> startPoints = new ArrayList<Point>();
        startPoints.add(new Point(240, 80));
        startPoints.add(new Point(270, 80));
        startPoints.add(new Point(265, 103));
        startPoints.add(new Point(255, 65));
        startPoints.add(new Point(275, 80));
        startPoints.add(new Point(275, 80));
        startPoints.add(new Point(302, 80));
        startPoints.add(new Point(275, 107));

        startPoints.add(new Point(320, 70));
        startPoints.add(new Point(313, 80));
        startPoints.add(new Point(330, 63));
        startPoints.add(new Point(315, 87));
        startPoints.add(new Point(330, 80));
        startPoints.add(new Point(315, 100));
        startPoints.add(new Point(330, 90));
        startPoints.add(new Point(315, 110));
        startPoints.add(new Point(345, 65));
        startPoints.add(new Point(357, 67));
        startPoints.add(new Point(363, 103));

        startPoints.add(new Point(375, 80));
        startPoints.add(new Point(375, 80));
        startPoints.add(new Point(425, 80));
        startPoints.add(new Point(380, 95));
        startPoints.add(new Point(400, 63));

        List<Point> endPoints = new ArrayList<Point>();
        endPoints.add(new Point(270, 80));
        endPoints.add(new Point(270, 110));
        endPoints.add(new Point(270, 110));
        endPoints.add(new Point(250, 110));
        endPoints.add(new Point(275, 107));
        endPoints.add(new Point(302, 80));
        endPoints.add(new Point(302, 107));
        endPoints.add(new Point(302, 107));

        endPoints.add(new Point(340, 70));
        endPoints.add(new Point(360, 80));
        endPoints.add(new Point(330, 80));
        endPoints.add(new Point(340, 87));
        endPoints.add(new Point(315, 100));
        endPoints.add(new Point(345, 98));
        endPoints.add(new Point(330, 120));
        endPoints.add(new Point(345, 108));
        endPoints.add(new Point(360, 120));
        endPoints.add(new Point(363, 75));
        endPoints.add(new Point(345, 117));

        endPoints.add(new Point(380, 95));
        endPoints.add(new Point(425, 80));
        endPoints.add(new Point(420, 95));
        endPoints.add(new Point(420, 95));
        endPoints.add(new Point(400, 120));
        ArrayList<float[]> list = new ArrayList<float[]>();

        int offsetX = Integer.MAX_VALUE;
        int offsetY = Integer.MAX_VALUE;

        for (int i = 0; i < startPoints.size(); i++) {
            offsetX = Math.min(startPoints.get(i).x, offsetX);
            offsetY = Math.min(startPoints.get(i).y, offsetY);
        }
        for (int i = 0; i < endPoints.size(); i++) {
            float[] point = new float[4];
            point[0] = startPoints.get(i).x - offsetX;
            point[1] = startPoints.get(i).y - offsetY;
            point[2] = endPoints.get(i).x - offsetX;
            point[3] = endPoints.get(i).y - offsetY;
            list.add(point);
        }
        return list;
    }
}
