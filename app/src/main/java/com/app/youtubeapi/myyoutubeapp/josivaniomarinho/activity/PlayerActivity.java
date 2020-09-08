package com.app.youtubeapi.myyoutubeapp.josivaniomarinho.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.R;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.helper.YouTubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity
                            implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubePlayerView;
    private String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        youTubePlayerView = findViewById(R.id.youTubeView);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            idVideo = bundle.getString("idVideo");
            youTubePlayerView.initialize(YouTubeConfig.CHAVE_YOUTUBE_API, this);
        }

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setFullscreen(true);
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.loadVideo(idVideo);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
