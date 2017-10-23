package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.ProfileMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment {
    MenuAdapter adapter;
    List<ProfileMenu> data;
    @Bind(R.id.rv)
    RecyclerView rv;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutFragment(){
        data = new ArrayList();
    }

    public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.Holder> {

        public class Holder extends RecyclerView.ViewHolder {
            @Bind(R.id.txtTitle)
            TextView txtTitle;
            @Bind(R.id.txtValue)
            TextView txtValue;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.item_about, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            holder.txtTitle.setText(((ProfileMenu) data.get(position)).getTitle());
            holder.txtValue.setText(((ProfileMenu) data.get(position)).getValue());
        }

        public int getItemCount() {
            return data.size();
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.about);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        fillMenu();
        adapter = new MenuAdapter();
        rv.setAdapter(adapter);
    }

    private void fillMenu() {
        data.clear();
        data.add(new ProfileMenu(R.string.version, 1, "1.0.0.1"));
        data.add(new ProfileMenu(R.string.policy, 2, ""));
        data.add(new ProfileMenu(R.string.terms_use, 3,""));
        data.add(new ProfileMenu(R.string.end_user, 4,""));
    }
}
