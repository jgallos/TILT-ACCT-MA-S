package com.programmer.jgallos.ma_s;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class SingleFeedbackActivity extends AppCompatActivity {

    private TextView singleDate, singleLevel, singleDesc, singleStatus, singleUID;
    String post_key = null;
    private DatabaseReference mDatabase;
    private Button replyBtn, scalateBtn, resolvedBtn;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private DatabaseReference mReplyDatabase;
    private DatabaseReference escalateDatabaseRef;
    private DatabaseReference escalateMarkerRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;

    private DatabaseReference mDatabaseUsers;

    String signin_subject = null;
    String uniLevel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_feedback);

        singleDate = (TextView)findViewById(R.id.sfeedbackDate);
        singleLevel = (TextView)findViewById(R.id.sfeedbackLevel);
        singleDesc = (TextView)findViewById(R.id.sfeedbackDesc);
        singleStatus = (TextView)findViewById(R.id.sfeedbackStatus);
        singleUID = (TextView)findViewById(R.id.sfeedbackUid);

        signin_subject = getIntent().getExtras().getString("SigninSubject");
        mDatabase = FirebaseDatabase.getInstance().getReference().child(signin_subject + "_feedback");
        post_key = getIntent().getExtras().getString("FeedbackID");
        escalateDatabaseRef = FirebaseDatabase.getInstance().getReference().child(signin_subject + "_feedback").child(post_key);
        escalateMarkerRef = FirebaseDatabase.getInstance().getReference().child("Reply_" + post_key);
        //Toast.makeText(SinglePostActivity.this,post_key.toString(),Toast.LENGTH_LONG).show();
        replyBtn = (Button)findViewById(R.id.buttonReply);
        scalateBtn = (Button)findViewById(R.id.buttonScalate);
        resolvedBtn = (Button)findViewById(R.id.buttonResolved);
        mAuth = FirebaseAuth.getInstance();
        replyBtn.setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewSingleFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mReplyDatabase = FirebaseDatabase.getInstance().getReference().child("Reply_" + post_key);

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String feedback_level = (String) dataSnapshot.child("level").getValue();
                String feedback_desc = (String) dataSnapshot.child("desc").getValue();
                String feedback_status = (String) dataSnapshot.child("status").getValue();
                String feedback_uid = (String) dataSnapshot.child("uid").getValue();
                String feedback_username = (String) dataSnapshot.child("username").getValue();

                uniLevel = feedback_level;

                singleDate.setText("");
                singleLevel.setText("Level: " + feedback_level);
                singleDesc.setText("Feedback: " + feedback_desc);
                singleStatus.setText("Status: " + feedback_status);
                singleUID.setText("(remove later): " + feedback_uid);
                if (mAuth.getCurrentUser().getUid().equals(feedback_uid)){

                    replyBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        scalateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final DatabaseReference newMarker = escalateMarkerRef.push();
               String newLevel = null;
                Map<String, Object> updateLevel = new HashMap<String, Object>();

                if (uniLevel.equals("DEFCON 5")) {
                    updateLevel.put("level", "DEFCON 4");
                    newLevel = "DEFCON 4";
                } else if (uniLevel.equals("DEFCON 4")) {
                    updateLevel.put("level", "DEFCON 3");
                    newLevel = "DEFCON 3";
                } else if (uniLevel.equals("DEFCON 3")) {
                    updateLevel.put("level", "DEFCON 2");
                    newLevel = "DEFCON 2";
                } else if (uniLevel.equals("DEFCON 2")){
                    updateLevel.put("level", "DEFCON 1");
                    newLevel = "DEFCON 1";
                }

                if (!uniLevel.equals("DEFCON 1")) {
                    escalateDatabaseRef.updateChildren(updateLevel);
                    newMarker.child("reply").setValue("Feedback escalated to: " + newLevel);
                    newMarker.child("date").setValue("x");
                    newMarker.child("time").setValue("x").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }


            }
        });

        resolvedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference newMarker = escalateMarkerRef.push();
                Map<String, Object> updateStatus = new HashMap<String, Object>();

                updateStatus.put("status", "Resolved.");
                escalateDatabaseRef.updateChildren(updateStatus);

                newMarker.child("reply").setValue("Feedback marked as resolved.");
                newMarker.child("date").setValue("x");
                newMarker.child("time").setValue("x").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleFeedbackActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickReply(View view) {
        Intent singleReply = new Intent(this, FeedbackReplyActivity.class);
        singleReply.putExtra("SingleFeedbackID", post_key);
        singleReply.putExtra("SigninSubject", signin_subject);
        startActivity(singleReply);
        //finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<ReplyRecords, SingleFeedbackActivity.SFBViewHolder> FBRA = new FirebaseRecyclerAdapter<ReplyRecords, SingleFeedbackActivity.SFBViewHolder>(
                ReplyRecords.class,
                R.layout.reply_list,
                SingleFeedbackActivity.SFBViewHolder.class,
                mReplyDatabase
        ) {
            @Override
            protected void populateViewHolder(SingleFeedbackActivity.SFBViewHolder viewHolder, ReplyRecords model, int position) {
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                //mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

               /* if (!mDatabaseUsers.toString().equals("https://ma-s-514f2.firebaseio.com/Users/" + model.getUid())){
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                } else {
                    viewHolder.mView.setVisibility(View.VISIBLE);
                } */

                final String reply_key = getRef(position).getKey().toString();

                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setReply(model.getReply());


                //Toast.makeText(ViewAttendanceActivity.this, model.getSignout().toString(), Toast.LENGTH_LONG).show();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
        recyclerView.setAdapter(FBRA);
    }


    public static class SFBViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public SFBViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setDate(String date) {
            TextView response_date = mView.findViewById(R.id.textViewResponseDate);
            response_date.setText("Date: " + date);
        }

        public void setTime(String time) {
            TextView response_time = mView.findViewById(R.id.textViewResponseTime);
            response_time.setText("Time: " + time);
        }

        public void setReply(String reply) {
            TextView response_reply = mView.findViewById(R.id.textViewResponse);
            response_reply.setText(reply);
        }

    }

}
