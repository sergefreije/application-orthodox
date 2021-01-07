
package com.example.orthodox1;

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

public class LessonVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    int setNo;
    private final String GOOGLE_API_KEY = "AIzaSyD9yQqmhWtbml6zAXmg4lJ121YJCGiplsg";
    Button NextVid;
    FirebaseFirestore fStore;
    private String VideoId;
    public static int i;
    public static String  count;
    public static String  played = "NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_video);
        fStore = FirebaseFirestore.getInstance();
        NextVid = findViewById(R.id.Next);
        setNo = getIntent().getIntExtra("SETNO", 1);



        DocumentReference documentReference = fStore.collection("Arabic").document("Level 1")
                .collection("Lesson "+ setNo).document("Lesson");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable final DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                        VideoId  = documentSnapshot.getString("LectureVideo");


                        YouTubePlayerView youtubePlayerView = (YouTubePlayerView) findViewById(R.id.mPlayer);
                        youtubePlayerView.initialize(GOOGLE_API_KEY, LessonVideo.this);

                        NextVid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                } else {
                    Toast.makeText(LessonVideo.this, "The video is not Ready yet", Toast.LENGTH_SHORT);
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
