package com.example.v4n0v.instatest.di;

import com.example.v4n0v.instatest.di.modules.InstaRepoTestModule;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;

import dagger.Component;

/**
 * Dagger AppComponent class
 */
@Component(modules = {InstaRepoTestModule.class})
public interface TestComponent {
    void inject (FeedPresenter presenter);
}
