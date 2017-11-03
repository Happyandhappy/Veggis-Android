package com.developer.android.quickveggis.ui.fragments;
// Changed by happyandhappy 10/26/2017
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.model.SocialLoginData;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.ProfileMenu;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;
import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    //public static int MENU_LINK_GOOGLE;
    public static int MENU_LINK_FACEBOOK;
    public static int MENU_NOTIFY;
    public static int MENU_PREF;
    public static int MENU_SECURITY;   //// Add by happyandhappy 10/26/2017

    MenuAdapter adapter;
    List<ProfileMenu> data;
    @Bind(R.id.rv)
    RecyclerView rv;

    public class MenuAdapter extends Adapter<MenuAdapter.Holder> {
        static final int TYPE_CHECKABLE = 1;
        static final int TYPE_NORMAL = 2;

        public class Holder extends ViewHolder {
            @Bind(R.id.txtTitle)
            TextView txtTitle;
            @Bind(R.id.arrow)
            ImageView arrow;
            @Bind(R.id.social_state)
            TextView social_state;

            public Holder(View itemView, int viewType) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
             return new Holder(LayoutInflater.from(SettingsFragment.this.getContext()).inflate(R.layout.item_settings_checked, parent, false),viewType);
          }

        public void onBindViewHolder(Holder holder, int position) {
            holder.txtTitle.setText(((ProfileMenu) SettingsFragment.this.data.get(position)).getTitle());
            if (((ProfileMenu) SettingsFragment.this.data.get(position)).getTitle()==R.string.link_facebook){
                holder.arrow.setVisibility(View.GONE);
                if (((ProfileMenu) SettingsFragment.this.data.get(position)).isCheckable()) {
                    holder.social_state.setText("Link");
                    holder.social_state.setTextColor(getResources().getColor(R.color.mainGreen));
                }else{
                    holder.social_state.setText("Unlink");
                    holder.social_state.setTextColor(getResources().getColor(R.color.mainRed));
                }
            }else{
                holder.arrow.setVisibility(View.VISIBLE);
                holder.social_state.setVisibility(View.GONE);
            }
        }

        public int getItemViewType(int position) {
            if (((ProfileMenu) SettingsFragment.this.data.get(position)).isCheckable()) {
                return TYPE_CHECKABLE;
            }
            return TYPE_NORMAL;
        }

        public int getItemCount() {
            return SettingsFragment.this.data.size();
        }
    }

    class mOnItemClickListener implements RecyclerItemClickListener.OnItemClickListener {
        mOnItemClickListener() {

        }

        public void onItemClick(View view, int position) {
            onMenuClick((ProfileMenu) data.get(position));
        }
    }

    public void onMenuClick(ProfileMenu menu) {
        switch (menu.getId()) {
//            case 1  /*link_facebook*/:
//                break;
//            case 2: /*link_google*/
//                break;
            case 3  /*preference*/:
                FragmentUtils.changeFragment(getActivity(), R.id.content, PreferenceFragment.newInstance(), true);
                break;
            case 4: /*Notify*/
                FragmentUtils.changeFragment(getActivity(), R.id.content, NotifyFragment.newInstance(), true);
                break;
            case 5: // Add by happyandhappy 10/26/2017
                FragmentUtils.changeFragment(getActivity(),R.id.content,SecuritySettingsFragment.newInstance(), true);
                break;
            default:break;
        }
    }

    public SettingsFragment() {
        this.data = new ArrayList();
    }

    static {
        MENU_LINK_FACEBOOK = 1;
//        MENU_LINK_GOOGLE = 2;
        MENU_PREF = 3;
        MENU_NOTIFY = 4;
        MENU_SECURITY=5; // Add by happyandhappy 10/26/2017
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.settings);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        fillMenu();
        adapter = new MenuAdapter();
        rv.setAdapter(this.adapter);
        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(ContextCompat.getColor(getContext(), R.color.divider))).sizeResId(R.dimen.dp1)).build());
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new mOnItemClickListener()));
    }

    private void fillMenu() {
        this.data.clear();
/*get social customer data // added by happyandhappy on 11/3/2017*/
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);
        String profileImg = preferences.getString("profileImg","");
        String provider = preferences.getString("provider","");
        SocialLoginData socialdata = new SocialLoginData("", "", provider, profileImg);

        if ( !socialdata.getProvider().contentEquals("") )
            this.data.add(new ProfileMenu(R.string.link_facebook, MENU_LINK_FACEBOOK, true));
        else this.data.add(new ProfileMenu(R.string.link_facebook, MENU_LINK_FACEBOOK, false));

//      this.data.add(new ProfileMenu(R.string.link_google, MENU_LINK_GOOGLE, true));
        this.data.add(new ProfileMenu(R.string.preference, MENU_PREF));
        this.data.add(new ProfileMenu(R.string.notify, MENU_NOTIFY));
        this.data.add(new ProfileMenu(R.string.security_settings,MENU_SECURITY));    // Add by happyandhappy 10/26/2017
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity)getActivity()).btnSave.setVisibility(View.GONE);
    }
}