package com.example.dylan.affirmationcardsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class CardActivity extends AppCompatActivity {
    ImageView cardView;
    TextView cardText;
    List<Card> cards;
    Card card;
    EasyFlipView flipView;
    ImageView heartView;
    SharedPreferences prefs = null;
    ImageView cardback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        String purpose = getIntent().getStringExtra("Purpose");
        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");

        cardText = findViewById(R.id.cardText);
        cardback = findViewById(R.id.cardBack);

        prefs = getSharedPreferences("CardType", Context.MODE_PRIVATE);
        String imageType = prefs.getString("style", "porcelain");
        if (imageType.equals("porcelain")) {
            cardback.setImageResource(R.drawable.porcelain);
        } else {
            cardback.setImageResource(R.drawable.warmcard);
        }





        // Update the action bar title with the TypefaceSpan instance


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("My Reading");
        title.setTypeface(font2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cardText.setTypeface(font);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;


        float textSize = dpWidth / 11.75510f;
        cardText.setTextSize(textSize);

        float textWidth = dpWidth / 1.314468f;

        float textHeight = dpHeight / 1.9972526f;


        ViewGroup.LayoutParams params = cardText.getLayoutParams();
        // params.height = (int)textHeight;
        // params.width = (int)textWidth;
        //cardText.setLayoutParams(params);


        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        cards = cardBox.getAll();
        cardView = findViewById(R.id.imageCard);
        heartView = findViewById(R.id.heart_button);


        Random rand = new Random();
        int randNum = rand.nextInt(cards.size() - 1);
        card = cards.get(randNum);

        if (card.isCreated()) {
            Glide
                    .with(this)
                    .load(R.drawable.cardblank)
                    .into(cardView);


        } else {
            Glide
                    .with(this)
                    .load(card.getImage())
                    .into(cardView);

            cardText.setText(null);
        }


        flipView = findViewById(R.id.flipView);
        ImageView cardView = findViewById(R.id.imageCard);
        cardView.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          flipCard();
                                      }
                                  },
                700);


        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        }
    }

    public void flipCard() {
        cardView.setVisibility(View.VISIBLE);

        flipView.flipTheView();

        if (card.isCreated()) {

            cardText.postDelayed(new Runnable() {
                public void run() {
                    cardText.setText(card.getText());
                }
            }, 320);


        }



    }

    public void favorite_button(View view) {
        ImageView heartView = findViewById(R.id.heart_button);
        String action;
        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsortfavorite_24dp));
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
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        String shareBodyText = "I just received my Fertile Affirmation. You can to at fertileaffirmations.com!";
        // i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wow!");
        i.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(i, "Choose sharing method"));
    }

    public void setImage(View view) {
        ImageView iv = findViewById(R.id.imageCard);
        iv.setVisibility(View.VISIBLE);


        if (card.isCreated()) {
            cardText.setText(card.getText());
            Glide
                    .with(this)
                    .load(R.drawable.cardblank)
                    .into(cardView);


        } else {
            Glide
                    .with(this)
                    .load(card.getImage())
                    .into(cardView);

            cardText.setText(null);
        }

    }
}
