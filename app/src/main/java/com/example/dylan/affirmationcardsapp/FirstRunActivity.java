package com.example.dylan.affirmationcardsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FirstRunActivity extends AppCompatActivity {

    //                <category android:name="android.intent.category.LAUNCHER" />

    SharedPreferences prefs = null;
    private boolean warm = false;
    private boolean porcelain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "italic.otf");
        TextView tv = findViewById(R.id.preferenceView);
        tv.setTypeface(font2);

        // Perhaps set content view here


        prefs = getSharedPreferences("CardType", Context.MODE_PRIVATE);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (prefs.getBoolean("firstrun", true)) {
//            // Do first run stuff here then set 'firstrun' as false
//            // using the following line to edit/commit prefs
//
//
//        } else {
//
//            Intent i = new Intent(this, Main2Activity.class);
//            i.putExtra("style", prefs.getString("style", "porcelain"));
//
//            startActivity(i);
//        }
//    }

    public void warmClicked(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("style", "warm");


        prefs.edit().putString("style", "warm").apply();

        prefs.edit().putBoolean("firstrun", false).apply();

        startActivity(i);
    }

    public void porcelainClicked(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("style", "porcelain");


        prefs.edit().putBoolean("firstrun", false).apply();
        prefs.edit().putString("style", "porcelain").apply();

        startActivity(i);


    }


}
