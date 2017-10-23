package com.developer.android.quickveggis.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 12/15/2016.
 */

public class NotificationLaunch extends Fragment {

    NotificationListFragment parentFragment;
    public String strUrlPath;

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.btnDone)
    Button btnDone;

    public static NotificationLaunch newInstance() {

        Bundle args = new Bundle();
        NotificationLaunch fragment = new NotificationLaunch();
        fragment.setArguments(args);

        return fragment;
    }

    public static NotificationLaunch newInstance(Bundle args) {
        NotificationLaunch fragment = new NotificationLaunch();
        fragment.setArguments(args);
        return fragment;
    }

    public static NotificationLaunch newInstance(String strPath, NotificationListFragment pFragment) {
        NotificationLaunch fragment = new NotificationLaunch();

        fragment.parentFragment = pFragment;

        if ( !strPath.contains("http")) {
            strPath = "http://" + strPath;
        }
        fragment.strUrlPath = strPath;

        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_launch, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle(R.string.notification_launch);

        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(strUrlPath);
//        webView.requestFocus();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.popBackStack(NotificationLaunch.this.getActivity());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
