package com.example.dylan.affirmationcardsapp;

import java.util.Date;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Card {
    private double createdId;
    private int cardID;

    static List<Integer> ids;

    private static boolean justReset = false;
    private int image;
    private boolean free = true;
    private boolean favorite;
    private boolean owned = true;
    private String text;
    private boolean created = false;
    @Id
    private long id;



    public Card(String text, boolean favorited) {

        this.text = text;
        this.created = true;
        this.image = R.drawable.ic_menu_send;
        this.favorite = favorited;
        createdId = (new Date()).getTime();
        Random rand = new Random();

        this.cardID = rand.nextInt(100000);


    }


    public Card() {
        //generateRandomId();

    }

    public Card(int image) {
        this.image = image;
        Random rand = new Random();

        this.cardID = rand.nextInt(100000);

    }


    public double getCreatedId() {
        return createdId;
    }

    public String getText() {
        return this.text;
    }

    public boolean getCreated() {
        return this.created;
    }

    public boolean isCreated() {
        return this.created;
    }

    public int getImage() {
        return this.image;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFree() {
        return this.free;
    }

    public boolean isFavorite() {
        return favorite;
    }

    //If the user clicks the favorite button, it will swap the value of the boolean favorite value.
    public void setFavorite(boolean b) {

        favorite = b;
        Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
        cardBox.put(this);
    }

    public boolean isOwned() {
        return owned;
    }

    public boolean getJustReset() {
        return justReset;
    }

    public void setJustReset(boolean b) {
        justReset = b;
    }

    public int getCardID() {
        return this.cardID;
    }
}


