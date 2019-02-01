package com.programmer.jgallos.ma_s;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;


public class ViewAttendance extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Android Development_attendance");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<AttendanceRecords, AttendanceViewHolder> FBRA = new FirebaseRecyclerAdapter<AttendanceRecords, AttendanceViewHolder>(
                AttendanceRecords.class,
                R.layout.attendance_list,
                AttendanceViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(AttendanceViewHolder viewHolder, AttendanceRecords model, int position) {
                final String attendance_key = getRef(position).getKey().toString();
                viewHolder.setDate(model.getDate());
                viewHolder.setSignin(model.getSignin());
                viewHolder.setSignout(model.getSignout());
                viewHolder.setUid(model.getUid());

                //Toast.makeText(ViewAcadActivity.this, model.getImageUrl().toString(), Toast.LENGTH_LONG).show();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
        recyclerView.setAdapter(FBRA);
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setDate(String adate) {
            TextView attendance_date = mView.findViewById(R.id.textDate);
            attendance_date.setText(adate);
        }

        public void setSignin(String signin_time) {
            TextView attendance_signin = mView.findViewById(R.id.textSigninTime);
            attendance_signin.setText(signin_time);
        }

        public void setSignout(String asignout) {
            TextView attendance_signout = mView.findViewById(R.id.textSignoutTime);
            attendance_signout.setText(asignout);
        }
        public void setUid(String uid) {
            TextView attendance_uid = mView.findViewById(R.id.textUid);
            attendance_uid.setText(uid);
        }


    }


}
