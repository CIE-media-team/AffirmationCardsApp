package com.example.dylan.affirmationcardsapp;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Card {
    private int image;
    private boolean free = true;
    private boolean favorite;

    @Id
    private long id;

    public Card(int image, boolean isFree){
        this.image = image;
        this.free = isFree;
    }
    public Card() { }


    //If the user clicks the favorite button, it will swap the value of the boolean favorite value.
    public void setFavorite(boolean b){

        favorite = b;
        Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
        cardBox.put(this);
    }


    public Card(int image){
        this.image = image;
    }

    public int getImage(){
        return this.image;
    }

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public boolean isFree(){
        return this.free;
    }
    public boolean isFavorite(){
        return favorite;
    }
}
