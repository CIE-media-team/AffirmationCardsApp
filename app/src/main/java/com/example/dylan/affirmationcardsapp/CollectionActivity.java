package com.example.dylan.affirmationcardsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Card> cardBox = boxStore.boxFor(Card.class);
        List<Card> cardList = cardBox.getAll();


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

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(freeCardImages);
        cardRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);


    }

}
