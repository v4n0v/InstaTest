package com.example.v4n0v.instatest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.v4n0v.instatest.mvp.view.fragments.Photo;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void init();



    void fillUserInfo(String username);
    void loadAvatar(String url);

    void updateRecycler();
}
