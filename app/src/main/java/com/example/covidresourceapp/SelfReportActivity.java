package com.example.covidresourceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SelfReportActivity extends AppCompatActivity {
    private static final String TAG = SelfReportActivity.class.getSimpleName();
    GoogleSignInAccount account = MainActivity.getAccount();
    GoogleSignInClient client = MainActivity.getClient();
    String userEmail = account.getEmail();
    String userIdentity = account.getDisplayName();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Set<String> closeContactEmails = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_report);

        Button addContact = findViewById(R.id.addContactButton);
        addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                addCloseContact();
            }
        });

        Button notifyContacts = findViewById(R.id.notifyContactsButton);
        notifyContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                notifyContacts("theepicsniper3");
            }
        });

    }

    private void addCloseContact() {
        // Since emails are unique, and all are google, extract string before @
        // Because file directories can't take those symbols
        String userToken = userEmail.substring(0, userEmail.indexOf('@')).replaceAll("[\\-\\+\\.\\^:,]","");
        String key = mDatabase.child("users").push().getKey();

        Map<String, Object> postValues = new HashMap<>();

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        postValues.put("userID", userToken);
        postValues.put("closeContactEmail", "rina.kawamura@yale.edu");
        postValues.put("timestamp", timeStamp);

        DatabaseReference ref= mDatabase.child("users");
        ref.orderByChild("users").equalTo(userToken).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //create new user
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/users/" + key, postValues);
                mDatabase.updateChildren(childUpdates);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

    }

    void notifyContacts(String currentUserID) {

        // Retrieve data from database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()){
                        Object data = dataMap.get(key);
                        HashMap<String, Object> userData = (HashMap<String, Object>) data;

                        for (String key2 : userData.keySet()){
                            Object data2 = userData.get(key2);
                            HashMap<String, Object> userData2 = (HashMap<String, Object>) data2;

                            String userID = (String) userData2.get("userID");
                            String closeContactEmail = (String) userData2.get("closeContactEmail");
                            String timeStamp = (String) userData2.get("timestamp");
                            String currTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                            long diff;

                            try {
                                Date currDate = sdf.parse(currTimeStamp);
                                Date contactDate = sdf.parse(timeStamp);
                                diff = currDate.getTime() - contactDate.getTime();
                                int days = (int) diff / (1000 * 60 * 60 * 24);
                                // Only add to the closeContactEmails list if less than 2 weeks since contact
                                if (userID.equals(currentUserID) && days < 14){
                                    closeContactEmails.add(closeContactEmail);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }

                        }

                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        for (String s : closeContactEmails) {
            Log.w(TAG, "closeContactEmails: " + s);
        }
//        String temp[] = (String[]) closeContactEmails.toArray();

    }

}