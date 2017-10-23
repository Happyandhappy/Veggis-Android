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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SurveyRadioFragment extends Fragment {
    List<String> answers;

    @Bind(R.id.txtHeader)
    TextView txtHeader;

    @Bind(R.id.btnNext)
    View btnNext;

    @Bind(R.id.radioAnswers)
    RadioGroup radioAnswers;

    @Bind(R.id.txtQuestion)
    TextView txtQuestion;

    @Bind(R.id.txtError)
    TextView txtError;

    Task task;
    String questions = "";
    String correctAnswer = "";
    List<String> answersArray;


    boolean isCorrect = false;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyRadioFragment.1 */
    class C03201 implements OnClickListener {
        C03201() {
        }

        public void onClick(View v) {

            if (Integer.parseInt(task.getType()) == Config.TASK_TRIVIA) {
                if (isCorrect) {
                    txtError.setVisibility(View.GONE);
                } else {
                    txtError.setVisibility(View.VISIBLE);
                    return;
                }
            }

//            FragmentUtils.changeFragment(SurveyRadioFragment.this.getActivity(), (int) R.id.content, SurveyCheckFragment.newInstance(), false);
            App.setTask(true);
            //FragmentUtils.changeFragment(getActivity(), R.id.content, UnlockedFragment.newInstance(), false);
            Intent outIntent = new Intent();
            outIntent.putExtra(Config.KEY_UNLOCKED_TASK_ID, task.getId());

            //getActivity().setResult(200, outIntent);
            getActivity().setResult(Activity.RESULT_OK, outIntent);
            getActivity().finish();

        }
    }

    public SurveyRadioFragment() {
        this.answers = new ArrayList();
    }

    public static SurveyRadioFragment newInstance(Task task) {
        Bundle args = new Bundle();
//        args.putSerializable("selected_task", task);
        SurveyRadioFragment fragment = new SurveyRadioFragment();
        fragment.task = task;
//        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_radio, container, false);
        ButterKnife.bind((Object) this, view);

        btnNext.setEnabled(false);

        // Text Header
        switch (Integer.parseInt(task.getType())) {
            case Config.TASK_POLL:
                txtHeader.setText("Poll");
                break;

            case Config.TASK_SURVEY:
                txtHeader.setText("Survey");
                break;

            case Config.TASK_TRIVIA:
                txtHeader.setText("Triva");
                break;
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (task != null) {
            questions = task.getTaskData().getQuestion();
            if (questions != null)
                txtQuestion.setText(questions);

            if (task.getTaskData().getAnswerType().equalsIgnoreCase("radio")) {
                answersArray = task.getTaskData().getAnswers();

            }

            if (task.getTaskData().getCorrectAnswer() != null)
                correctAnswer = task.getTaskData().getCorrectAnswer();

        }
        this.btnNext.setOnClickListener(new C03201());
        fillTest();
        fillAnswers();
    }

    private void fillTest() {
        this.answers.clear();
        if (answersArray != null) {
            for (int i = 0; i < answersArray.size(); i++) {
                this.answers.add(answersArray.get(i).toString());
            }
        }

    }

    private void fillAnswers() {
        this.radioAnswers.removeAllViews();
        int i = 0;
        for (String answer : this.answers) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_survey_radio, this.radioAnswers, false);
            radioButton.setText(answer);
            radioButton.setId(i);
            this.radioAnswers.addView(radioButton);
            i++;
        }

        this.radioAnswers.clearCheck();
        this.radioAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioId = checkedId;
                if (answers.get(selectedRadioId).equalsIgnoreCase(correctAnswer)) {
                    isCorrect = true;
                } else {
                    isCorrect = false;
                }
                btnNext.setEnabled(true);
            }
        });
    }
}