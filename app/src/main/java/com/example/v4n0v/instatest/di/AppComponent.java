package com.example.v4n0v.instatest.di;

import com.example.v4n0v.instatest.di.modules.AppModule;
import com.example.v4n0v.instatest.di.modules.CacheModule;
import com.example.v4n0v.instatest.di.modules.FavoritesModule;
import com.example.v4n0v.instatest.di.modules.ImageModule;
import com.example.v4n0v.instatest.di.modules.InstagramRepoModule;
import com.example.v4n0v.instatest.mvp.presenters.FavoritesPresenter;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFavorites;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFeed;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, InstagramRepoModule.class, ImageModule.class, FavoritesModule.class})
public interface AppComponent {

    void inject(FragmentFeed fragmentFeed);
    void inject(FragmentFavorites fragmentFavorites);
    void inject(FeedPresenter feedPresenter);
    void inject(FavoritesPresenter favoritesPresenter);

}
