package com.example.dylan.affirmationcardsapp;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CollectionEnlarge extends AppCompatActivity {
    Drawable d;
    public static final String EXTRA_CARD_ID = "id";
    private Card card;
    private LikeButton likeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        likeListener();

        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        List<Card> cards = cardBox.getAll();
        ImageView cardView=findViewById(R.id.enlargedCard);
        int cardId = (Integer) getIntent().getExtras().get(EXTRA_CARD_ID);

        card = cards.get(cardId);
        if(card.isFavorite()){
            likeButton.setLiked(true);
        }
        cardView.setImageResource(cards.get(cardId).getImage());


    }


    public void setDrawable(Drawable d){
        this.d = d;
    }


    public void likeListener(){
        likeButton = findViewById(R.id.heart_button);
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                card.setFavorite(true);
                TextView tv = findViewById(R.id.tv);

                tv.setText("" + card.isFavorite());
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                card.setFavorite(false);
                TextView tv = findViewById(R.id.tv);

                tv.setText("" + card.isFavorite());

            }
        });
    }




}
