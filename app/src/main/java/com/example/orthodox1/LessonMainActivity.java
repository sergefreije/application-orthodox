package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class LessonMainActivity extends AppCompatActivity {
    private Button StartBtn;
    private int setNo;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fStore = FirebaseFirestore.getInstance();
        setNo = getIntent().getIntExtra("SETNO", 1);


        StartBtn = findViewById(R.id.lessons_main_startB);
        getVideoList();



    }

    private void getVideoList() {
         DocumentReference documentReference = fStore.collection("Arabic").document("Level 1").collection("Lesson" + String.valueOf(setNo)).document("Video_LIST");
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        String count = documentSnapshot.getString("COUNT");
                        if (Integer.parseInt(count) != 0) {
                            for (int i = 1; i <= Integer.parseInt(count); i++) {
                                final String VideoLink = documentSnapshot.getString("VideoLink" + (i));
                                StartBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent LessonMain = new Intent(LessonMainActivity.this, LessonVideo.class);
                                        LessonMain.putExtra("Video_Link", VideoLink);
                                        Toast.makeText(LessonMainActivity.this, VideoLink, Toast.LENGTH_SHORT).show();
                                        startActivity(LessonMain);
                                    }
                                });

                            }
                        }else{
                            finish();
                        }
                        }else{
                            finish();
                        }
                    }
            });
    }
}

