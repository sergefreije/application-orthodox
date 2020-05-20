package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class registerExtraInfo extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText City;
    Button startingapp;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_extra_info);
        City = findViewById(R.id.city);
        final Spinner CountrySpinner = findViewById(R.id.countrySpinner);
        final Spinner LevelSpinner = findViewById(R.id.registrationLevelSpinner);
        final Spinner RegistrationLanguageSpinner = findViewById(R.id.registrationLanguageSpinner);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = fAuth.getCurrentUser().getUid();

        // this part is to show the chosen language

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //  R.array.spinner_items, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //aSpinner.setAdapter(adapter);

        final DocumentReference documentReference = fStore.collection("users").document(user.getUid());
        startingapp = findViewById(R.id.starting);

        startingapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(City.getText().toString())) {
                    City.setError("Email is Required.");
                    return;
                }

                final DocumentReference documentReference = fStore.collection("users").document(user.getUid());
                Map<String, Object> user = new HashMap<>();

                user.put("Country", CountrySpinner.getSelectedItem().toString());
                user.put("Level", LevelSpinner.getSelectedItem().toString());
                user.put("languagevideo", RegistrationLanguageSpinner.getSelectedItem().toString());
                user.put("City", City.getText().toString());

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

    }
}
