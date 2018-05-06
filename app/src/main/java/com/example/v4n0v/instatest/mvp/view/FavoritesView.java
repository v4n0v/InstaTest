package com.example.v4n0v.instatest.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Интерфейс фрагмента фаворитов
 */
 @StateStrategyType(AddToEndSingleStrategy.class)
public interface FavoritesView extends MvpView {
    void init();
    void toast(String msg);
    void updateRecycler();
}
