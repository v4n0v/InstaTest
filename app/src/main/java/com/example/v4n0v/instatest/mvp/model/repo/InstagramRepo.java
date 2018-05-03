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

    private IInstaRepoCahe cache;
    private ApiService api;

    public InstagramRepo(ApiService apiService, IInstaRepoCahe cache) {
        this.api = apiService;
        this.cache=cache;
    }

    public Observable<Instagram> getData(String token) {
        if (NetworkStatus.isOnline()) {
            return api.getData(token)
                    .subscribeOn(Schedulers.io())
                    .map(instagram -> {
                       // cache.saveData(instagram);
                        return instagram;
                    });
        } else {
            return cache.loadData();
        }
    }
}
