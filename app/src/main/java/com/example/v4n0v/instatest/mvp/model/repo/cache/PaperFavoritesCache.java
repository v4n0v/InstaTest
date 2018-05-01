package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class PaperFavoritesCache implements IFavoritesCache {

    @Override
    public void writeToFavorives(Datum data) {
        readFromFavorites()
                .subscribeOn(Schedulers.io())
                .subscribe(favorites->{
                   favorites.add(data);
                   Paper.book("favorites").write("all", favorites);
                    Timber.d("Added to favorites");
                });
    }

    @Override
    public void removeFromFavorives(Datum data) {
        readFromFavorites()
                .subscribeOn(Schedulers.io())
                .subscribe(favorites->{
                    for (Datum favorite: favorites){
                        if (data==favorite){
                            favorites.remove(favorite);
                        }
                    }
                    Paper.book("favorites").write("all", favorites);
                    Timber.d("Removed favorites");
                });
    }

    @Override
    public Observable<List<Datum>> readFromFavorites() {
        return Observable.fromCallable(()->{
            List<Datum> favorites = Paper.book("favorites").read("all");
            if (favorites==null) {
                favorites = new ArrayList<>();
            }
            Timber.d("Favorites loaded from paper cache");
            return favorites;
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
