package com.developer.android.quickveggis.ui.fragments;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.VideoView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.utils.YouTubeExtractor;

public class SurveyVideoFragment extends Fragment implements YouTubeExtractor.YouTubeExtractorListener {
    @Bind(R.id.btnNext)
    View btnNext;
    private MediaPlayer mMediaPlayer;
    @Bind(R.id.videoView)
    VideoView videoView;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyVideoFragment.1 */
    class C03231 implements OnClickListener {
        C03231() {
        }

        public void onClick(View v) {
            SurveyVideoFragment.this.getActivity().finish();
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyVideoFragment.2 */
    class C03242 implements OnTouchListener {
        C03242() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 0) {
                try {
                    if (SurveyVideoFragment.this.mMediaPlayer.isPlaying()) {
                        SurveyVideoFragment.this.mMediaPlayer.pause();
                    } else {
                        SurveyVideoFragment.this.mMediaPlayer.start();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyVideoFragment.3 */
    class C03253 implements OnCompletionListener {
        C03253() {
        }

        public void onCompletion(MediaPlayer mp) {
            SurveyVideoFragment.this.btnNext.setEnabled(true);
        }
    }

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.SurveyVideoFragment.4 */
    class C03264 implements OnPreparedListener {
        C03264() {
        }

        public void onPrepared(MediaPlayer mp) {
            SurveyVideoFragment.this.mMediaPlayer = mp;
        }
    }

    public static SurveyVideoFragment newInstance() {
        Bundle args = new Bundle();
        SurveyVideoFragment fragment = new SurveyVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_video, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.btnNext.setOnClickListener(new C03231());
        this.btnNext.setEnabled(false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.videoView.getLayoutParams().height = (size.x * 9) / 16;
        this.videoView.setVisibility(0);
        this.videoView.setOnTouchListener(new C03242());
        play();
    }

    private void play() {
        new YouTubeExtractor("cdwWONi2FrI").startExtracting(this);
    }

    private void play(Uri uri) {
        this.videoView.setVideoURI(uri);
        this.videoView.setOnCompletionListener(new C03253());
        this.videoView.setOnPreparedListener(new C03264());
        this.videoView.start();
    }

    public void onSuccess(YouTubeExtractor.YouTubeExtractorResult result) {
        play(result.getVideoUri());
    }

    public void onFailure(Error error) {
        error.printStackTrace();
    }
}
