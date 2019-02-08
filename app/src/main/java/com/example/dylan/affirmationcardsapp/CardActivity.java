package com.example.dylan.affirmationcardsapp;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class CardActivity extends AppCompatActivity {
    ImageView cardView;
    List<Card> cards;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        String purpose = getIntent().getStringExtra("Purpose");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        cards = cardBox.getAll();
        cardView=findViewById(R.id.imageCard);

        Random rand = new Random();
        int randNum = rand.nextInt(cards.size() - 1);
        Card chosenCard = cards.get(randNum);
        cardView.setImageResource(chosenCard.getImage());







    }
}
