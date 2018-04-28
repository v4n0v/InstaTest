package com.example.v4n0v.instatest.images;

import java.io.File;

/**
 * Created by v4n0v on 28.04.18.
 */

public interface ImageCache {
    void saveImage(String url, File file);
    File loadImage(String url);
}
