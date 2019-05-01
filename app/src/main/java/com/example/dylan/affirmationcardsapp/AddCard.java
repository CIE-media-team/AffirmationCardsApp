package com.example.dylan.affirmationcardsapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;

public class AddCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        switch (item.getItemId()) {
            case R.id.close:
                finish();
            case R.id.check:
                EditText et = (EditText) findViewById(R.id.editText);
                ImageView iv = findViewById(R.id.image2);
                et.setDrawingCacheEnabled(true);
                iv.setDrawingCacheEnabled(true);

                Bitmap bgBitmap = Bitmap.createBitmap(iv.getDrawingCache());
                Bitmap bmp = Bitmap.createBitmap(et.getDrawingCache());
                Bitmap combined = combineImages(bgBitmap, bmp);


                try (FileOutputStream out = new FileOutputStream("testcard.png")) {
                    combined.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (IOException e) {
                    e.printStackTrace();
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public Bitmap combineImages(Bitmap background, Bitmap foreground) {

        int width = 0, height = 0;
        Bitmap cs;

        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height, true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground, 0, 0, null);

        return cs;
    }
}
