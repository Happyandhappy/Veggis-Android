
/*Merge Notification and support chat by Happyandhappy on 10/31/2017*/
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

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.TestData;
import com.developer.android.quickveggis.model.NotificationModel;
import com.developer.android.quickveggis.model.Support;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.adapter.NotificationAdapter;
import com.developer.android.quickveggis.ui.adapter.SupportAdapter;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.freshdesk.hotline.Hotline;
import com.quickveggies.quickveggies.ui.custom.SlideButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationListFragment extends Fragment implements MainActivity.MenuController{
    static final int TAB_NOTIFICATION = 1;
    static final int TAB_MESSAGE = 2;

    NotificationAdapter notifyadapter;    // the adapter for notification data
    SupportAdapter suppAdapter;           // the adapter for support chat data

    List<NotificationModel> notifydata;   // notification data
    List<Support> suppdata;               // support chat data


    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.tabNotification)
    View tabNotification;
    @Bind(R.id.tabSupport)
    View tabSupport;
    @Bind(R.id.btn_notislide)
    SlideButton btnSlide;

    boolean flag;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.HistoryListFragment.1 */
    class C05651 implements RecyclerItemClickListener.OnItemClickListener {
        C05651() {
        }

        public void onItemClick(View view, int position) {

            if (tabNotification.isSelected()) { //if the current tab is tabNotification
                NotificationModel notification = (NotificationModel) notifydata.get(position);
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
            } else if (flag){//if the current tab is tabSupport
                    Hotline.showConversations(NotificationListFragment.this.getActivity());
                    flag=!flag;
            }
        }
    }

    public NotificationListFragment() {
        this.notifydata = new ArrayList();
        this.suppdata=new ArrayList<>();
        flag=true;
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
        getActivity().setTitle("");

        // Initially set tabNotification as current tab
        updateTab(TAB_NOTIFICATION);
        tabNotification.setSelected(true);
        tabSupport.setSelected(false);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        setNotificationData();

        // set the listener to the TABs which control the state.
        tabNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTab(TAB_NOTIFICATION);
                tabNotification.setSelected(true);
                tabSupport.setSelected(false);
            }
        });

        tabSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTab(TAB_MESSAGE);
                tabNotification.setSelected(false);
                tabSupport.setSelected(true);
            }
        });

    }

    // Set the tab state which tab is selected
    private void updateTab(int tab) {
        if (tab == TAB_NOTIFICATION) {
            this.btnSlide.startAnimation(TAB_NOTIFICATION);
            this.tabNotification.setSelected(true);
            this.tabSupport.setSelected(false);
            setNotificationData();
            return;
        }
        this.btnSlide.startAnimation(100);
        this.tabSupport.setSelected(false);
        this.tabNotification.setSelected(true);
        setSupportData();
    }

    private void setNotificationData(){  // set data in notification list
        notifydata.clear();
        NotificationModel notificationRepo = new NotificationModel();
        if (false) { // To test
            notifydata.addAll(TestData.getNotification());
        } else {

            List<NotificationModel> notifications = notificationRepo.getAll();
            ArrayList<NotificationModel> arrayList = new ArrayList<>(notifications);
            notifydata.addAll(arrayList);
        }

        notifyadapter = new NotificationAdapter(this.notifydata, getContext());
        rv.setAdapter(this.notifyadapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05651()));
        NotificationModel.lastVisited(getContext());
    }

    private void setSupportData(){  // set data in support chat list
        flag=true;
        suppdata.clear();
        suppdata.addAll(TestData.getSupports());
        suppAdapter = new SupportAdapter(suppdata, getContext());
        rv.setAdapter(suppAdapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),new C05651()));
    }

    @Override
    public void onResume() {
        super.onResume();
        flag=true;  // initial set flag for avoid double hotline screens
    }

    @Override
    public int getMenuVisibility() {
        return 1;
    }

}

