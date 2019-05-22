package com.example.dylan.affirmationcardsapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class AddCard extends AppCompatActivity {
    private boolean favorited = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Card");
        setContentView(R.layout.activity_add_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        EditText editText = findViewById(R.id.editText);
        editText.setTypeface(font);

        ImageView iv = findViewById(R.id.image2);

        Glide
                .with(this)
                .load(R.drawable.cardblank)
                .into(iv);


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;


        float textSize = dpWidth / 11.75510f;
        editText.setTextSize(textSize);

        float textWidth = dpWidth / 1.314468f;

        float textHeight = dpHeight / 1.9972526f;


        // params.height = (int)textHeight;
        //  params.width = (int)textWidth;
        // editText.setLayoutParams(params);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.cardmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action;
        Boolean close = true;
        int id = item.getItemId();
        Log.d("Tag", Integer.toString(item.getItemId()));
        Log.d("Tag", Integer.toString(findViewById(R.id.close).getId()));
        if (item.getItemId() == findViewById(R.id.close).getId()) {
            finish();
        } else if (item.getItemId() == findViewById(R.id.check).getId()) {
            EditText et = (EditText) findViewById(R.id.editText);
            String text = et.getText().toString();
            if (text.length() > 0) {
                Card newCard = new Card(text, favorited);
                App.getApp().getBoxStore().boxFor(Card.class).put(newCard);
                action = "Added the new card to your collection!";

            } else {
                action = "Card text cannot be empty!";
                close = false;
            }
            Toast t = Toast.makeText(this, action,
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();

            if (close) {
                finish();

            }


        }
        return super.onOptionsItemSelected(item);


    }
    public void favorite_button(View view) {
        ImageView heartView = findViewById(R.id.heart_button);
        String action;
        if (favorited) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsortfavorite_24dp));
            action = "The new card was removed from your favorites.";

        } else {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
            action = "The new card will be favorited!";
        }
        //if the button is clicked, it flips the favorite boolean value
        favorited = !favorited;


        Toast t = Toast.makeText(this, action,
                Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
        t.show();

    }

    public void share_button(View view) {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        String shareBodyText = "I created my own card on my journey to mindfulness with the Fertile Affirmations app. \n http://fertileaffirmations.com/";
        i.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(i, "Choose sharing method"));
    }



}
