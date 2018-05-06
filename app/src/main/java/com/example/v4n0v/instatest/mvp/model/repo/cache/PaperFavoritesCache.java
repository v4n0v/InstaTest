package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class PaperFavoritesCache implements IFavoritesCache {

    @Override
    public Observable<Boolean> writeToFavorites(Datum data) {
        return Observable.fromCallable(() -> {
            List<Datum> favorites = Paper.book("favorites").read("all");
            if (favorites != null) {
                // проверяем был ли ранее добавлен элемент
                boolean isWritten = false;
                for (int i = 0; i < favorites.size(); i++) {
                    if (data.getId().equals(favorites.get(i).getId())) {
                        isWritten = true;
                        continue;
                    }
                }
                // если нет, добавляем и пишем в базу
                if (!isWritten) {
                    favorites.add(data);
                }

            } else {
                favorites = new ArrayList<>();
                favorites.add(data);
            }
            Paper.book("favorites").write("all", favorites);
            Timber.d("Added to favorites");
            return true;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> removeFromFavorites(Datum data) {
        return Observable.fromCallable(() -> {
            List<Datum> favorites = Paper.book("favorites").read("all");
            for (int i = 0; i < favorites.size(); i++) {
                if (data.getId().equals(favorites.get(i).getId())) {
                    favorites.remove(i);
                }
            }
            Paper.book("favorites").write("all", favorites);
            Timber.d("Removed favorites");
            return true;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Datum>> readFromFavorites() {
        return Observable.fromCallable(() -> {
            List<Datum> favorites = Paper.book("favorites").read("all");
            if (favorites == null) {
                favorites = new ArrayList<>();
            }
            Timber.d("Favorites loaded from paper cache");
            return favorites;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Instagram> verifyFavorites(Instagram instagram) {
        return Observable.fromCallable(() -> {
            List<Datum> favorites = Paper.book("favorites").read("all");
            if (favorites != null) {
                for (Datum data : instagram.getData()) {
                    for (Datum favorite : favorites) {
                        if (data.getId().equals(favorite.getId())) {
                            data.setFavorite(true);
                        }
                    }
                }
            }
            return instagram;
        }).subscribeOn(Schedulers.io());
    }
}
