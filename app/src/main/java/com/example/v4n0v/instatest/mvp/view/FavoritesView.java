package com.example.v4n0v.instatest.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by v4n0v on 29.04.18.
 */

public interface FavoritesView extends MvpView {
    void init();
    void toast(String msg);
    void updateRecycler();
}
