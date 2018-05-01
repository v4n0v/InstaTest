package com.example.v4n0v.instatest.images;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.v4n0v.instatest.common.NetworkStatus;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

public class GlideLoader implements ImageLoader<ImageView> {
    ImageCache cache;

    public GlideLoader(ImageCache cache) {
        this.cache = cache;
    }

    @Override
    public void loadInto(String url, ImageView container) {

        if (cache.loadImage(url) != null) {
            GlideApp.with(container.getContext())
                    .load(cache.loadImage(url))
                    .into(container);

        } else {
            if (NetworkStatus.isOnline()) {
                GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Timber.e(e, "Image load failed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {


                        cache.saveImage(url, resource);
                        return false;
                    }
                }).into(container);

            }

        }
    }


}
