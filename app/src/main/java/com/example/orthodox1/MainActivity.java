package com.example.orthodox1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    TextView firstName;
    Button videoBtn;

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.activity_main_name);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    firstName.setText(documentSnapshot.getString("fName"));
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        //This part is for the animation at the beginning
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(1300).setStartDelay(1000);
        clover.animate().alpha(0).setDuration(1000).setStartDelay(1000);
        textsplash.animate().translationY(140).alpha(0).setDuration(1300).setStartDelay(950);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        // Done

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        videoBtn = findViewById(R.id.VideoBtn);
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoContent = new Intent(MainActivity.this, LessonsList.class);
                startActivity(VideoContent);
            }
        });


        View headerView = navigationView.getHeaderView(0);
        final TextView username = headerView.findViewById(R.id.userDisplayName);
        final TextView userEmail = headerView.findViewById(R.id.userDisplayEmail);


        DocumentReference documentReference1 = fStore.collection("users").document(userId);
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    username.setText(documentSnapshot.getString("fName"));
                    userEmail.setText(documentSnapshot.getString("email"));
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }

    public void message (View view){
        Toast.makeText(this, "you have clicked the image", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {

            case R.id.UserProfile:
                Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
                break;

            case R.id.UserLanguages:
                Intent intent1 = new Intent(this, Languages.class);
                startActivity(intent1);
                break;


            case R.id.logout:
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;

            default:
                break;
        }

        return true;

    }
}
