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
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));







            // ObjectBox is smart enough to handle CRUD on Collections of entities
            cardBox.put(initialCards);
        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
