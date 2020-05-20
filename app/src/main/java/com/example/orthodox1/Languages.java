package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Languages extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button updatelanguage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_languages);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Spinner aSpinner = findViewById(R.id.spinner);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        // this part is to show the chosen language

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //  R.array.spinner_items, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //aSpinner.setAdapter(adapter);

        final DocumentReference documentReference = fStore.collection("users").document(user.getUid());

        updatelanguage = findViewById(R.id.Update);

        updatelanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> edited = new HashMap<>();
                edited.put("languagevideo", aSpinner.getSelectedItem().toString());

                documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Languages.this, aSpinner.getSelectedItem().toString() + " has been Chosen", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

    }
}