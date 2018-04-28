package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App app(){
        return app;
    }

}