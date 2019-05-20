package com.example.dylan.affirmationcardsapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Card");
        setContentView(R.layout.activity_add_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        ((EditText) findViewById(R.id.editText)).setTypeface(font);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.cardmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action;
        switch (item.getItemId()) {
            case R.id.close:
                finish();
            case R.id.check:
                EditText et = (EditText) findViewById(R.id.editText);
                String text = et.getText().toString();
                if (text.length() > 0) {
                    Card newCard = new Card(text);
                    App.getApp().getBoxStore().boxFor(Card.class).put(newCard);
                    action = "Added the new card to your collection!";

                } else {
                    action = "Card text cannot be empty!";
                }
                Toast t = Toast.makeText(this, action,
                        Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();
                finish();



            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
