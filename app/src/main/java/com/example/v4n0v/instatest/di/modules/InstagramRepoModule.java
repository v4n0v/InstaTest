package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.mvp.model.api.retrofit.ApiService;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IInstagramRepoCahe;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль репозиториев InstagramRepo
 */
@Module(includes = {ApiModule.class, CacheModule.class})
public class InstagramRepoModule {

    @Provides
    public InstagramRepo instagramRepo(ApiService apiService, @Named("paper-insta")IInstagramRepoCahe cache){
        return new InstagramRepo(apiService, cache);
    }
}
