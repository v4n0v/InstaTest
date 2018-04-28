package com.example.v4n0v.instatest.images;

/**
 * Created by v4n0v on 28.04.18.
 */

public interface ImageLoader<T> {
    void loadInto(String url, T container);
}
