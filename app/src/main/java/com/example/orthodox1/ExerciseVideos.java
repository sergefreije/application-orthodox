package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class ExerciseVideos  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private final String GOOGLE_API_KEY = "AIzaSyD9yQqmhWtbml6zAXmg4lJ121YJCGiplsg";
    Button NextVid;
    FirebaseFirestore fStore;
    private String VideoId;
    public static int i;
    public static String  count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);
        fStore = FirebaseFirestore.getInstance();
        NextVid = findViewById(R.id.Next);
        int setNo = getIntent().getIntExtra("SETNO", 1);



        DocumentReference documentReference = fStore.collection("Arabic").document("Level 1")
                .collection("Lesson" + setNo).document("Exercises_LIST");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable final DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    count = documentSnapshot.getString("COUNT");

                    if (i < Integer.parseInt(count)) {

                        String VideoID = ("VideoLink" + (i + 1));
                        VideoId = documentSnapshot.getString(VideoID);

                        YouTubePlayerView youtubePlayerView = (YouTubePlayerView) findViewById(R.id.mPlayer);
                        youtubePlayerView.initialize(GOOGLE_API_KEY, ExerciseVideos.this);

                        NextVid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                i++;
                                String VideoID = ("VideoLink" + (i + 1));
                                VideoId = documentSnapshot.getString(VideoID);
                                Intent LessonMain = new Intent(ExerciseVideos.this, LessonVideo.class);
                                YouTubePlayerView youtubePlayerView = (YouTubePlayerView) findViewById(R.id.mPlayer);
                                youtubePlayerView.initialize(GOOGLE_API_KEY, ExerciseVideos.this);
                                startActivity(LessonMain);
                                finish();
                            }
                        });
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(ExerciseVideos.this, "No Video was found!", Toast.LENGTH_SHORT);
                    finish();
                }

            }
        });

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if(!wasRestored){
            youTubePlayer.cueVideo(VideoId);
        }

    }
    private final YouTubePlayer.PlaybackEventListener playbackEventListener= new YouTubePlayer.PlaybackEventListener(){

        @Override
        public void onPlaying() {
        }

        @Override
        public void onPaused() {

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
    final YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

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
