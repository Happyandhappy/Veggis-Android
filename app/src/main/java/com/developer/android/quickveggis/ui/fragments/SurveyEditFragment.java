package com.developer.android.quickveggis.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Task;

public class SurveyEditFragment extends Fragment {
    @Bind(R.id.btnNext)
    View btnNext;

    @Bind(R.id.txtQuestion)
    TextView txtQuestion;

    @Bind(R.id.txt_answer)
    EditText txtAnswer;

    String answer;
    String question;
    Task editTask;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyEditFragment.1 */
    class C03181 implements OnClickListener {
        C03181() {
        }

        public void onClick(View v) {
//            SurveyEditFragment.this.getActivity().startActivity(UnlockedActivity.getStartIntent(SurveyEditFragment.this.getContext()));
//            SurveyEditFragment.this.getActivity().finish();
//            if (question.equalsIgnoreCase(answer)) {
            Intent outIntent = new Intent();
            outIntent.putExtra(Config.KEY_UNLOCKED_TASK_ID, editTask.getId());

            //getActivity().setResult(200, outIntent);
            getActivity().setResult(Activity.RESULT_OK, outIntent);
            getActivity().finish();
//            }
        }
    }

    public static SurveyEditFragment newInstance(Task task) {
        Bundle args = new Bundle();
        SurveyEditFragment fragment = new SurveyEditFragment();
        fragment.editTask = task;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_memo, container, false);
        ButterKnife.bind((Object) this, view);

        btnNext.setEnabled(false);


        if (editTask != null) {
            if (editTask.getTaskData().getQuestion() != null) {
                question = editTask.getTaskData().getQuestion();
                txtQuestion.setText(question);
            } else
                question = "";

            if (editTask.getTaskData().getCorrectAnswer() != null) {
                answer = editTask.getTaskData().getCorrectAnswer();
            } else
                answer = "";
        }

        txtAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                answer = s.toString();
                checkAnswer();
            }
        });

        return view;
    }

    private void checkAnswer() {
        if (question.equalsIgnoreCase(answer)) {

            btnNext.setEnabled(true);
        } else {
            //for test

            btnNext.setEnabled(true);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.btnNext.setOnClickListener(new C03181());
    }
}
