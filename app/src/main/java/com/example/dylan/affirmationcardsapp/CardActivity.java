package com.example.dylan.affirmationcardsapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class CardActivity extends AppCompatActivity {
    ImageView cardView;
    List<Card> cards;
    Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Get Card");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        String purpose = getIntent().getStringExtra("Purpose");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        TextView cardText = (TextView) findViewById(R.id.cardText);
        cardText.setTypeface(font);




        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        cards = cardBox.getAll();
        cardView = findViewById(R.id.imageCard);
        ImageView heartView = findViewById(R.id.heart_button);


        Random rand = new Random();
        int randNum = rand.nextInt(cards.size() - 1);
        card = cards.get(randNum);

        if (card.isCreated()) {
            cardText.setText(card.getText());
            cardView.setImageResource(R.drawable.template);

        } else {
            cardView.setImageResource(card.getImage());
            cardText.setText(null);
        }

        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        }

    }

    public void favorite_button(View view) {
        ImageView heartView = findViewById(R.id.heart_button);
        String action;
        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            action = "Card removed from favorites";

        } else {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
            action = "Card added to favorites";
        }
        //if the button is clicked, it flips the favorite boolean value
        card.setFavorite(!card.isFavorite());


        Toast t = Toast.makeText(this, action,
                Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
        t.show();
    }

    public void share_button(View view) {
        Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                + "/drawable/" + "ic_launcher");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }
}
