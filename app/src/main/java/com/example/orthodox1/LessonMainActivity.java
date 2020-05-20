package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LessonMainActivity extends AppCompatActivity {
    private Button StartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StartBtn = findViewById(R.id.lessons_main_startB);
        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoContent = new Intent(LessonMainActivity.this, LessonVideo.class );
                startActivity(VideoContent);
            }
        });
    }
}
