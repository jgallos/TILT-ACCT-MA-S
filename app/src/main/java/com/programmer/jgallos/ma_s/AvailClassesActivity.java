package com.programmer.jgallos.ma_s;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;



public class AvailClassesActivity extends AppCompatActivity {

    //private Spinner subSpinner;


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

    }

    public void onClickSignin(View view) {



        Spinner spinnerContent = (Spinner)findViewById(R.id.subSpinner);

        final String subject = spinnerContent.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), subject, Toast.LENGTH_SHORT).show();

    }
}
