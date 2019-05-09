package com.programmer.jgallos.ma_s;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MySubjectsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subjects);
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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewSubjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAuth = FirebaseAuth.getInstance();
        String user_id =mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child(user_id + "_subjects");

        //Toast.makeText(MySubjectsActivity.this,mDatabase.toString(),Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<SubjectRecords, SubjectViewHolder> FBRA = new FirebaseRecyclerAdapter<SubjectRecords, SubjectViewHolder>(
                SubjectRecords.class,
                R.layout.subject_list,
                SubjectViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(SubjectViewHolder viewHolder, SubjectRecords model, int position) {

                viewHolder.setSubject(model.getSubject());

                if (model.getSubject().equals("default")) {
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                } else {
                    viewHolder.mView.setVisibility(View.VISIBLE);
                }

            }
        };
        recyclerView.setAdapter(FBRA);
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setSubject(String subject) {
            TextView subject_e = mView.findViewById(R.id.textViewSubject);
            subject_e.setText("Subject: " + subject);
        }

    }

}
