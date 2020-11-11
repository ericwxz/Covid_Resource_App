package com.example.covidresourceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Button b1 = findViewById(R.id.contactTracingButton);
        Button b2 = findViewById(R.id.visualPlannerButton);
        Button b3 = findViewById(R.id.distanceDetectionButton);
        Button b4 = findViewById(R.id.sign_out_button);

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openTaskOne();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openTaskTwo();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                openTaskThree();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                signOut();
            }
        });

    }

    void openTaskOne() {
        Intent intent = new Intent(this, ContactTracingActivity.class);
        startActivity(intent);
    }

    void openTaskTwo() {
        Intent intent = new Intent(this, VisualPlannerActivity.class);
        startActivity(intent);
    }

    void openTaskThree() {
        Intent intent = new Intent(this, DistanceDetectionActivity.class);
        startActivity(intent);
    }

    void signOut() {
        // sign out user
    }
}