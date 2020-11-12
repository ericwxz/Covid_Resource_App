// Mudi's code

package com.example.covidresourceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class VisualPlannerActivity extends AppCompatActivity {

    ArrayList<Integer> corners = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_planner);


        //listener for new corner
        Button SetCornerButton = findViewById(R.id.setCornerButton);

        SetCornerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                setCorner();
            }
        });


        //listener for completed all corners
        Button GenRoom = findViewById(R.id.generateRoomButton);
        GenRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                genRoom(corners);
            }
        });


    }

    void setCorner() {
        Integer position;
        position = 0;
        corners.add(position);
    }

    void genRoom(ArrayList<Integer> corners){
        Intent intent = new Intent(this, ShowEmptyRoom.class);
        startActivity(intent);
    }


}