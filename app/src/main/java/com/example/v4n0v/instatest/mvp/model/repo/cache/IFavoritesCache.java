package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;

import java.util.List;

import io.reactivex.Observable;



public interface IFavoritesCache {
    void writeToFavorives(Datum data);
    void removeFromFavorives(Datum data);
    Observable<List<Datum>> readFromFavorites();
}
