package com.example.dylan.affirmationcardsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

import static com.example.dylan.affirmationcardsapp.CollectionActivity.SORTED_BY_FAVORITES;

public class CollectionEnlarge extends AppCompatActivity {
    public static final String EXTRA_CARD_ID = "id";
    Drawable d;
    private Card card;
    private LikeButton likeButton;
    private Box<Card> cardBox;
    private ImageView heartView;
    private Menu menu;
    EasyFlipView flipView;
    ImageView cardView2;
    TextView cardText;
    ImageView cardback;
    List<Card> cards;
    ImageView cardView;
    long cardId;
    Boolean sortByFavorites;
    List<Card> cardList;
    int position;


    int favoritePosition = -1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);

        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");


        cardback = findViewById(R.id.cardBack);
        SharedPreferences prefs = getSharedPreferences("CardType", Context.MODE_PRIVATE);
        String imageType = prefs.getString("style", "porcelain");
        if (imageType.equals("porcelain")) {

            Glide
                    .with(cardback.getContext())
                    .load(R.drawable.porcelainround)
                    .into((ImageView) cardback.findViewById(R.id.cardBack));
            //cardback.setImageResource(R.drawable.porcelainround);


        } else {
            Glide
                    .with(cardback.getContext())
                    .load(R.drawable.warmround)
                    .into((ImageView) cardback.findViewById(R.id.cardBack));

            //cardback.setImageResource(R.drawable.warmround);
        }






        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("My Card");
        title.setTypeface(font2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        heartView = findViewById(R.id.heartView);
        cardView = findViewById(R.id.enlargedCard);

        // The database id of the card is in the intent. Get the Card from ObjectBox based on the id
        cardId = getIntent().getLongExtra(EXTRA_CARD_ID, -1);
        cardBox = App.getApp().getBoxStore().boxFor(Card.class);
        cards = cardBox.getAll();
        card = cardBox.get(cardId);
        cardText = findViewById(R.id.cardText);
        cardText.setTypeface(font);

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

        if (card.isFavorite()) {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
        }
        //cardView.setImageResource(CollectionActivity.imageIDs[cardId]);

        flipView = findViewById(R.id.flipView);


        flipView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {

                changeCard("left");
            }

            @Override
            public void onSwipeRight() {

                changeCard("right");
            }


        });


        cardView.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          flipCard();
                                      }
                                  },
                700);


        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);

        if (sortByFavorites) {
            //cardQuery = cardQuery.orderDesc(Card_.favorite);
            QueryBuilder<Card> cardQueryFavorites = cardBox.query()
                    .equal(Card_.favorite, true);
            cardList = cardQueryFavorites.build().find();
        } else {
            cardList = cardBox.getAll();
        }


        Card c = cardBox.get(cardId);

        for (Card card : cardList) {

            if (c.getCardID() == card.getCardID()) {
                position = cardList.indexOf(card);
                break;
            }
        }

    }

    public void changeCard(String direction) {

        boolean change = false;


        if (direction.equals("left")) {
            if (position < cardList.size() - 1) {
                cardId += 1;
                change = true;
                position += 1;

                if (sortByFavorites) {
                    card = cardList.get(position);
                }

            }
        } else if (direction.equals("right")) {
            if (position > 0) {
                cardId -= 1;
                position -= 1;
                change = true;
                if (sortByFavorites) {
                    card = cardList.get(position);
                }
            }
        }
        if (change) {

            if (!sortByFavorites) {
                card = cardBox.get(cardId);
            }
            if (card.isCreated()) {


                Glide
                        .with(this)
                        .load(R.drawable.cardblank)
                        .into(cardView);
                cardText.setText(card.getText());

            } else {


                Glide
                        .with(this)
                        .load(card.getImage())
                        .into(cardView);

                cardText.setText(null);
            }
            if (card.isFavorite()) {
                heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
            } else {
                heartView.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsortfavorite_24dp));

            }
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





