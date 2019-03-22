package com.example.dylan.affirmationcardsapp;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CollectionActivity extends AppCompatActivity {
    private List<Card> cardList;
    private CaptionedImagesAdapter adapter;
    private boolean solid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        cardList = cardBox.getAll();
        int zzzi = 0;
        RecyclerView cardRecycler = (RecyclerView) findViewById(R.id.building_recycler);

        int[] cardImages = new int[cardList.size()];
        for (int i = 0; i < cardImages.length; i++) {
            if (cardList.get(i).isFree()) {
                cardImages[i] = cardList.get(i).getImage();
            }

        }
        int counter = 0;
        for (int i : cardImages) {
            if (i == 0) {
                continue;

            } else {
                counter += 1;

            }
        }
        int[] freeCardImages = new int[counter];
        int counter2 = 0;
        for (int i : cardImages) {
            if (i == 0) {
                continue;
            } else {
                freeCardImages[counter2] = i;
                counter2 += 1;
            }
        }

        adapter = new CaptionedImagesAdapter(freeCardImages);

        cardRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);


    }

    public void sortByFavorites(View view){
        //NEED TO MAKE IT SO HEART CHANGES TO SOLID #########################################
        //12:15-2:30

        //if the heart is currently not solid, make it solid
        ImageButton heartView = (ImageButton)findViewById(R.id.sortFavorite);

        if(solid == false){
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
            solid= true;
        }
        else{
            heartView.setImageResource(R.drawable.ic_unsortfavorite_24dp);
            solid = false;
        }
        List<Card> favoritedCards = new ArrayList<>();
        List<Card> unfavoritedCards = new ArrayList<>();
        List<Card> sortedList = new ArrayList<>();

        for(Card card: cardList){
            //this only matters if the user owns the specific card
            if(card.isOwned()){
                //if they own the card, check to see if it is favorited
                if(card.isFavorite()){
                    //if it is favorited, add it to the favorited list
                    favoritedCards.add(card);
                }
                else{
                    unfavoritedCards.add(card);
                }
            }
        }


        sortedList.addAll(favoritedCards);
        sortedList.addAll(unfavoritedCards);
        int[] listImageIDs = new int[sortedList.size()];
        int counter = 0;
        for(Card c: sortedList){
            listImageIDs[counter] = c.getImage();
            counter+=1;
        }


        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(listImageIDs);



        RecyclerView cardRecycler = (RecyclerView) findViewById(R.id.building_recycler);
        cardRecycler.removeAllViews();
        adapter.notifyDataSetChanged();

        cardRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);

    }
    public void addCard(View view){

    }
    public void removeCard(View view){

    }



}
