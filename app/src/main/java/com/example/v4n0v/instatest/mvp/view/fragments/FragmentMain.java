package com.example.v4n0v.instatest.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;
import com.example.v4n0v.instatest.mvp.view.MainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by v4n0v on 15.03.18.
 */

public class FragmentMain extends MvpAppCompatFragment {

   private  List<Photo> photoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews();
        ButterKnife.bind(this, view);
        return view;
    }

    private void initViews() {


      initRecyclerView();


    }

    public void  initRecyclerView(){


    }



}
