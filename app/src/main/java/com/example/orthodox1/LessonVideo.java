
package com.example.orthodox1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class LessonVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
        YouTubePlayerView mPlayer;
        private String GOOGLE_API_KEY = "AIzaSyD9yQqmhWtbml6zAXmg4lJ121YJCGiplsg";
        String YOUTUBE_VID_ID = getIntent().getStringExtra("Video_Link");
        Button NextVid;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lesson_video);

            YouTubePlayerView youtubePlayerView = (YouTubePlayerView) findViewById(R.id.mPlayer);
            youtubePlayerView.initialize(GOOGLE_API_KEY, this);


        NextVid = findViewById(R.id.Next);
        NextVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        Toast.makeText(this, "Initialised Youtube Player successfully", Toast.LENGTH_LONG).show();
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if(!wasRestored){
            youTubePlayer.cueVideo(YOUTUBE_VID_ID);
        }

    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener= new YouTubePlayer.PlaybackEventListener(){

        @Override
        public void onPlaying() {
            Toast.makeText(LessonVideo.this, "Video is playing alright", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(LessonVideo.this, "Video is paused", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
            Toast.makeText(LessonVideo.this, "Click the ads!", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(LessonVideo.this, "Video was started!", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Initialised Youtube Player failed", Toast.LENGTH_LONG).show();

    }
}
