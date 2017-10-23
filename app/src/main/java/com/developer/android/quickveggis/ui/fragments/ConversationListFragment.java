package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Conversation;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ConversationListFragment extends Fragment  implements MainActivity.MenuController{
    ConversationAdapter adapter;
    List<Conversation> data;
    @Bind(R.id.fab)
    View fab;
    @Bind(R.id.rv)
    RecyclerView rv;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ConversationListFragment.2 */
    class C02942 implements OnClickListener {
        C02942() {
        }

        public void onClick(View v) {
            FragmentUtils.changeFragment(ConversationListFragment.this.getActivity(), (int) R.id.content, ChatFragment.newInstance(), true);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ConversationListFragment.1 */
    class C05641 implements RecyclerItemClickListener.OnItemClickListener {
        C05641() {
        }

        public void onItemClick(View view, int position) {
            FragmentUtils.changeFragment(ConversationListFragment.this.getActivity(), (int) R.id.content, ChatFragment.newInstance(), true);
        }
    }

    public class ConversationAdapter extends Adapter<ConversationAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.txtMessage)
            TextView txtMessage;
            @Bind(R.id.txtWho)
            TextView txtWho;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(ConversationListFragment.this.getContext()).inflate(R.layout.item_converstion, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
        }

        public int getItemCount() {
            return ConversationListFragment.this.data.size();
        }
    }

    public ConversationListFragment() {
        this.data = new ArrayList();
    }

    public static ConversationListFragment newInstance() {
        Bundle args = new Bundle();
        ConversationListFragment fragment = new ConversationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.team_qv);
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new ConversationAdapter();
        fill();
        this.rv.setAdapter(this.adapter);
        this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05641()));
        //this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_history)).build());
        this.fab.setOnClickListener(new C02942());
    }

    private void fill() {
        this.data.clear();
        this.data.add(new Conversation());
    }

    public int getMenuVisibility() {
        return 1;
    }

}
