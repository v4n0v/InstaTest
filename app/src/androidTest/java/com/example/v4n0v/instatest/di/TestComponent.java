package com.example.v4n0v.instatest.di;

import com.example.v4n0v.instatest.InstagramInstrumentalTest;
import com.example.v4n0v.instatest.di.modules.CacheModule;
import com.example.v4n0v.instatest.di.modules.FavoritesModule;
import com.example.v4n0v.instatest.di.modules.InstagramRepoModule;

import dagger.Component;

/**
 * Dagger Instrumental Test Component
 */
@Component(modules = {InstagramRepoModule.class, CacheModule.class, FavoritesModule.class})
public interface TestComponent {
    void inject(InstagramInstrumentalTest test);
}
