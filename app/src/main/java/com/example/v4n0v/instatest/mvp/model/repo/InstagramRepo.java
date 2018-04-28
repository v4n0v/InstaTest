package com.example.v4n0v.instatest.mvp.model.repo;



import com.example.v4n0v.instatest.common.NetworkStatus;
import com.example.v4n0v.instatest.mvp.model.api.retrofit.ApiService;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IInstaRepoCahe;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperRepoCache;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class InstagramRepo {
    IInstaRepoCahe cache;
    @Inject
    ApiService api;

    public InstagramRepo(ApiService apiService) {
        this.api = apiService;
        cache = new PaperRepoCache();
    }

    public Observable<Instagram> getData(String token){
        if (NetworkStatus.isOnline()) {
            return api.getData(token)
                    .observeOn(AndroidSchedulers.mainThread())
            .map(instagram -> {
                cache.saveData(instagram);
                return instagram;
            });
        } else
        {
            return cache.loadData();
        }
    }
}
