package com.example.dylan.affirmationcardsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.like.LikeButton;

import io.objectbox.Box;

public class CollectionEnlarge extends AppCompatActivity {
    public static final String EXTRA_CARD_ID = "id";
    Drawable d;
    private Card card;
    private LikeButton likeButton;
    private Box<Card> cardBox;
    private ImageView heartView;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("My Card");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        heartView = findViewById(R.id.heartView);
        ImageView cardView = findViewById(R.id.enlargedCard);

        // The database id of the card is in the intent. Get the Card from ObjectBox based on the id
        long cardId = getIntent().getLongExtra(EXTRA_CARD_ID, -1);
        cardBox = App.getApp().getBoxStore().boxFor(Card.class);
        card = cardBox.get(cardId);
        TextView cardText = (TextView) findViewById(R.id.cardText);
        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        cardText.setTypeface(font);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;


        float textSize = dpWidth / 11.75510f;
        //cardText.setTextSize(textSize);

        float textWidth = dpWidth / 1.314468f;

        float textHeight = dpHeight / 1.9972526f;


        //  ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) cardText.getLayoutParams();
        //  params.height = (int)textHeight;
        // params.width = (int)textWidth;
        // cardText.setLayoutParams(params);

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

        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        }
        //cardView.setImageResource(CollectionActivity.imageIDs[cardId]);



    }


    public void setDrawable(Drawable d) {
        this.d = d;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.delete_card_menu, menu);
        this.menu = menu;
        if (!card.isCreated()) {
            menu.findItem(R.id.delete).setVisible(false);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action;
        final Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);


        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Delete Card");
                builder.setMessage("Are you sure you would like to delete this card?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                cardBox.remove(card);
                                Intent i = new Intent(CollectionEnlarge.this, CollectionActivity.class);

                                startActivity(i);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


//                Toast t = Toast.makeText(this, action,
//                        Toast.LENGTH_SHORT);
//                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
//                t.show();
//                finish();


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void favorite_button(View view) {

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
        cardBox.put(card);


        Toast.makeText(this, action,
                Toast.LENGTH_SHORT).show();
    }

    public void share_button(View view) {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        String shareBodyText = "Fertile Affirmations is a mindfulness based tool created to help motivate and support you during your family building journey. Check it out at http://fertileaffirmations.com/.";
        // i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wow!");
        i.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(i, "Choose sharing method"));
    }
}





