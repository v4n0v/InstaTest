package com.example.v4n0v.instatest.mvp.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.v4n0v.instatest.App;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.event_bus.TabSelectBus;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.RecyclerImagesAdapter;
import com.example.v4n0v.instatest.mvp.presenters.FavoritesPresenter;
import com.example.v4n0v.instatest.mvp.view.FavoritesView;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;


public class FragmentFavorites extends MvpAppCompatFragment implements FavoritesView {

    public static FragmentFavorites newInstance(Bundle bundle) {
        FragmentFavorites currentFragment = new FragmentFavorites();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }


    @BindView(R.id.recycler_fav)
    RecyclerView recyclerView;

    RecyclerImagesAdapter adapter;

    @InjectPresenter
    FavoritesPresenter presenter;

    @ProvidePresenter
    public FavoritesPresenter providePresenter(){
        return new FavoritesPresenter(AndroidSchedulers.mainThread());
    }



    @Inject
    ImageLoader imageLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        App.getInstance().getAppComponent().inject(presenter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.update();
        TabSelectBus.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        TabSelectBus.getBus().unregister(this);
    }

    @Subscribe
    public void onTabBusMessageRecieved(Integer i){
        Timber.d("onTabBusMessageRecieved: "+i);
        if (i==0){
            presenter.update();
        }
    }



    @Override
    public void init() {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerImagesAdapter(imageLoader, presenter.getListPresenter());

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void updateRecycler() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}