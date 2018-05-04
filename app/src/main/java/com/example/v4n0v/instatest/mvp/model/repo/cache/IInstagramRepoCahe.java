package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import io.reactivex.Observable;

public interface IInstagramRepoCahe {
    void saveData(Instagram instagram);
    Observable<Instagram> loadData();
}
