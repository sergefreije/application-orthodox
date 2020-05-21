package com.example.orthodox1;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LessonsList extends AppCompatActivity {
    //Button btn1;
    //  @Override
    //protected void onCreate(Bundle savedInstanceState) {
    //super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_lessons_list);
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //btn1 = findViewById(R.id.floatingActionButton1);
    //btn1.setOnClickListener(new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    //    Intent LessonContent = new Intent(LessonsList.this, LessonMainActivity.class);
    //    startActivity(LessonContent);
    //  }
    //});
    // }
//}


    private GridView sets_grid;
    private FirebaseFirestore firestore;
     public static String userId,Language,Level;
    FirebaseAuth fAuth;
    final public static List<String> setsIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_list);

        sets_grid = findViewById(R.id.sets_gridview);
        firestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        loadSets();

    }

    public void loadSets() {

        setsIDs.clear();


            firestore.collection("Arabic").document("Level 1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    long noOfLessons = (long) documentSnapshot.get("Lessons");
                    for (int i = 1; i <= noOfLessons; i++) {
                        setsIDs.add(documentSnapshot.getString("Lesson" + String.valueOf(i)));
                    }

                    SetsAdapter adapter = new SetsAdapter(setsIDs.size());
                    sets_grid.setAdapter(adapter);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LessonsList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }


    @Override
        public boolean onOptionsItemSelected (MenuItem item){

            if (item.getItemId() == android.R.id.home) {
                LessonsList.this.finish();
            }
            return super.onOptionsItemSelected(item);
    }

}