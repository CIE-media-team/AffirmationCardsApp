package com.fertileaffirmations.dylan.affirmationcardsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

import static com.fertileaffirmations.dylan.affirmationcardsapp.CollectionActivity.SORTED_BY_FAVORITES;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences prefs = null;
    boolean warm, porcelain;
    ImageView background;
    private boolean justReset = false;
    private boolean sortByFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        prefs = getSharedPreferences("CardType", Context.MODE_PRIVATE);
        String imageType = prefs.getString("style", "porcelain");

        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");
        NavigationView navigationView = findViewById(R.id.nav_view);


        //  String imageType = getIntent().getStringExtra("style");
        int image = 0;
        if (imageType.equals("warm")) {
            image = R.drawable.warmcard;
            warm = true;
        } else if (imageType.equals("porcelain")) {
            image = R.drawable.porcelain;
            porcelain = true;
        }

        background = findViewById(R.id.image);
        Glide
                .with(this)
                .load(image)
                .into(background);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //////////Menu icon here!!
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        toggle.setHomeAsUpIndicator(R.drawable.ic_view_list_green);
        //////////////

        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (true) {
//        } else {
//
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "italic.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());

        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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
            Intent i = new Intent(this, AddCard.class);
            startActivity(i);

        } else if (id == R.id.nav_collection) {
            Intent i = new Intent(this, CollectionActivity.class);

            SharedPreferences.Editor editor = getSharedPreferences("sort", MODE_PRIVATE).edit();
            editor.putBoolean(SORTED_BY_FAVORITES, false);
            editor.apply();

            startActivity(i);

        } else if (id == R.id.nav_getcard) {
            Intent i = new Intent(this, CardActivity.class);
            i.putExtra("Purpose", "new");
            startActivity(i);
        } else if (id == R.id.nav_info) {
            Intent i = new Intent(this, InstructionActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_remind) {

            try {


                Calendar calendar = Calendar.getInstance();

                // Here we set a start time of Tuesday the 17th, 6pm
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1, 8, 0, 0);
                calendar.setTimeZone(TimeZone.getDefault());

                long start = calendar.getTimeInMillis();

                // add three hours in milliseconds to get end time of 9pm


                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .setType("vnd.android.cursor.item/event")
                        .putExtra(CalendarContract.Events.TITLE, "Pull my daily Fertile Affirmation!")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Fertile Affirmations")
                        .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY")

                        // to specify start time use "beginTime" instead of "dtstart"
                        //.putExtra(Events.DTSTART, calendar.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, start + 10000)

                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            } catch (Exception e) {
            }


        } else if (id == R.id.nav_reset) {
            final DrawerLayout drawer = findViewById(R.id.drawer_layout);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Reset App");
            builder.setMessage("Are you sure you want to reset the app to default?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Box<Card> cardBox = App.getApp().getBoxStore().boxFor(Card.class);
                            cardBox.removeAll();
                            List<Card> cardList = App.getInitialCards();
                            cardBox.put(App.getInitialCards());
//                            QueryBuilder<Card> cardQuery = cardBox.query()
//                                    .equal(Card_.created, true);
//                            List<Card> cardList = cardQuery.build().find();
//
//
//                            for (Card card : cardList) {
//
//                                cardBox.remove(card);
//                            }
//                            QueryBuilder<Card> cardQuery2 = cardBox.query()
//                                    .equal(Card_.favorite, true);
//                            List<Card> cardList2 = cardQuery2.build().find();
//                            for (Card card : cardList2) {
//                                card.setFavorite(false);
//                            }
                            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                            editor.putBoolean(CollectionActivity.getReset(), false);
                            editor.apply();
                            prefs.edit().putBoolean("firstrun", true).apply();

                            justReset = true;


                            getSharedPreferences("sort", MODE_PRIVATE).edit().putBoolean(SORTED_BY_FAVORITES, false).apply();


                            String action = "Successfully reset app";


                            Toast t = Toast.makeText(getApplicationContext(), action,
                                    Toast.LENGTH_SHORT);
                            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                            t.show();


                            selectStyleAgain();


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

        } else if (id == R.id.nav_learnmore) {
            //http://fertileaffirmations.com/
            Uri uriUrl = Uri.parse("http://fertileaffirmations.com/");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        } else if (id == R.id.nav_purchase) {
            //http://fertileaffirmations.com/
            Uri uriUrl = Uri.parse("http://fertileaffirmations.com/shop");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
//        } else if (id == R.id.nav_preference) {
//
//            Intent i = new Intent(this, FirstRunActivity.class);
//            startActivity(i);

        } else if (id == R.id.nav_favorites) {

            Intent i = new Intent(this, CollectionActivity.class);
            SharedPreferences.Editor editor = getSharedPreferences("sort", MODE_PRIVATE).edit();
            editor.putBoolean(SORTED_BY_FAVORITES, true);
            editor.apply();
            i.putExtra("favorites", true);


            startActivity(i);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectStyleAgain() {
        if (justReset) {
            justReset = false;

            Intent i = new Intent(this, FirstRunActivity.class);
            startActivity(i);
        }
    }


}
