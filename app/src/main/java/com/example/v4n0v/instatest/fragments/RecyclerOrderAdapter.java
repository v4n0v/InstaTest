package com.example.v4n0v.instatest.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v4n0v.instatest.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by v4n0v on 16.03.18.
 */

class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder> {

    private List<Photo> elements;

    private SimpleDateFormat dateFormat;
    private final String template = "dd. MMM HH:mm";

    public RecyclerOrderAdapter(List<Photo> elements) {
        this.elements = elements;

        dateFormat = new SimpleDateFormat(template, Locale.getDefault());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerOrderAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(position);

    }

    @Override
    public int getItemCount() {
        return (null != elements ? elements.size() : 0);
    }

    /**
     * View holder to display each RecylerView itemDescription
     */
    protected class ViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener{
        private TextView itemDescription;
        private ImageView itemPhoto;
        private ImageView itemFav;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_photo_card, parent, false));
            itemView.setOnLongClickListener(this);
            itemPhoto = (ImageView) itemView.findViewById(R.id.item_photo);
            itemDescription = (TextView) itemView.findViewById(R.id.item_description);
            itemFav = (ImageView) itemView.findViewById(R.id.item_fav_added);
        }

        void bind(int position) {
            if (elements.size() > 0) {
                itemDescription.setText(elements.get(position).getDescription());
                itemPhoto.setImageResource(elements.get(position).getImage());
               // itemFav.setText(String.valueOf(elements.get(position).getSumm())+"p");
            }
        }



        @Override
        public boolean onLongClick(View view) {

            Toast.makeText(view.getContext(), " удален", Toast.LENGTH_SHORT).show();
            elements.remove(getLayoutPosition());
            notifyDataSetChanged();
            return false;
        }
    }


}
