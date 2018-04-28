package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.images.GlideLoader;
import com.example.v4n0v.instatest.images.ImageCache;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.images.RealmImageCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class ImageModule {
    @Provides
    public ImageLoader imageLoader(@Named("realm") ImageCache imageCache){
        return new GlideLoader(imageCache);
    }

    @Provides
    @Named("realm")
    ImageCache imageCache(){
        return new RealmImageCache();
    }
}
