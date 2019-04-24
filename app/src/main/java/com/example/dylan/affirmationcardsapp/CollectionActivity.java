package com.example.dylan.affirmationcardsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;

public class CollectionActivity extends AppCompatActivity {
    private static final String SORTED_BY_FAVORITES = "sorted_by_favorites";

    private CaptionedImagesAdapter adapter;
    private boolean sortByFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the RecyclerView and configure its adapter
        RecyclerView cardRecycler = findViewById(R.id.building_recycler);
        adapter = new CaptionedImagesAdapter();
        cardRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);

        // Get the stored state of the "Sort By Favorites" button
        sortByFavorites = getPreferences(MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        if(sortByFavorites) {
            ImageButton heartView = (ImageButton) findViewById(R.id.sortFavorite);
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
        }

        loadCards(sortByFavorites);

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
        ImageButton heartView = (ImageButton)findViewById(R.id.sortFavorite);


        //change whether the heart is filled or not
        if(!sortByFavorites) {
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
            sortByFavorites = true;
        }
        else{
            heartView.setImageResource(R.drawable.ic_unsortfavorite_24dp);
            sortByFavorites = false;
        }
        // Save the user's choice so it will be remembered when the Activity is recreated
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putBoolean(SORTED_BY_FAVORITES, sortByFavorites);
        editor.apply();

        loadCards(sortByFavorites);
    }
    public void addCard(View view){

    }
    public void removeCard(View view){

    }



}
