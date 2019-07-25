package com.example.dylan.affirmationcardsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class CollectionActivity extends AppCompatActivity {
    public static final String SORTED_BY_FAVORITES = "sorted_by_favorites";
    private static int counter = 0;
    private CaptionedImagesAdapter adapter;
    private boolean sortByFavorites;
    SharedPreferences prefs = null;
    String imageType;
    RecyclerView cardRecycler;
    int flipcounter = 0;

    public static String getReset() {
        return SORTED_BY_FAVORITES;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("My Collection");
        title.setTypeface(font2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv3 = findViewById(R.id.error);
        tv3.setTypeface(font2);

        // Initialize the RecyclerView and configure its adapter
        cardRecycler = findViewById(R.id.building_recycler);

        prefs = getSharedPreferences("CardType", Context.MODE_PRIVATE);
        imageType = prefs.getString("style", "porcelain");
        int image;
        if (imageType.equals("porcelain")) {
            image = (R.drawable.porcelain);
        } else {
            image = (R.drawable.warmcard);
        }


        adapter = new CaptionedImagesAdapter(image);
        cardRecycler.setAdapter(adapter);


        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);
        View v = null;
        if (getIntent().getBooleanExtra("delete", false)) {
            removeCard(v);


        }



        // Get the stored state of the "Sort By Favorites" button
        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);


//        if (sortByFavorites) {
//            ImageButton heartView = findViewById(R.id.sortFavorite);
//            heartView.setImageResource(R.drawable.fave);
//        }

        //loadCards(sortByFavorites);
    }

    @Override
    public void onResume() {
        // fetch updated data
        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        loadCards(sortByFavorites);
        super.onResume();

    }

    private void loadCards(boolean sortedByFavorites) {
        Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
        List<Card> cardList;

        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);

        if (sortByFavorites) {
            ImageButton heartView = findViewById(R.id.sortFavorite);
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
        }
        // Let the database do the searching. Get cards that are Free or Owned.
        QueryBuilder<Card> cardQuery = cardBox.query()
                .equal(Card_.free, true)
                .or()
                .equal(Card_.owned, true);

        // If sorting by favorites, lets also "order by Favorite descending". This will sort
        // by the "Favorite" property, and True comes after False, so we use descending.
        if (sortedByFavorites) {
            //cardQuery = cardQuery.orderDesc(Card_.favorite);
            QueryBuilder<Card> cardQueryFavorites = cardBox.query()
                    .equal(Card_.favorite, true);
            cardList = cardQueryFavorites.build().find();


        } else {


            cardList = cardQuery.build().find();
        }
        TextView tv = findViewById(R.id.error);

        if (cardList.size() == 0) {

            tv.setText("You have no cards favorited!");
            tv.setVisibility(View.VISIBLE);

        } else {
            tv.setText("");
            tv.setVisibility(View.INVISIBLE);


        }

        // Set the adapter with the arranged list of cards. Then tell it to update the UI.
        adapter.setPreference(imageType);
        adapter.setImages(cardList);
        adapter.notifyDataSetChanged();

    }

    public void sortByFavorites(View view) {
        //NEED TO MAKE IT SO HEART CHANGES TO SOLID #########################################
        //12:15-2:30


        //if the heart is currently not sortByFavorites, make it sortByFavorites
        ImageButton heartView = findViewById(R.id.sortFavorite);


        //change whether the heart is filled or not
        if (!sortByFavorites) {
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
            sortByFavorites = true;
        } else {
            heartView.setImageResource(R.drawable.fave);
            sortByFavorites = false;
        }
        // Save the user's choice so it will be remembered when the Activity is recreated
        SharedPreferences.Editor editor = getSharedPreferences("sort", MODE_PRIVATE).edit();
        editor.putBoolean(SORTED_BY_FAVORITES, sortByFavorites);
        editor.apply();

        loadCards(sortByFavorites);
    }

    public void addCard(View view) {
        Intent i = new Intent(this, AddCard.class);
        startActivity(i);

    }

    public void removeCard(View view) {
        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        TextView tv = findViewById(R.id.error);

        if (counter % 2 == 0) {

            Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
            QueryBuilder<Card> cardQuery = cardBox.query()
                    .equal(Card_.created, true);
            if (sortByFavorites) {
                cardQuery = cardQuery.orderDesc(Card_.favorite);
            }
            List<Card> cardList = cardQuery.build().find();
            if (cardList.size() == 0) {
                tv.setText("You have no cards left to delete!");
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.INVISIBLE);

            }
            // Set the adapter with the arranged list of cards. Then tell it to update the UI.
            adapter.setImages(cardList);
            adapter.notifyDataSetChanged();
        } else {
            tv.setVisibility(View.INVISIBLE);

            loadCards(sortByFavorites);

        }

        counter += 1;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.collectionoption, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action;
        final Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);


        switch (item.getItemId()) {
            case R.id.flip:
                sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);


                flipcounter++;
                if (flipcounter % 2 == 1) {

                    List<Card> cardList;
                    if (sortByFavorites) {
                        //cardQuery = cardQuery.orderDesc(Card_.favorite);
                        QueryBuilder<Card> cardQueryFavorites = cardBox.query()
                                .equal(Card_.favorite, true);
                        cardList = cardQueryFavorites.build().find();
                    } else {
                        cardList = cardBox.getAll();
                    }

                    item.setIcon(R.drawable.flipfront);
                    CaptionedImagesAdapter newAdapter = new CaptionedImagesAdapter(cardList);
                    cardRecycler.setAdapter(newAdapter);
                    newAdapter.setFront(true);


                    LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
                    cardRecycler.setLayoutManager(layoutManager);
                } else {
                    item.setIcon(R.drawable.flipback);

                    cardRecycler.setAdapter(adapter);
                    adapter.setFront(false);


                    LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
                    cardRecycler.setLayoutManager(layoutManager);
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
