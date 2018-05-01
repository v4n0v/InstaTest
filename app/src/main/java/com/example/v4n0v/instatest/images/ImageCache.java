package com.example.v4n0v.instatest.images;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by v4n0v on 28.04.18.
 */

public interface ImageCache {
    void saveImage(String url,  Bitmap bitmap);
    File loadImage(String url);
}
