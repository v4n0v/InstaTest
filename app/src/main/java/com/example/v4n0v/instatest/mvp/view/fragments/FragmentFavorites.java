package com.example.v4n0v.instatest.mvp.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.RecyclerImagesAdapter;
import com.example.v4n0v.instatest.mvp.presenters.FavoritesPresenter;
import com.example.v4n0v.instatest.mvp.view.FavoritesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    View view;
    List<Photo> photoList;

    @InjectPresenter
    FavoritesPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);

         presenter.init();

        photoList = new ArrayList<>();
        int imgId = R.drawable.foto;
        Photo photo1 = new Photo("sasasdasd", imgId);
        Photo photo2 = new Photo("sasasdsdasdasd", imgId);
        Photo photo3 = new Photo("hgntydbfg", imgId);
        photoList.add(photo1);
        photoList.add(photo2);
        photoList.add(photo3);

        initViews();
        //   addItem();
        return view;
    }

    private void initViews() {

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
}