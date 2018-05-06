package com.example.v4n0v.instatest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
/**
 * Интерфейс фрагмента ленты
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void init();
    void fillUserInfo(String username);
    void loadAvatar(String url);
    void updateRecycler();
    void toast(String msg);
}
