package com.programmer.jgallos.ma_s;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFeedbackActivity extends AppCompatActivity {

    private TextView singleDate, singleLevel, singleDesc, singleStatus, singleUID;
    String post_key = null;
    private DatabaseReference mDatabase;
    private Button replyBtn, scalateBtn, resolvedBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_feedback);

        singleDate = (TextView)findViewById(R.id.sfeedbackDate);
        singleLevel = (TextView)findViewById(R.id.sfeedbackLevel);
        singleDesc = (TextView)findViewById(R.id.sfeedbackDesc);
        singleStatus = (TextView)findViewById(R.id.sfeedbackStatus);
        singleUID = (TextView)findViewById(R.id.sfeedbackUid);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Android Development_feedback");
        post_key = getIntent().getExtras().getString("FeedbackID");
        //Toast.makeText(SinglePostActivity.this,post_key.toString(),Toast.LENGTH_LONG).show();
        replyBtn = (Button)findViewById(R.id.buttonReply);
        scalateBtn = (Button)findViewById(R.id.buttonScalate);
        resolvedBtn = (Button)findViewById(R.id.buttonResolved);
        mAuth = FirebaseAuth.getInstance();
        replyBtn.setVisibility(View.INVISIBLE);



        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String feedback_level = (String) dataSnapshot.child("level").getValue();
                String feedback_desc = (String) dataSnapshot.child("desc").getValue();
                String feedback_status = (String) dataSnapshot.child("status").getValue();
                String feedback_uid = (String) dataSnapshot.child("uid").getValue();
                String feedback_username = (String) dataSnapshot.child("username").getValue();

                singleLevel.setText(feedback_level);
                singleDesc.setText(feedback_desc);
                singleStatus.setText(feedback_status);
                singleUID.setText(feedback_uid);
                if (mAuth.getCurrentUser().getUid().equals(feedback_uid)){

                    replyBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

}
