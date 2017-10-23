package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.TestData;
import com.developer.android.quickveggis.model.Support;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.adapter.SupportAdapter;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.freshdesk.hotline.Hotline;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupportListFragment extends Fragment implements MainActivity.MenuController{
    SupportAdapter adapter;
    List<Support> data;
    StickyRecyclerHeadersDecoration decoration;
    @Bind(R.id.rv)
    RecyclerView rv;
    int type;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.HistoryListFragment.1 */
    class C05651 implements RecyclerItemClickListener.OnItemClickListener {
        C05651() {

        }

        public void onItemClick(View view, int position) {
//            ((MainActivity) SupportListFragment.this.getActivity()).moveIntoConversationsActivity();
//            FragmentUtils.changeFragment(SupportListFragment.this.getActivity(), (int) R.id.content, ConversationListFragment.newInstance(), true);

//            Hotline.showFAQs(getApplicationContext());
            Hotline.showConversations(SupportListFragment.this.getActivity());
        }
    }

    public SupportListFragment() {
        this.data = new ArrayList();
    }

    public static SupportListFragment newInstance() {
        Bundle args = new Bundle();
        SupportListFragment fragment = new SupportListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support_list, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.supports);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        data.clear();
        data.addAll(TestData.getSupports());
        adapter = new SupportAdapter(this.data, getContext());
        rv.setAdapter(this.adapter);
//        decoration = new StickyRecyclerHeadersDecoration(this.adapter);
//        rv.addItemDecoration(this.decoration);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05651()));
//        rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(R.color.divider))).sizeResId(R.dimen.divider_history)).build());
    }

    @Override
    public int getMenuVisibility() {
        return 1;
    }
}