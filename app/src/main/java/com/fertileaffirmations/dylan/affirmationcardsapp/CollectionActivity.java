package com.fertileaffirmations.dylan.affirmationcardsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class CollectionActivity extends AppCompatActivity {
    public static final String SORTED_BY_FAVORITES = "sorted_by_favorites";

    private static int counter = 0;
    private CaptionedImagesAdapter adapter;
    private boolean sortByFavorites;
    SharedPreferences prefs = null;
    String imageType;
    RecyclerView cardRecycler;
    LinearLayoutManager layoutManager;
    TextView tv1, tv2;

    static Menu menu;
    MenuItem mi;
    static CollectionActivity c;
    boolean front = false;
    boolean minusSelected;
    int image;

    public static String getReset() {
        return SORTED_BY_FAVORITES;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Typeface font = Typeface.createFromAsset(getAssets(), "font.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("My Collection");
        title.setTypeface(font2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv3 = findViewById(R.id.error);
        tv3.setTypeface(font2);

        // Initialize the RecyclerView and configure its adapter
        cardRecycler = findViewById(R.id.building_recycler);

        prefs = getSharedPreferences("CardType", Context.MODE_PRIVATE);

        imageType = prefs.getString("style", "porcelain");

        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv1.setTypeface(font2);
        tv2.setTypeface(font2);

        //front = getSharedPreferences("flipPref", MODE_PRIVATE).getBoolean("front", false);

        if (imageType.equals("porcelain")) {
            image = (R.drawable.porcelainsmall);
        } else {
            image = (R.drawable.warmcardsmall);
        }
        c = this;


        adapter = new CaptionedImagesAdapter(image);

        adapter.setFront(false);

        cardRecycler.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this, 4);
        cardRecycler.setLayoutManager(layoutManager);
        View v = null;
        if (getIntent().getBooleanExtra("delete", false)) {
            removeCard(v);


        }



        // Get the stored state of the "Sort By Favorites" button
        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        adapter.notifyDataSetChanged();


        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float xInches= metrics.widthPixels/metrics.xdpi;

        float textsize = xInches / .24f;
        adapter.setTextsize(textsize);



    }

    public static CollectionActivity getC() {
        return c;
    }

    public boolean getMinusSelected() {
        return minusSelected;
    }

    @Override
    public void onResume() {
        // fetch updated data
        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        loadCards();


        super.onResume();

    }

    @Override
    protected void onPause() {
        final MenuItem mi = menu.findItem(R.id.flip);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mi.setIcon(R.drawable.flipback);

            }
        }, 500);


        super.onPause();
    }

    private void loadCards() {
        Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
        List<Card> cardList;
        TextView tv = findViewById(R.id.error);

        sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        front = getSharedPreferences("flipPref", MODE_PRIVATE).getBoolean("front", false);

        if (this.sortByFavorites) {
            ImageButton heartView = findViewById(R.id.sortFavorite);
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
        }
        // Let the database do the searching. Get cards that are Free or Owned.


        // If sorting by favorites, lets also "order by Favorite descending". This will sort
        // by the "Favorite" property, and True comes after False, so we use descending.
        if (minusSelected) {
            cardBox = App.getApp().getBoxStore().boxFor(Card.class);
            QueryBuilder<Card> cardQueryCF = cardBox.query()
                    .equal(Card_.created, true)
                    .equal(Card_.favorite, true);
            if (!this.sortByFavorites) {
                cardQueryCF = cardBox.query()
                        .equal(Card_.created,true);
                cardQueryCF = cardQueryCF.orderDesc(Card_.favorite);
            }
            cardList = cardQueryCF.build().find();

            if(cardList.size() == 0){
                tv.setText("");
            }



        } else  if (this.sortByFavorites) {
            //cardQuery = cardQuery.orderDesc(Card_.favorite);
            QueryBuilder<Card> cardQueryFavorites = cardBox.query()
                    .equal(Card_.favorite, true);
            cardList = cardQueryFavorites.build().find();
        } else {
            cardList = cardBox.getAll();
        }




        if (cardList.size() == 0) {
            if(this.minusSelected){
                if(!this.sortByFavorites){
                    tv.setText("You have no created cards!");
                }
                else {
                    tv.setText("You have no created cards favorited!");
                }
            }
            else{
                tv.setText("You have no cards favorited!");

            }
            tv.setVisibility(View.VISIBLE);

        } else {
            tv.setText("");
            tv.setVisibility(View.INVISIBLE);


        }
        // Set the adapter with the arranged list of cards. Then tell it to update the UI.


        adapter.setImages(cardList);
        adapter.setFront(front);
        adapter.setPreference(imageType);
        adapter.notifyDataSetChanged();


    }

    public void sortByFavorites(View view) {
        //NEED TO MAKE IT SO HEART CHANGES TO SOLID #########################################
        //12:15-2:30


        //if the heart is currently not sortByFavorites, make it sortByFavorites
        ImageButton heartView = findViewById(R.id.sortFavorite);
        mi.setIcon(R.drawable.flipback);


        //change whether the heart is filled or not
        if (!this.sortByFavorites) {
            heartView.setImageResource(R.drawable.ic_favorite_red_24dp);
            this.sortByFavorites = true;
        } else {
            heartView.setImageResource(R.drawable.fave);
            this.sortByFavorites = false;

        }
        // Save the user's choice so it will be remembered when the Activity is recreated
        SharedPreferences.Editor editor = getSharedPreferences("sort", MODE_PRIVATE).edit();
        editor.putBoolean(SORTED_BY_FAVORITES, this.sortByFavorites);
        editor.apply();
        front = false;



        loadCards();

    }

    public void addCard(View view) {
        Intent i = new Intent(this, AddCard.class);
        startActivity(i);

    }


    public void removeCard(View view) {
        mi.setIcon(R.drawable.flipback);
        this.sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
        TextView tv = findViewById(R.id.error);
        List<Card> cardList;

        if (counter % 2 == 0) {

            tv1.setText("Back to Collection");
            tv1.setGravity(Gravity.CENTER);

            minusSelected = true;
            Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
            if(!this.sortByFavorites){
                QueryBuilder<Card> cardQuery = cardBox.query()
                        .equal(Card_.created, true);
                cardList = cardQuery.build().find();

            }
            else{
                QueryBuilder<Card> cardQuery = cardBox.query()
                        .equal(Card_.created, true)
                        .equal(Card_.favorite,true);
                cardList = cardQuery.build().find();

            }


            if (cardList.size() == 0) {
                tv.setText("You have no created cards!");
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.INVISIBLE);

            }
            // Set the adapter with the arranged list of cards. Then tell it to update the UI.
            adapter.setImages(cardList);

            adapter.notifyDataSetChanged();
        } else {
            minusSelected = false;
            tv.setVisibility(View.INVISIBLE);

            loadCards();
            tv1.setText("My Creations");


        }

        counter += 1;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        CollectionActivity.menu = menu;

        getMenuInflater().inflate(R.menu.collectionoption, menu);
        mi = menu.findItem(R.id.flip);
        mi.setIcon(R.drawable.flipback);




        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String action;


        switch (item.getItemId()) {
            case R.id.flip:
                sortByFavorites = getSharedPreferences("sort", MODE_PRIVATE).getBoolean(SORTED_BY_FAVORITES, false);
                Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);


                if (!front) {

                    loadCards();
                    adapter.setFront(true);
                    front = true;
                    item.setIcon(R.drawable.flipfront);


                } else {
                    item.setIcon(R.drawable.flipback);

                    cardRecycler.setAdapter(adapter);
                    adapter.setFront(false);
                    front = false;


                }
                return true;
            case android.R.id.home:
                Log.d("Test", "back pressed");

                MenuItem mi = menu.findItem(R.id.flip);
                mi.setIcon(R.drawable.flipback);



            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
