package com.example.orthodox1;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class LessonVideo extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_video);
        MediaController mediaController = new MediaController(this);
        videoView = findViewById(R.id.videoView1);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/orthodoxmusicapp.appspot.com/o/20180404_145856.mp4?alt=media&token=f13e026e-051e-4704-862a-9945935277ab");
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
