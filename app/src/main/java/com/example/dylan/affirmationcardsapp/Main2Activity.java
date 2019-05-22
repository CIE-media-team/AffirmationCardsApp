package com.example.dylan.affirmationcardsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Intent i = new Intent(this,AddCard.class);
            startActivity(i);

        } else if (id == R.id.nav_collection) {
            Intent i = new Intent(this, CollectionActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_getcard) {
            Intent i = new Intent(this, CardActivity.class);
            i.putExtra("Purpose", "new");
            startActivity(i);
        } else if (id == R.id.nav_info) {
            Intent i = new Intent(this, InstructionActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_reset) {
            final DrawerLayout drawer = findViewById(R.id.drawer_layout);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete Card");
            builder.setMessage("Are you sure you would like reset the app to default?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);

                            QueryBuilder<Card> cardQuery = cardBox.query()
                                    .equal(Card_.created, true);
                            List<Card> cardList = cardQuery.build().find();
                            for (Card card : cardList) {
                                if (cardBox.getAll().contains(card)) {
                                    cardBox.remove(card);
                                }
                            }


                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.nav_share) {

            Intent i = new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            String shareBodyText = "Fertile Affirmations is a mindfulness based tool created to help motivate and support you during your family building journey. Check it out at: \n http://fertileaffirmations.com/";
            // i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wow!");
            i.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(i, "Choose sharing method"));

        } else if (id == R.id.nav_learnmore){
            //http://fertileaffirmations.com/
            Uri uriUrl = Uri.parse("http://fertileaffirmations.com/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } else if (id == R.id.nav_purchase){
            //http://fertileaffirmations.com/
            Uri uriUrl = Uri.parse("http://fertileaffirmations.com/shop");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
