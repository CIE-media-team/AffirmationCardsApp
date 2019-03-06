package com.example.dylan.affirmationcardsapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class App extends Application {

    private static App app;
    private static Box<Card> cardBox;
    private BoxStore boxStore;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        // Initialize the main data access object
        boxStore = MyObjectBox.builder().androidContext(App.this).build();

        // Get the wrapper (Box) for the Book table that lets us store Book objects
        cardBox = boxStore.boxFor(Card.class);
        //if the database hasn't been populated already, populate it
        cardBox.removeAll();
        if (cardBox.count() == 0) {
            List<Card> initialCards = new ArrayList<>();
            initialCards.add(new Card(R.drawable.red_joker));
            initialCards.add(new Card(R.drawable.queen_of_spades2));
            initialCards.add(new Card(R.drawable.queen_of_spades));
            initialCards.add(new Card(R.drawable.queen_of_hearts));
            initialCards.add(new Card(R.drawable.queen_of_diamonds2));
            initialCards.add(new Card(R.drawable.queen_of_diamonds));
            initialCards.add(new Card(R.drawable.queen_of_clubs2));
            initialCards.add(new Card(R.drawable.queen_of_clubs));
            initialCards.add(new Card(R.drawable.king_of_spades2));
            initialCards.add(new Card(R.drawable.king_of_spades));
            initialCards.add(new Card(R.drawable.king_of_hearts));
            initialCards.add(new Card(R.drawable.king_of_diamonds2));
            initialCards.add(new Card(R.drawable.king_of_diamonds));
            initialCards.add(new Card(R.drawable.king_of_clubs2));
            initialCards.add(new Card(R.drawable.king_of_clubs));
            initialCards.add(new Card(R.drawable.king_of_spades2));

            initialCards.add(new Card(R.drawable.king_of_clubs));
            initialCards.add(new Card(R.drawable.king_of_spades2));
            initialCards.add(new Card(R.drawable.jack_of_clubs));
            initialCards.add(new Card(R.drawable.king_of_clubs, false));
            initialCards.add(new Card(R.drawable.king_of_spades2, false));
            initialCards.add(new Card(R.drawable.jack_of_clubs, false));
            initialCards.add(new Card(R.drawable.king_of_clubs, false));
            initialCards.add(new Card(R.drawable.king_of_spades2, false));
            initialCards.add(new Card(R.drawable.jack_of_clubs, false));
            initialCards.add(new Card(R.drawable.king_of_clubs, false));
            initialCards.add(new Card(R.drawable.ace_of_clubs));


            // ObjectBox is smart enough to handle CRUD on Collections of entities
            cardBox.put(initialCards);
        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
