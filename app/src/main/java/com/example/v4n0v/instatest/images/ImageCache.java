package com.example.v4n0v.instatest.images;

import android.graphics.Bitmap;

import java.io.File;

/**
 * ImageCache interface
 * методы кеширования изображений
 */

public interface ImageCache {
    File saveImage(String url,  Bitmap bitmap);
    File loadImage(String url);
}
