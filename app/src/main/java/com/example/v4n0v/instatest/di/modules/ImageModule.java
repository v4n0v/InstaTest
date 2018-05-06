package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.images.GlideLoader;
import com.example.v4n0v.instatest.images.ImageCache;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.images.RealmImageCache;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module(includes = CacheModule.class)
public class ImageModule {
    @Provides
    public ImageLoader imageLoader(@Named("realm-image") ImageCache imageCache){
        return new GlideLoader(imageCache);
    }


}
