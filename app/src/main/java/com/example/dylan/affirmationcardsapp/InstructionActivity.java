package com.example.dylan.affirmationcardsapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InstructionActivity extends AppCompatActivity {

    private ArrayList<Integer> pics = new ArrayList<>();
    private int counter = 0;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);


        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.numView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Instructions");
        title.setTypeface(font2);
        textView.setTypeface(font);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pics.add(R.drawable.newinfocard);
        pics.add(R.drawable.instructions1);
        pics.add(R.drawable.instructions2);
        pics.add(R.drawable.instructions3);
        pics.add(R.drawable.instructions4);
        pics.add(R.drawable.instructions5);


    }

    public void goLeft(View view) {
        if (counter > 0) {
            counter -= 1;
            imageView.setImageResource(pics.get(counter));
            setText();
        }

    }

    public void goRight(View view) {

        if (counter < 5) {
            counter += 1;

            imageView.setImageResource(pics.get(counter));
            setText();


        }

    }

    public void setText() {
        String text = Integer.toString(counter + 1) + " / 6";
        textView.setText(text);
    }


}
