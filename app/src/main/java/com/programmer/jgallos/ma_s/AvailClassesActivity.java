package com.programmer.jgallos.ma_s;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.DatabaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AvailClassesActivity extends AppCompatActivity {

    private Button signinBtn;

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private DatabaseReference mDatabaseUsers;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //subSpinner = (Spinner)findViewById(R.id.subSpinner);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avail_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.subSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.available_classes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        signinBtn = (Button)findViewById(R.id.signinBtn);


        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AvailClassesActivity.this, "Signing-in...", Toast.LENGTH_SHORT).show();
                Spinner spinnerContent = (Spinner)findViewById(R.id.subSpinner);
                final String subject = spinnerContent.getSelectedItem().toString();
                databaseRef = database.getInstance().getReference().child(subject + "_attendance");
                final DatabaseReference newAttendance = databaseRef.push();

                //final DatabaseReference databaserefForTime = database.getInstance().getReference(".info/serverTimeOffset");

                /*databaserefForTime.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long offset = dataSnapshot.getValue(Long.class);
                        long estimatedServerTimeMs = System.currentTimeMillis() +offset;
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                        Date resultdate = new Date(estimatedServerTimeMs);
                        Toast.makeText(AvailClassesActivity.this, sdf.format(resultdate).toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }); */


                mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                       //Toast.makeText(AvailClassesActivity.this, timeconverted,Toast.LENGTH_SHORT).show();

                        //newAttendance.child("signin_time").setValue(ServerValue.TIMESTAMP);
                        newAttendance.child("uid").setValue(mCurrentUser.getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    startActivity(new Intent(AvailClassesActivity.this,ClassSessionActivity.class));
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    /* public void onClickSignin(View view) {



        Spinner spinnerContent = (Spinner)findViewById(R.id.subSpinner);

        final String subject = spinnerContent.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), subject, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AvailClassesActivity.this, ClassSessionActivity.class));
        finish();
    } */
}
