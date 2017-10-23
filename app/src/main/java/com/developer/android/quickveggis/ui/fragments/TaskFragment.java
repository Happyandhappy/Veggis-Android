package com.developer.android.quickveggis.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.Task;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskFragment extends Fragment {

    @Bind(R.id.btnNext)
    View btnNext;
    @Bind(R.id.img)
    ImageView img_task;

    @Bind(R.id.txtHeader)
    TextView txtHeader;

    @Bind(R.id.txtDesc)
    TextView txtDescription;

    @Bind(R.id.txtTitle)
    TextView txtTitle;

    Task task;

    String imgUrl;
    String Description;
    String title;
    int type;

    public TaskFragment() {
    }

    public static TaskFragment newInstance(Task task) {
        Bundle args = new Bundle();
//        args.putSerializable("selected_task", selectedTask);
        TaskFragment fragment = new TaskFragment();
        fragment.task = task;
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this, view);


            if (task != null){
                if (task.getTaskData().getAttachmentUrl() != null && !task.getTaskData().getAttachmentUrl().isEmpty())
                    imgUrl = task.getTaskData().getAttachmentUrl();
                else{
                    if (task.getTaskData().getPictureUrl() != null && !task.getTaskData().getPictureUrl().isEmpty())
                        imgUrl = task.getTaskData().getPictureUrl();
                }

                type = Integer.parseInt(task.getType());
                if (type == Config.TASK_RECIPE) {
                    title = task.getTaskData().getReceipeName();
                    Description = task.getTaskData().getReceipeDescription();
                } else {
                    title = task.getTaskData().getFactTitle();
                    Description = task.getTaskData().getFactContent();
                }

            }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (imgUrl != null && !imgUrl.isEmpty())
            Picasso.with(getContext()).load(imgUrl).placeholder(R.drawable.place_holder).into(this.img_task);

        // Text Header
        switch (Integer.parseInt(task.getType()) ) {
            case Config.TASK_FACT:
                txtHeader.setText("Fact");
                break;

            case Config.TASK_RECIPE:
                txtHeader.setText("Recipe");
                break;

            case Config.TASK_NUTRITION:
                txtHeader.setText("Nutrition Fact");
                break;

            case Config.TASK_DO_GOODER:
                txtHeader.setText("Do Gooder");
                break;
        }

        if (type != Config.TASK_DO_GOODER) {
            if (type > 0)
                txtTitle.setText(Config.taskTitleArray[type - 1]);
        }

        if (title != null)
            txtTitle.setText(title);
        if (Description != null)
            txtDescription.setText(Description);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                App.setTask(true);
                //FragmentUtils.changeFragment(getActivity(), R.id.content, UnlockedFragment.newInstance(), false);
                Intent outIntent = new Intent();
                outIntent.putExtra(Config.KEY_UNLOCKED_TASK_ID, task.getId());

                //getActivity().setResult(200, outIntent);
                getActivity().setResult(Activity.RESULT_OK, outIntent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 1){
//            this.getActivity().finish();
//        }
    }
}