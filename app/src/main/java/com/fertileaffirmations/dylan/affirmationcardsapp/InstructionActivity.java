package com.fertileaffirmations.dylan.affirmationcardsapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class InstructionActivity extends AppCompatActivity {

    private ArrayList<Integer> pics = new ArrayList<>();
    private int counter = 0;
    PhotoView imageView;
    TextView textView;
    ImageView leftbutton, rightbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);


        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.numView);
        leftbutton = findViewById(R.id.left_button);
        rightbutton = findViewById(R.id.right_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Instructions");
        title.setTypeface(font2);
        textView.setTypeface(font);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pics.add(R.drawable.instructions1);
        pics.add(R.drawable.instructions2);
        pics.add(R.drawable.instructions3);
        pics.add(R.drawable.instructions4);
        pics.add(R.drawable.instructions5);
        pics.add(R.drawable.instructions6);
        pics.add(R.drawable.instructions7);


        leftbutton.setVisibility(View.INVISIBLE);
        imageView.setImageResource(pics.get(0));
        
//        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
//
//            @Override
//            public void onSwipeLeft() {
//
//                goRight(imageView);
//            }
//
//            @Override
//            public void onSwipeRight() {
//
//                goLeft(imageView);
//            }
//
//
//        });


    }

    public void goLeft(View view) {
        if (counter > 0) {
            counter -= 1;
            imageView.setImageResource(pics.get(counter));
            setText();


            if (counter == 0) {
                leftbutton.setVisibility(View.INVISIBLE);
            } else {
                rightbutton.setVisibility(View.VISIBLE);
            }

        }


    }

    public void goRight(View view) {

        if (counter < 6) {
            leftbutton.setVisibility(View.VISIBLE);
            counter += 1;

            imageView.setImageResource(pics.get(counter));
            setText();

            if (counter == 6) {
                rightbutton.setVisibility(View.INVISIBLE);
            } else {
                rightbutton.setVisibility(View.VISIBLE);
            }


        }

    }

    public void setText() {
        String text = Integer.toString(counter + 1) + " / 7";
        textView.setText(text);
    }


}
