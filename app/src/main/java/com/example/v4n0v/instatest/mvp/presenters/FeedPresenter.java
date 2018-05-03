package com.example.v4n0v.instatest.mvp.presenters;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.v4n0v.instatest.mvp.model.ImagesModel;
import com.example.v4n0v.instatest.mvp.model.api.okhttp.AppOkHandler;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListImageRaw;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListPresenter;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.PaperFavoritesCache;
import com.example.v4n0v.instatest.mvp.view.MainView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.paperdb.Paper;
import io.reactivex.Scheduler;


@InjectViewState
public class FeedPresenter extends MvpPresenter<MainView> {
    private final String TOKEN = "265526058.3228490.4357f56a65514c0692e3171a6fc54cba";
    private final Scheduler scheduler;
    private Instagram instagram;

    @Inject
    InstagramRepo repo;

    public FeedPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;

    }

    private IFavoritesCache favoritesCache = new PaperFavoritesCache();
    public ListPresenter getListPresenter() {
        return listPresenter;
    }

    private ListPresenter listPresenter = new ListPresenter();



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
                favoritesCache.removeFromFavorives(items.get(pos));
                getViewState().toast("Удалено из избранного");
            }else {
                items.get(pos).setFavorite(true);
                favoritesCache.writeToFavorives(items.get(pos));
                getViewState().toast("Добавлено в избранное");
            }
            getViewState().updateRecycler();

        }
    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    @SuppressLint("CheckResult")
    public void getImages() {

        repo.getData(TOKEN)
                .observeOn(scheduler)
                .subscribe(inst -> {
                    this.instagram = inst;

                    // берем список фаворитов из базы, сравниваем с полученным, при совпадении ставим метку
                    List<Datum> favorites = Paper.book("favorites").read("all");
                    for (Datum data: inst.getData()){
                        for (Datum favorite:favorites){
                            if (data.getId().equals(favorite.getId())){
                                data.setFavorite(true);
                            }
                        }
                    }

                    // обновляем вью
                    getViewState().fillUserInfo(inst.getData().get(0).getUser().getUsername());
                    getViewState().loadAvatar(inst.getData().get(0).getUser().getProfilePicture());

                    listPresenter.items= this.instagram .getData();
                    getViewState().updateRecycler();

                });


    }

}
