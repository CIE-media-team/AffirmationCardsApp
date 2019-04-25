package com.example.dylan.affirmationcardsapp;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private List<Card> images;

    void setImages(List<Card> images) {
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.cardView);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), images.get(position).getImage());

        imageView.setImageDrawable(drawable);


        //
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), CollectionEnlarge.class);

                // Instead of sending the position, send the database id in the intent.
                long id = CaptionedImagesAdapter.this.images.get(position).getId();
                intent.putExtra(CollectionEnlarge.EXTRA_CARD_ID, id);
                Log.d("testing", Long.toString(id));

                cardView.getContext().startActivity(intent);


            }


        });


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
