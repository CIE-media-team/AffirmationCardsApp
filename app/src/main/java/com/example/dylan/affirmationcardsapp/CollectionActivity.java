package com.example.dylan.affirmationcardsapp;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private RecyclerView cardRecycler;
    private int[] freeCardImages;
    private Box<Card> cardBox;
    private BoxStore boxStore;


    public static int[] imageIDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        boxStore = App.getApp().getBoxStore();
        cardBox = boxStore.boxFor(Card.class);
        cardList = cardBox.getAll();

        cardRecycler = (RecyclerView) findViewById(R.id.building_recycler);

        int[] cardImages = new int[cardList.size()];
        for (int i = 0; i < cardImages.length; i++) {
            if (cardList.get(i).isFree()) {
                cardImages[i] = cardList.get(i).getImage();
            }

        }
        int counter = 0;
        for (int i : cardImages) {
            if (i == 0) {
                //if there is no image
                continue;

            } else {
                counter += 1;

            }
        }
        freeCardImages = new int[counter];
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
        imageIDs = freeCardImages;
        cardRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);


    }


    public void sort(List<Card> cards){

        boxStore = App.getApp().getBoxStore();
        cardBox = boxStore.boxFor(Card.class);
        cardList = cardBox.getAll();

        int[] cardImages = new int[cards.size()];

        for (int i = 0; i < cardImages.length; i++) {
            if (cardList.get(i).isFree()) {
                cardImages[i] = cards.get(i).getImage();
            }

        }
        int counter = 0;
        //making a counter to find out how many images
        for (int i : cardImages) {
            if (i == 0) {
                continue;

            } else {
                counter += 1;

            }
        }
        int[] images = new int[counter];
        int counter2 = 0;
        for (int i : cardImages) {
            if (i == 0) {
                continue;
            } else {
                images[counter2] = i;
                counter2 += 1;
            }
        }


        int p = 0;
        for(Card c: cards){
            

            p+=1;
        }



        cardList = cards;

        freeCardImages = images;
        imageIDs = freeCardImages;

        cardBox.put(cardList);


        Log.d("Test",cardList.toString());

        List<Card> newList = cardBox.getAll();
        Log.d("Test", newList.toString());




       // CaptionedImagesAdapter.imageIds = cardImages;

        adapter.notifyItemRangeChanged(0,freeCardImages.length);



        CaptionedImagesAdapter newAdapter = new CaptionedImagesAdapter(cardImages);
        cardRecycler.swapAdapter(newAdapter,true);





    }



    public void sortByFavorites(View view){
        //NEED TO MAKE IT SO HEART CHANGES TO SOLID #########################################
        //12:15-2:30


        boxStore = App.getApp().getBoxStore();
        cardBox = boxStore.boxFor(Card.class);
        cardList = cardBox.getAll();

        //if the heart is currently not solid, make it solid
        ImageButton heartView = (ImageButton)findViewById(R.id.sortFavorite);

        //change whether the heart is filled or not
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
        sort(sortedList);
//        int[] listImageIDs = new int[sortedList.size()];
//        int counter = 0;
//        for(Card c: sortedList){
//            listImageIDs[counter] = c.getImage();
//            counter+=1;
//        }
//
//
//        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(listImageIDs);
//
//
//
//        RecyclerView cardRecycler = (RecyclerView) findViewById(R.id.building_recycler);
//        cardRecycler.removeAllViews();
//        adapter.notifyDataSetChanged();
//
//        cardRecycler.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
//        cardRecycler.setLayoutManager(layoutManager);

    }
    public void addCard(View view){

    }
    public void removeCard(View view){

    }



}
