package com.programmer.jgallos.ma_s;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NotificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private RecyclerView recyclerView;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mNotifDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewNotifs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mNotifDatabase = FirebaseDatabase.getInstance().getReference().child("Notifications");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };





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


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<NotificationRecords, NotificationActivity.NViewHolder> FBRA = new FirebaseRecyclerAdapter<NotificationRecords, NotificationActivity.NViewHolder>(
                NotificationRecords.class,
                R.layout.notif_list,
                NotificationActivity.NViewHolder.class,
                mNotifDatabase
        ) {
            @Override
            protected void populateViewHolder(NotificationActivity.NViewHolder viewHolder, NotificationRecords model, int position) {
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                //mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

               /* if (!mDatabaseUsers.toString().equals("https://ma-s-514f2.firebaseio.com/Users/" + model.getUid())){
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                } else {
                    viewHolder.mView.setVisibility(View.VISIBLE);
                } */

                final String notif_key = getRef(position).getKey().toString();

                viewHolder.setNdate(model.getNdate());
                viewHolder.setNtime(model.getNtime());
                viewHolder.setNnotif(model.getNnotif());


                //Toast.makeText(ViewAttendanceActivity.this, model.getSignout().toString(), Toast.LENGTH_LONG).show();
                viewHolder.mnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
        recyclerView.setAdapter(FBRA);
    }


    public static class NViewHolder extends RecyclerView.ViewHolder {
        View mnView;

        public NViewHolder(View itemView) {
            super(itemView);
            mnView=itemView;
        }

        public void setNdate(String ndate) {
            TextView notif_date = mnView.findViewById(R.id.textViewNotifDate);
            notif_date.setText("Date: " + ndate);
        }

        public void setNtime(String ntime) {
            TextView notif_time = mnView.findViewById(R.id.textViewNotifTime);
            notif_time.setText("Time: " + ntime);
        }

        public void setNnotif(String nnotif) {
            TextView notif = mnView.findViewById(R.id.textViewNotif);
            notif.setText(nnotif);
        }

    }

}
