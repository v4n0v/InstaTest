package com.example.v4n0v.instatest;

import android.app.Application;

import com.example.v4n0v.instatest.di.AppComponent;
import com.example.v4n0v.instatest.di.DaggerAppComponent;
import com.example.v4n0v.instatest.di.modules.AppModule;

import io.paperdb.Paper;
import timber.log.Timber;


public class App extends Application {

    private static App app;
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static App getInstance() {
        return app;
    }
}
