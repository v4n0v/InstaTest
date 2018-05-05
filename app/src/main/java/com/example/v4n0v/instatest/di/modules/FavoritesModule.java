package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.mvp.model.repo.cache.IFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperFavoritesCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * FavoritesModule модуль, предоставляющий классы добавления\удаления\чтения избранного
 */
@Module
public class FavoritesModule {
    @Provides
    @Named("paper-favorites")
    public IFavoritesCache favoritesCache(){
        return new PaperFavoritesCache();
    }

}
