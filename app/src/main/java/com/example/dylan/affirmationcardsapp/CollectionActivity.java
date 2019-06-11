package com.example.dylan.affirmationcardsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class CollectionActivity extends AppCompatActivity {
    public static final String SORTED_BY_FAVORITES = "sorted_by_favorites";

    private CaptionedImagesAdapter adapter;
    private boolean sortByFavorites;
    private static int counter = 0;

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


        // Initialize the RecyclerView and configure its adapter
        RecyclerView cardRecycler = findViewById(R.id.building_recycler);
        adapter = new CaptionedImagesAdapter();
        cardRecycler.setAdapter(adapter);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int number = 0;


        number = (int) dpWidth / 91;


        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);
        View v = null;
        if (getIntent().getBooleanExtra("delete", false)) {
            removeCard(v);


        }


            // Get the stored state of the "Sort By Favorites" button
        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);

            if (sortByFavorites) {
                ImageButton heartView = findViewById(R.id.sortFavorite);
                heartView.setImageResource(R.drawable.fave);
            }

            loadCards(sortByFavorites);
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

        // Let the database do the searching. Get cards that are Free or Owned.
        QueryBuilder<Card> cardQuery = cardBox.query()
                .equal(Card_.free, true)
                .or()
                .equal(Card_.owned, true);

        // If sorting by favorites, lets also "order by Favorite descending". This will sort
        // by the "Favorite" property, and True comes after False, so we use descending.
        if(sortedByFavorites){
            cardQuery = cardQuery.orderDesc(Card_.favorite);

        }
        List<Card> cardList = cardQuery.build().find();

        // Set the adapter with the arranged list of cards. Then tell it to update the UI.
        adapter.setImages(cardList);
        adapter.notifyDataSetChanged();

    }


    public void sortByFavorites(View view){
        //NEED TO MAKE IT SO HEART CHANGES TO SOLID #########################################
        //12:15-2:30


        //if the heart is currently not sortByFavorites, make it sortByFavorites
        ImageButton heartView = findViewById(R.id.sortFavorite);


        //change whether the heart is filled or not
        if(!sortByFavorites) {
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
            sortByFavorites = true;
        }
        else{
            heartView.setImageResource(R.drawable.fave);
            sortByFavorites = false;
        }
        // Save the user's choice so it will be remembered when the Activity is recreated
        SharedPreferences.Editor editor = getSharedPreferences("sort", MODE_PRIVATE).edit();
        editor.putBoolean(SORTED_BY_FAVORITES, sortByFavorites);
        editor.apply();

        loadCards(sortByFavorites);
    }
    public void addCard(View view){
        Intent i = new Intent(this, AddCard.class);
        startActivity(i);

    }
    public void removeCard(View view){
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


    public static String getReset() {
        return SORTED_BY_FAVORITES;
    }


}
