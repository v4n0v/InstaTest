package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.mvp.model.api.retrofit.ApiService;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль репозиториев InstagramRepo
 */
@Module(includes = ApiModule.class)
public class RepoModule {

    @Provides
    public InstagramRepo instagramRepo(ApiService apiService){
        return new InstagramRepo(apiService);
    }
}
