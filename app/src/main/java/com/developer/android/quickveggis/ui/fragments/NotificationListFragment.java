package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.TestData;
import com.developer.android.quickveggis.db.NotificationRepo;
import com.developer.android.quickveggis.model.NotificationModel;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.adapter.NotificationAdapter;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.quickveggies.quickveggies.ui.custom.SlideButton;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationListFragment extends Fragment implements MainActivity.MenuController{
    static final int TAB_NOTIFICATION = 1;
    static final int TAB_MESSAGE = 2;

    NotificationAdapter adapter;
    List<NotificationModel> data;

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.tabNotification)
    View tabNotification;
    @Bind(R.id.tabSupport)
    View tabSupport;
    @Bind(R.id.btn_notislide)
    SlideButton btnSlide;

    int type;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.HistoryListFragment.1 */
    class C05651 implements RecyclerItemClickListener.OnItemClickListener {
        C05651() {
        }

        public void onItemClick(View view, int position) {

            NotificationModel notification = (NotificationModel) data.get(position);

            String description = notification.getContent();
            try {
                JSONObject obj = new JSONObject(description);

                String customStr = obj.getString("custom");
                JSONObject customObj0 = new JSONObject(customStr);

                if (customObj0 != null) {
                    String urlObj = customObj0.getString("u");
                    if (urlObj != null) {

                        FragmentUtils.changeFragment(NotificationListFragment.this.getActivity(), (int) R.id.content, NotificationLaunch.newInstance(urlObj, NotificationListFragment.this), true);
                    }
                }

                Log.d("My App", obj.toString());

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + description + "\"");

            }
        }
    }

    public NotificationListFragment() {
        this.data = new ArrayList();
    }

    public static NotificationListFragment newInstance() {
        Bundle args = new Bundle();
        NotificationListFragment fragment = new NotificationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.notifications);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        data.clear();

        NotificationModel notificationRepo = new NotificationModel();
        if (false) { // To test
            data.addAll(TestData.getNotification());
        } else {

                List<NotificationModel> notifications = notificationRepo.getAll();
                ArrayList<NotificationModel> arrayList = new ArrayList<>(notifications);
                data.addAll(arrayList);
        }

        adapter = new NotificationAdapter(this.data, getContext());
        rv.setAdapter(this.adapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05651()));

        NotificationModel.lastVisited(getContext());
    }

    @Override
    public int getMenuVisibility() {
        return 1;
    }

    private void updateTab(int tab) {
        if (tab == TAB_NOTIFICATION) {
            this.btnSlide.startAnimation(TAB_NOTIFICATION);
            this.tabNotification.setSelected(true);
            this.tabSupport.setSelected(false);
//            this.blockNotification.setVisibility(View.VISIBLE);
//            this.blockSupport.setVisibility(View.GONE);
            return;
        }
        this.btnSlide.startAnimation(100);
        this.tabSupport.setSelected(false);
        this.tabNotification.setSelected(true);
//        this.blockSupport.setVisibility(View.VISIBLE);
//        this.blockNotification.setVisibility(View.GONE);
    }
}
