package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import io.reactivex.Observable;
import timber.log.Timber;


public class PaperInstagramRepoCache implements IInstagramRepoCahe {
    @Override
    public void saveData(Instagram instagram) {
        Timber.d("saveData on paper");
    }

    @Override
    public Observable<Instagram> loadData() {
        Timber.d("load data from paper");
        return null;
    }
}
