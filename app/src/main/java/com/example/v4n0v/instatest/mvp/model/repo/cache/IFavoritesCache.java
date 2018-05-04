package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import java.util.List;

import io.reactivex.Observable;



public interface IFavoritesCache {
    Observable<Boolean> writeToFavorites(Datum data);
    Observable<Boolean>removeFromFavorites(Datum data);
    Observable<List<Datum>> readFromFavorites();
    Observable<Instagram> verifyFavorites(Instagram instagram);

}
