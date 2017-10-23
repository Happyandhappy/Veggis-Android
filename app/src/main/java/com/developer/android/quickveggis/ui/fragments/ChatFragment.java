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
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Message;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    ChatAdapter adapter;
    List<Message> data;
    @Bind(R.id.rv)
    RecyclerView rv;

    public class ChatAdapter extends Adapter<ChatAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.txtMessage)
            TextView txtMessage;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new Holder(LayoutInflater.from(ChatFragment.this.getContext()).inflate(R.layout.item_chat_admin, parent, false));
            }
            if (viewType == 2) {
                return new Holder(LayoutInflater.from(ChatFragment.this.getContext()).inflate(R.layout.item_chat_user, parent, false));
            }
            return null;
        }

        public void onBindViewHolder(Holder holder, int position) {
        }

        public int getItemViewType(int position) {
            return ((Message) ChatFragment.this.data.get(position)).getType();
        }

        public int getItemCount() {
            return ChatFragment.this.data.size();
        }
    }

    public ChatFragment() {
        this.data = new ArrayList();
    }

    public static ChatFragment newInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.team_qv);
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new ChatAdapter();
        fill();
        this.rv.setAdapter(this.adapter);
        this.rv.addItemDecoration(((Builder) ((Builder) new Builder(getActivity()).color(getResources().getColor(17170445))).sizeResId(R.dimen.divider_chat)).build());
    }

    private void fill() {
        this.data.clear();
        this.data.add(new Message(1));
        this.data.add(new Message(2));
        this.data.add(new Message(1));
    }
}
