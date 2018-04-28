package com.example.v4n0v.instatest.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.mvp.model.ImagesModel;
import com.example.v4n0v.instatest.mvp.model.api.okhttp.AppOkHandler;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListImageRaw;
import com.example.v4n0v.instatest.mvp.model.recycler_adapter.IListPresenter;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;
import com.example.v4n0v.instatest.mvp.view.MainView;
import com.example.v4n0v.instatest.mvp.view.fragments.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


@InjectViewState
public class FeedPresenter extends MvpPresenter<MainView> {
    private final String TOKEN = "265526058.3228490.4357f56a65514c0692e3171a6fc54cba";
    private final Scheduler scheduler;
    private ArrayList<Photo> photoList;
    private AppOkHandler handler;
    Instagram instagram;
    ImagesModel model;

    @Inject
    InstagramRepo repo;

    public FeedPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        model=new ImagesModel();
        handler = new AppOkHandler();
    }


    public ListPresenter getListPresenter() {
        return listPresenter;
    }

    private ListPresenter listPresenter = new ListPresenter();


    class ListPresenter implements IListPresenter {
        List<Datum> items = new ArrayList<>();

        @Override
        public void bindView(IListImageRaw view) {
            view.setImageData(items.get(view.getPos()));
        }

        @Override
        public int getViewCount() {
            return items.size();
        }

        @Override
        public void selectItem(int pos) {
            if (items.get(pos).isFavorite())
                items.get(pos).setFavorite(false);
            else
                items.get(pos).setFavorite(true);
            getViewState().updateRecycler();
//            Weather weather = items.get(pos);
//            getViewState().toast("Loading "+weather.getCity()+" weather");
//            ChangeCityBus.getBus().post(weather.getCity());

        }
    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void getImages() {

        repo.getData(TOKEN).subscribeOn(Schedulers.io())
                .subscribe(inst -> {
                    instagram = inst;
                    steUserInfo(inst.getData().get(0).getUser().getUsername());
                    String avaLink = inst.getData().get(0).getUser().getProfilePicture();
                    getViewState().getAvatar(avaLink);
                    model.saveImages(inst)
                           .subscribe(boo -> {
                                Timber.d("Image extracting complete");

                            });
                    //model.saveImages(inst);
                    //   Timber.d(answer);
                    listPresenter.items=inst.getData();
                    getViewState().updateRecycler();
                    Timber.d(avaLink);
                });

//        handler.getSelf().subscribeOn(Schedulers.io())
//                .observeOn(scheduler)
//                .subscribe(answer->{
//
//                    Instagram inst = gson.fromJson(answer, Instagram.class);
//                    steUserInfo(inst.getData().get(0).getUser().getUsername());
//                    String avaLink = inst.getData().get(0).getUser().getProfilePicture();
//                 //   Timber.d(answer);
//                    Timber.d(avaLink);
//                });


        photoList = new ArrayList<>();
        int imgId = R.drawable.foto;
        Photo photo1 = new Photo("sasasdasd", imgId);
        Photo photo2 = new Photo("sasasdsdasdasd", imgId);
        Photo photo3 = new Photo("hgntydbfg", imgId);
        photoList.add(photo1);
        photoList.add(photo2);
        photoList.add(photo3);
        getViewState().applyPhotoFeed(photoList);
    }

    private void steUserInfo(String username) {
        getViewState().fillUserInfo(username);
    }
}
