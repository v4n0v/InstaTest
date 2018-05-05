package com.example.v4n0v.instatest.mvp.model.repo.cache;

import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class PaperInstagramRepoCache implements IInstagramRepoCahe {
    @Override
    public void saveData(String username, Instagram instagram) {
        Paper.book("instagram").write(username, instagram);
        Paper.book("user").write("current", username);
        Timber.d("saveData on paper");
    }

    @Override
    public Observable<Instagram> loadLastData( ) {
        return  Observable.fromCallable(()->{
            Timber.d("load data from paper");
            String usrrname = Paper.book("user").read("current");
            Instagram inst =Paper.book("instagram").read(usrrname, null);
            return inst;
        }).subscribeOn(Schedulers.io());


    }
}
