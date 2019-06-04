package com.example.dylan.affirmationcardsapp;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Card {
    private int image;
    private boolean free = true;
    private boolean favorite;
    private boolean owned = true;
    private String text;
    private boolean created = false;
    private static boolean justReset = false;


    @Id
    private long id;

    public Card(int image, boolean isFree) {
        this.image = image;
        this.free = isFree;


    }

    public Card(String text, boolean favorited) {

        this.text = text;
        this.created = true;
        this.image = R.drawable.ic_menu_send;
        this.favorite = favorited;


    }

    public Card() {
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
    public Card(int image) {
        this.image = image;

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

    public boolean isOwned(){
        return owned;
    }

    public void setJustReset(boolean b) {
        justReset = b;
    }

    public boolean getJustReset() {
        return justReset;
    }
}
