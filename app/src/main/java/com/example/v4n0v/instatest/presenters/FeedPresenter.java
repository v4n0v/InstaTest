package com.example.v4n0v.instatest.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.fragments.Photo;
import com.example.v4n0v.instatest.view.MainView;

import java.util.ArrayList;

import io.reactivex.Scheduler;


@InjectViewState
public class FeedPresenter extends MvpPresenter<MainView> {

    private final Scheduler scheduler;
    private ArrayList<Photo> photoList;

    public FeedPresenter(Scheduler scheduler) {
        this.scheduler=scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void getImages() {
        photoList = new ArrayList<>();
        int imgId = R.drawable.foto;
        Photo photo1 = new Photo("sasasdasd", imgId);
        Photo photo2 = new Photo("sasasdsdasdasd", imgId);
        Photo photo3 = new Photo("hgntydbfg", imgId);
        photoList.add(photo1);
        photoList.add(photo2);
        photoList.add(photo3);
        getViewState().applyPhotoFeed(photoList);
    }
}
