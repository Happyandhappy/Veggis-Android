package com.developer.android.quickveggis.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.config.Config;
import com.developer.android.quickveggis.model.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SurveyVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Bind(R.id.btnClose)
    View btnClose;

    @Bind(R.id.btnUnlock)
    Button btnUnlock;

    @Bind(R.id.txtTxtView)
    TextView txtHeader;

    @Bind(R.id.txt_video_title)
    TextView txtTitle;

    @Bind(R.id.txt_video_description)
    TextView txtDescription;

    @Bind(R.id.youtube_view)
    YouTubePlayerView playerView;

    @Bind(R.id.btnPlay)
    ImageView btnPlayVideo;

    @Bind(R.id.taskImage)
    ImageView taskImage;

    Task videoTask;
    String videoId;

    boolean isLoadedVideo = false;
    YouTubePlayer ytPlayer;

    private ProgressDialog progressDialog;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SurveyVideoActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        videoTask = (Task) getIntent().getSerializableExtra("task");
        if (videoTask.getTaskData().getVideoUrl() != null)
            videoId = extractYTId(videoTask.getTaskData().getVideoUrl());

        setContentView(R.layout.activity_survey);
        ButterKnife.bind(this);

        btnPlayVideo.setVisibility(View.GONE);
        btnPlayVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoadedVideo && ytPlayer != null) {
                    if (!videoId.equalsIgnoreCase("")) {
                        btnPlayVideo.setVisibility(View.GONE);
                        ytPlayer.play();
                    }
                }
            }
        });

        switch (Integer.parseInt(videoTask.getType()) ) {
            case Config.TASK_DO_GOODER:
                taskImage.setImageResource(R.drawable.ic_do_gooder);
                txtHeader.setText("Do Gooder");
                break;
        }

        String strTemp = videoTask.getTaskData().getFactTitle();
        if ( strTemp != null )
            txtTitle.setText(strTemp);

        strTemp = videoTask.getTaskData().getFactContent();
        if (strTemp != null)
            txtDescription.setText( strTemp );

        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUnlock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent();
                outIntent.putExtra(Config.KEY_UNLOCKED_TASK_ID, videoTask.getId());

                //getActivity().setResult(200, outIntent);
                setResult(Activity.RESULT_OK, outIntent);
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.msg_video_loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Initializing video player with developer key
        playerView.initialize(Config.GOOGLE_DEVELOPER_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (!b) {
            isLoadedVideo = true;
            ytPlayer = youTubePlayer;
            ytPlayer.setPlayerStateChangeListener(playerStateChangeListener);
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            if (!videoId.equalsIgnoreCase("")) {
                youTubePlayer.cueVideo(videoId);
                // Hiding player controls
                ytPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public String extractYTId(String ytUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "";
        }
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            btnPlayVideo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            btnPlayVideo.setVisibility(View.GONE);
        }

        @Override
        public void onVideoEnded() {
//            btnPlayVideo.setVisibility(View.VISIBLE);
            btnUnlock.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}

