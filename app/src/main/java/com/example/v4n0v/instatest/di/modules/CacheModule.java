package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.images.ImageCache;
import com.example.v4n0v.instatest.images.RealmImageCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IInstagramRepoCahe;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperInstagramRepoCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль кеширования
 */

@Module
public class CacheModule {

    @Provides
    @Named("paper-insta")
    public IInstagramRepoCahe iInstaRepoCahe(){
        return new PaperInstagramRepoCache();
    }

    @Provides
    @Named("realm-image")
    public ImageCache imageCache(){
        return new RealmImageCache();
    }





}
