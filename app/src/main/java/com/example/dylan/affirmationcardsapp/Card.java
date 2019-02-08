package com.example.dylan.affirmationcardsapp;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Card {
    private int image;
    private boolean free = true;

    @Id
    private long id;

    public Card(int image, boolean isFree){
        this.image = image;
        this.free = isFree;
    }
    public Card() { }


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
}
