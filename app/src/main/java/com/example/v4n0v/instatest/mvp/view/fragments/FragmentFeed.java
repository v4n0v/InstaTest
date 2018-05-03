package com.example.v4n0v.instatest.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.v4n0v.instatest.App;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.RecyclerImagesAdapter;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;
import com.example.v4n0v.instatest.mvp.view.MainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FragmentFeed extends MvpAppCompatFragment implements MainView {

    @InjectPresenter
    FeedPresenter presenter;

    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;
    @BindView(R.id.tv_username)
    TextView usernameTextView;

    @Inject
    ImageLoader imageLoader;

    @ProvidePresenter
    public FeedPresenter provideMainPresenter() {
        return new FeedPresenter(AndroidSchedulers.mainThread());
    }


    public static FragmentFeed newInstance(Bundle bundle) {
        FragmentFeed currentFragment = new FragmentFeed();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @BindView(R.id.recycler_feed)
    RecyclerView recyclerView;
    private RecyclerImagesAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        App.getInstance().getAppComponent().inject(presenter);
        presenter.getImages();


        return view;
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
    public void fillUserInfo(String username) {
        usernameTextView.setText(username);


    }

    @Override
    public void loadAvatar(String url) {
        imageLoader.loadInto(url, avatarImageView);
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