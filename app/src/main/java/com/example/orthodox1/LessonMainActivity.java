package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class LessonMainActivity extends AppCompatActivity {
    int setNo;
    public static String  lesson, lessontext;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fStore = FirebaseFirestore.getInstance();
        setNo = getIntent().getIntExtra("SETNO", 1);


        Button startVideo = findViewById(R.id.lessons_main_startB);
        Button startExercises = findViewById(R.id.exercises_main_start);
        Button TestYourself = findViewById(R.id.test_start);
        final TextView lessons_main_title = findViewById(R.id.lessons_main_title);
        final TextView lessons_main_Text = findViewById(R.id.lessons_main_Text);



        DocumentReference documentReference = fStore.collection("Arabic").document("Level 1")
                .collection("Lesson " + setNo).document("Lesson");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable final DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    lesson = documentSnapshot.getString("Title");
                    lessons_main_title.setText(lesson);

                    lessontext = documentSnapshot.getString("Text");
                    lessons_main_Text.setText(lessontext);

                } else {
                    lessons_main_title.setText("Lesson number");
                }
        }
    });

        startVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LessonMain = new Intent(LessonMainActivity.this, LessonVideo.class);
                LessonMain.putExtra("SETNO", setNo);
                startActivity(LessonMain);
            }
        });


        startExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LessonMain = new Intent(LessonMainActivity.this, ExerciseVideos.class);
                LessonMain.putExtra("SETNO", setNo);
                startActivity(LessonMain);
            }
        });

        TestYourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LessonMain = new Intent(LessonMainActivity.this, PDF.class);
                startActivity(LessonMain);
            }
        });


    }
}

