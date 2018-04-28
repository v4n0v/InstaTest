package com.example.v4n0v.instatest.di;

import com.example.v4n0v.instatest.di.modules.ApiModule;
import com.example.v4n0v.instatest.di.modules.AppModule;
import com.example.v4n0v.instatest.di.modules.ImageModule;
import com.example.v4n0v.instatest.di.modules.RepoModule;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.RecyclerImagesAdapter;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFeed;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageModule.class})
public interface AppComponent {

    void inject(FragmentFeed fragmentFeed);
    void inject(FeedPresenter feedPresenter);

}
