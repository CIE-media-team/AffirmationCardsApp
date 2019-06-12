package com.example.dylan.affirmationcardsapp;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    CardView cardView;
    private List<Card> images;

    void setImages(List<Card> images) {
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Card c = images.get(position);
        TextView tv = holder.cardView.findViewById(R.id.cardText);

        if (c.isCreated()) {
            tv.setText(c.getText());

            Glide
                    .with(holder.cardView.getContext())
                    .load(R.drawable.cardblank)
                    .into((ImageView) holder.cardView.findViewById(R.id.cardView));


        } else {
            //holder.imageView.setImageResource(images.get(position).getImage());
            tv.setText("");

            Glide
                    .with(holder.cardView.getContext())
                    .load(c.getImage())
                    .into((ImageView) holder.cardView.findViewById(R.id.cardView));
        }
        final CardView cardView = holder.cardView;


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), CollectionEnlarge.class);

                // Instead of sending the position, send the database id in the intent.
                long id = CaptionedImagesAdapter.this.images.get(position).getId();
                intent.putExtra(CollectionEnlarge.EXTRA_CARD_ID, id);

                cardView.getContext().startActivity(intent);


            }


        });


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            imageView = v.findViewById(R.id.cardView);
            Typeface font = Typeface.createFromAsset(cardView.getContext().getAssets(), "font.otf");
            ((TextView) v.findViewById(R.id.cardText)).setTypeface(font);

        }
    }
}
