package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class LessonMainActivity extends AppCompatActivity {
    private int setNo;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fStore = FirebaseFirestore.getInstance();
        setNo = getIntent().getIntExtra("SETNO", 1);

        getVideoList();

        Button startVideo = findViewById(R.id.lessons_main_startB);
        Button startExercises = findViewById(R.id.exercises_main_start);
        Button TestYourself = findViewById(R.id.test_start);


        //final TextView username = findViewById(R.id.lessons_main_title);


        startVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LessonMain = new Intent(LessonMainActivity.this, LessonVideo.class);
                startActivity(LessonMain);
            }
        });


        startExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LessonMain = new Intent(LessonMainActivity.this, ExerciseVideos.class);
                startActivity(LessonMain);
            }
        });

        TestYourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LessonMain = new Intent(LessonMainActivity.this, QuestionActivity.class);
                startActivity(LessonMain);
            }
        });


    }

    private void getVideoList() {
         DocumentReference documentReference = fStore.collection("Arabic").document("Level 1")
                 .collection("Lesson" + setNo).document("Video_LIST");
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        String count = documentSnapshot.getString("COUNT");
                        if (Integer.parseInt(count) == 0) {
                            finish();
                        }
                        }else{
                            finish();
                        }
                    }
            });
    }
}

