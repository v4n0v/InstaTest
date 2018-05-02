package com.example.v4n0v.instatest;

import com.example.v4n0v.instatest.di.DaggerTestComponent;
import com.example.v4n0v.instatest.di.TestComponent;
import com.example.v4n0v.instatest.di.modules.InstaRepoTestModule;
import com.example.v4n0v.instatest.mvp.model.entity.json.Caption;
import com.example.v4n0v.instatest.mvp.model.entity.json.Comments;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Images;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;
import com.example.v4n0v.instatest.mvp.model.entity.json.Likes;
import com.example.v4n0v.instatest.mvp.model.entity.json.StandardResolution;
import com.example.v4n0v.instatest.mvp.model.entity.json.User;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;
import com.example.v4n0v.instatest.mvp.presenters.FeedPresenter;
import com.example.v4n0v.instatest.mvp.view.MainView;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;



public class FeedPresenterUnitTest {
    private FeedPresenter feedPresenter;
    private TestScheduler testScheduler;
    @Mock
    MainView mainView;


    @BeforeClass
    public static void setupClass()
    {

    }

    @AfterClass
    public static void tearDownClass() { }


    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        feedPresenter = Mockito.spy(new FeedPresenter(testScheduler));


    }
    @After
    public void tearDown()
    {

    }

    @Test
    public void onFirstViewAttach()
    {
        feedPresenter.attachView(mainView);
        Mockito.verify(mainView).init();
    }



    @Test
    public void instagramRepoTest(){

        TestComponent testComponent = DaggerTestComponent.builder().instaRepoTestModule(new InstaRepoTestModule(){
            @Override
            public InstagramRepo instagramRepo() {
                InstagramRepo repo = super.instagramRepo();
                String TOKEN = "265526058.3228490.4357f56a65514c0692e3171a6fc54cba";

                Instagram instagram = new Instagram();
                instagram.setData(new ArrayList<>());
                Datum datum = new Datum();
                datum.setId("111000111");


                User user = new User();
                user.setProfilePicture("https://scontent.cdninstagram.com/vp/2da02b56c59539339fd164f29c578791/5B9AC619/t51.2885-15/s640x640/sh0.08/e35/26867623_1494527720645806_1885776301218856960_n.jpg");
                user.setUsername("v4n0v");
                datum.setUser(user);
                instagram.getData().add(datum);
//                datum.setCaption(new Caption());
//                datum.setLikes(new Likes());
//                datum.setComments(new Comments());
//                Images images = new Images();
//                StandardResolution resolution = new StandardResolution();
//                resolution.setUrl("picture");
//                images.setStandardResolution(resolution);
//                images.setStandardResolution(resolution);
//                datum.setImages(images);

                Mockito.when(repo.getData(TOKEN)).thenReturn(Observable.just(instagram));

                return repo;
            }
        }).build();
        testComponent.inject(feedPresenter);


        feedPresenter.getImages();
        testScheduler.advanceTimeBy(1 , TimeUnit.SECONDS);

        Mockito.verify(mainView).fillUserInfo("v4n0v");
        Mockito.verify(mainView).loadAvatar(null);

    }

}
