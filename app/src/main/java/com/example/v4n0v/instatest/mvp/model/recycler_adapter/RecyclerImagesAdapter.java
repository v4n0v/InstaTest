package com.example.v4n0v.instatest.mvp.model.recycler_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Images;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class RecyclerImagesAdapter extends RecyclerView.Adapter<RecyclerImagesAdapter.ViewHolder> {
    public RecyclerImagesAdapter(ImageLoader imageLoader, IListPresenter presenter) {
        this.presenter = presenter;
        this.imgeLoader = imageLoader;
    }

    private IListPresenter presenter;

    ImageLoader<ImageView> imgeLoader;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getViewCount();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements IListImageRaw, View.OnClickListener {
        int pos = -1;
        @BindView(R.id.item_photo)
        ImageView photoImageView;
        @BindView(R.id.item_description)
        TextView descriptionTextView;

        @BindView(R.id.item_fav_added)
        ImageView favImageView;
        @BindView(R.id.item_comments_text_view)
        TextView commentsCountTextView;

        @BindView(R.id.item_likes_text_view)
        TextView likesCountTextView;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            favImageView.setOnClickListener(this);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setImageData(Datum data) {
            imgeLoader.loadInto(data.getImages().getStandardResolution().getUrl(), photoImageView);
            descriptionTextView.setText(data.getCaption().getText());
            likesCountTextView.setText(String.valueOf(data.getLikes().getCount()));
            commentsCountTextView.setText(String.valueOf(data.getComments().getCount()));
            int id;
            if (data.isFavorite()) {
                //   favImageView.setImageDrawable();

                id = R.drawable.ic_bookmark_24dp;
            } else {
                id = R.drawable.ic_bookmark_border_24dp;

            }

            favImageView.setImageResource(id);

        }

//        @Override
//        public void setWeather(Weather weather) {
//            String pressureInfo = weather.getPressure() + root.getResources().getString(R.string.pressure_dim);
//            String windInfo = weather.getWind() + root.getResources().getString(R.string.wind_dim);
//            String humInfo = weather.getHumidity() + root.getResources().getString(R.string.humidity_dim);
//
//            cityTV.setText(weather.getCity());
//            tempTV.setText(weatherDecorator.temperatureFormat(weather.getTemperature()));//  getString(R.string.cels));
//            pressureTV.setText(pressureInfo);
//            humidityTV.setText(humInfo);
//            windTV.setText(windInfo);
//            timeTV.setText(weatherDecorator.getLastUpdate(weather.getTime()));
//            int weatherIcon = weatherDecorator.getWeatherIcon(weather.getId());
//            Drawable drawable = ResourcesCompat.getDrawable(root.getContext().getResources(), weatherIcon, null);
//
//            img.setImageDrawable(drawable);
//        }


        @Override
        public void onClick(View view) {
            int pos = getLayoutPosition();
            Timber.d("click " + pos);
            presenter.selectItem(pos);
        }
    }
}
