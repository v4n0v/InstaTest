package com.example.v4n0v.instatest.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.v4n0v.instatest.App;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.di.modules.RepoModule;
import com.example.v4n0v.instatest.images.GlideLoader;
import com.example.v4n0v.instatest.images.ImageLoader;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.RecyclerImagesAdapter;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;
import com.example.v4n0v.instatest.mvp.view.MainView;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.bottom_sheet)
    View bottomView;
    private BottomSheetBehavior<View> sheetBehavior;
    @BindView(R.id.fab)
    FloatingActionButton fab;
//    @BindView(R.id.tv_posts)
//    TextView postsTextView;
//
//    @BindView(R.id.tv_subscribers)
//    TextView subscribersTextView;
//
//    @BindView(R.id.tv_subscribes)
//    TextView subscribesTextView;

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


    private void initViews() {

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerImagesAdapter(imageLoader, presenter.getListPresenter());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void init() {
        initBottom();
        initViews();
    }


    void initBottom() {
        sheetBehavior = BottomSheetBehavior.from(bottomView);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // этот код скрывает кнопку сразу же
// и отображает после того как нижний экран полностью свернется
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {

                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
            }

        });

    }


    @Override
    public void fillUserInfo(String username) {
        usernameTextView.setText(username);
//        postsTextView.setText(posts);
//        subscribersTextView.setText(subscribers);
//        subscribesTextView.setText(subscribes);

    }

    @Override
    public void loadAvatar(String url) {
        imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void updateRecycler() {
        adapter.notifyDataSetChanged();
    }
}