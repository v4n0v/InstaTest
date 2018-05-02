package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.mvp.model.api.retrofit.ApiService;
import com.example.v4n0v.instatest.mvp.model.entity.json.Caption;
import com.example.v4n0v.instatest.mvp.model.entity.json.Comments;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Images;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;
import com.example.v4n0v.instatest.mvp.model.entity.json.Likes;
import com.example.v4n0v.instatest.mvp.model.entity.json.StandardResolution;
import com.example.v4n0v.instatest.mvp.model.entity.json.User;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;

import org.mockito.Mockito;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

/**
 * Тестовый модуль репозитория получения данных инстаграмм
 */
@Module
public class InstaRepoTestModule {



    @Provides
    public InstagramRepo instagramRepo(){
//        Instagram instagram = new Instagram();
//        instagram.setData(new ArrayList<>());
//        Datum datum = new Datum();
//        datum.setId("111000111");
//        instagram.getData().add(datum);
//        String TOKEN = "265526058.3228490.4357f56a65514c0692e3171a6fc54cba";
//        User user = new User();
//        user.setProfilePicture(null);
//        user.setUsername("v4n0v");
//        datum.setUser(user);
//        datum.setCaption(new Caption());
//        datum.setLikes(new Likes());
//        datum.setComments(new Comments());
//        Images images = new Images();
//        StandardResolution resolution = new StandardResolution();
//        resolution.setUrl("picture");
//        images.setStandardResolution(resolution);
//        images.setStandardResolution(resolution);
//        datum.setImages(images);
//
//        InstagramRepo repo =  Mockito.mock(InstagramRepo.class);
//        Mockito.when(repo.getData(TOKEN)).thenReturn(Observable.just(instagram));
//
//        return repo;
        return Mockito.mock(InstagramRepo.class);
    }
}
