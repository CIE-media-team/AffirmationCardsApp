package com.example.dylan.affirmationcardsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Info");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
