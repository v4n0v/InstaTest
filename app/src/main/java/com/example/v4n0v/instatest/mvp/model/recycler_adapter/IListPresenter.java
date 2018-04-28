package com.example.v4n0v.instatest.mvp.model.recycler_adapter;

/**
 * Created by v4n0v on 28.04.18.
 */

public interface IListPresenter {

    int pos = -1;
    void bindView(IListImageRaw view);
    int getViewCount();
    void selectItem(int pos);

}
