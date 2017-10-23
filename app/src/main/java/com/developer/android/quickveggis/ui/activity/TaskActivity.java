package com.developer.android.quickveggis.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Task;
import com.developer.android.quickveggis.ui.fragments.SurveyEditFragment;
import com.developer.android.quickveggis.ui.fragments.SurveyImageChooserFragment;
import com.developer.android.quickveggis.ui.fragments.SurveyRadioFragment;
import com.developer.android.quickveggis.ui.fragments.TaskFragment;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskActivity extends AppCompatActivity {
    @Bind(R.id.btnClose)
    View btnClose;
    @Bind(R.id.taskImage)
    ImageView taskImage;

    int task_type;
    Task taskData;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TaskActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        this.btnClose.setOnClickListener(mOnClickListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            taskData = (Task)extras.getSerializable("task");
        }

        if (taskData != null){
            switch (Integer.parseInt(taskData.getType())){
                case Config.TASK_DO_GOODER:
                    taskImage.setImageResource(R.drawable.ic_do_gooder);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, TaskFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_FACT:
                    taskImage.setImageResource(R.drawable.ic_fact);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, TaskFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_LETS_BATTLE:
                    taskImage.setImageResource(R.drawable.ic_lets_battle);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyImageChooserFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_NUTRITION:
                    taskImage.setImageResource(R.drawable.ic_nutrition);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, TaskFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_POLL:
                    taskImage.setImageResource(R.drawable.ic_poll);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyRadioFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_RECIPE:
                    taskImage.setImageResource(R.drawable.ic_recipe);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, TaskFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_SURVEY:
                    taskImage.setImageResource(R.drawable.ic_survey);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyRadioFragment.newInstance(taskData), false);
                    break;

                case Config.TASK_TRIVIA:
                    taskImage.setImageResource(R.drawable.ic_trivia);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyRadioFragment.newInstance(taskData), false);
                    break;

//                case Config.TASK_VIDEO:
//                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyVideoFragment.newInstance(), false);
//                    break;

                case Config.TASK_TESTIFY:
                    taskImage.setImageResource(R.drawable.ic_testify);
                    FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, SurveyEditFragment.newInstance(taskData), false);
                    break;
            }
        }
        //FragmentUtils.changeFragment((FragmentActivity) this, (int) R.id.content, TaskFragment.newInstance(), false);
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(TaskActivity.this, ConfirmActivity.class), 1);
//            DialogUtils.showAlertDialog(TaskActivity.this, "test");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            this.finish();
        }
    }
}

