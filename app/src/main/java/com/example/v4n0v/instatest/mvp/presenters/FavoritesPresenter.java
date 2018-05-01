package com.example.v4n0v.instatest.mvp.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.example.v4n0v.instatest.mvp.view.FavoritesView;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListImageRaw;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListPresenter;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperFavoritesCache;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
// * Created by v4n0v on 29.04.18.
// */

public class FavoritesPresenter extends MvpPresenter<FavoritesView>{
    private final String USERNAME = "v4n0v";

    public FavoritesPresenter.ListPresenter getListPresenter() {
        return listPresenter;
    }

    private FavoritesPresenter.ListPresenter listPresenter = new FavoritesPresenter.ListPresenter();

    IFavoritesCache favoritesCache = new PaperFavoritesCache();

    public void update() {
        favoritesCache.readFromFavorites()

                .subscribeOn(Schedulers.io())
                .subscribe(favorites -> {
                    listPresenter.items = favorites;
                    getViewState().updateRecycler();
                });
    }

    class ListPresenter implements IListPresenter {
        List<Datum> items = new ArrayList<>();

        @Override
        public void bindView(IListImageRaw view) {
            view.setImageData(items.get(view.getPos()));
        }

        @Override
        public int getViewCount() {
            return items.size();
        }

        @Override
        public void selectItem(int pos) {
            if (items.get(pos).isFavorite())
                items.get(pos).setFavorite(false);
            else
                items.get(pos).setFavorite(true);
            getViewState().updateRecycler();
//            Weather weather = items.get(pos);
//            getViewState().toast("Loading "+weather.getCity()+" weather");
//            ChangeCityBus.getBus().post(weather.getCity());

        }
    }

    public void init() {
        favoritesCache.readFromFavorites()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(favorites->{
            listPresenter.items=favorites;
     //       getViewState().updateRecycler();
        });

    }

}
