package com.example.v4n0v.instatest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListImageRaw;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListPresenter;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperFavoritesCache;
import com.example.v4n0v.instatest.mvp.view.FavoritesView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;

/**
 * FavoritesPresenter
 */

@InjectViewState
public class FavoritesPresenter extends MvpPresenter<FavoritesView> {


    public FavoritesPresenter.ListPresenter getListPresenter() {
        return listPresenter;
    }

    private FavoritesPresenter.ListPresenter listPresenter = new FavoritesPresenter.ListPresenter();
    Scheduler scheduler;

    public FavoritesPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    IFavoritesCache favoritesCache = new PaperFavoritesCache();

    public void update() {
        favoritesCache.readFromFavorites()
                .observeOn(scheduler)
                .subscribe(favorites -> {
                    listPresenter.items = favorites;
                    getViewState().updateRecycler();

                });
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        update();
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
            if (items.get(pos).isFavorite()) {
                items.get(pos).setFavorite(false);
                favoritesCache.removeFromFavorites(items.get(pos));
                favoritesCache.removeFromFavorites(items.get(pos))
                        .observeOn(scheduler)
                        .subscribe(complete-> {
                            items.remove(pos);
                            getViewState().toast("Удалено из избранного");
                            getViewState().updateRecycler();
                        });

            }

        }
    }




}
