package com.example.dylan.affirmationcardsapp;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CollectionEnlarge extends AppCompatActivity {
    Drawable d;
    public static final String EXTRA_CARD_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        List<Card> cards = cardBox.getAll();
        ImageView cardView=findViewById(R.id.enlargedCard);
        int cardId = (Integer) getIntent().getExtras().get(EXTRA_CARD_ID);


        cardView.setImageResource(cards.get(cardId).getImage());

    }

    public void setDrawable(Drawable d){
        this.d = d;
    }


}
