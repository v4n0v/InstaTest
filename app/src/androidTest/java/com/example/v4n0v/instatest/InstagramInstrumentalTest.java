package com.example.v4n0v.instatest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.v4n0v.instatest.di.DaggerTestComponent;
import com.example.v4n0v.instatest.di.TestComponent;
import com.example.v4n0v.instatest.di.modules.ApiModule;
import com.example.v4n0v.instatest.images.ImageCache;
import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Images;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;
import com.example.v4n0v.instatest.mvp.model.entity.json.StandardResolution;
import com.example.v4n0v.instatest.mvp.model.entity.json.User;
import com.example.v4n0v.instatest.mvp.model.repo.InstagramRepo;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IFavoritesCache;
import com.example.v4n0v.instatest.mvp.model.repo.cache.IInstagramRepoCahe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;

/**
 * Instrumental test class
 */
@RunWith(AndroidJUnit4.class)
public class InstagramInstrumentalTest {
    private static MockWebServer webServer;

    @Inject
    InstagramRepo instagramRepo;

    @Inject
    @Named("paper-insta")
    IInstagramRepoCahe repoCahe;

    @Inject
    @Named("realm-image")
    ImageCache imageCache;

    @Inject
    @Named("paper-favorites")
    IFavoritesCache favoritesCache;


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.v4n0v.instatest", appContext.getPackageName());
    }

    @BeforeClass
    public static void setupClass() throws IOException {
        webServer = new MockWebServer();
        webServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        webServer.shutdown();
    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule() {
                    @Override
                    public String endpoint() {
                        return webServer.url("/").toString();
                    }
                })
                .build();

        component.inject(this);
    }

    @After
    public void tearDown() {


    }

    @Test
    public void testInstagramWebServerLoad() {
        webServer.enqueue(createUserResponse("superuser", "somepic.jpg", "someurl"));
        TestObserver<Instagram> observer = new TestObserver<>();
        instagramRepo.getData("token").subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getData().get(0).getUser().getUsername(), "superuser");
        assertEquals(observer.values().get(0).getData().get(0).getUser().getProfilePicture(), "somepic.jpg");
        assertEquals(observer.values().get(0).getData().get(0).getImages().getStandardResolution().getUrl(), "someurl");
    }

    @Test
    public void testInstagramWriteCache() {
        Instagram instagram = new Instagram();
        instagram.setData(new ArrayList<>());
        Datum datum = new Datum();
        User user = new User();
        user.setProfilePicture("somepic.jpg");
        user.setUsername("superuser");
        datum.setUser(user);
        instagram.getData().add(datum);
        repoCahe.saveData("superuser", instagram);
    }

    @Test
    public void testInstagramReadCache() {
        TestObserver<Instagram> observer = new TestObserver<>();
        repoCahe.loadLastData().subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getData().get(0).getUser().getUsername(), "superuser");
        assertEquals(observer.values().get(0).getData().get(0).getUser().getProfilePicture(), "somepic.jpg");
    }


    @Test
    public void testImageCache() {
        assertEquals(imageCache.saveImage("someurl", null), imageCache.loadImage("someurl"));
    }

    @Test
    public void testWriteToFavoritesCache() {
        Datum data = new Datum();
        data.setId("someID");
        TestObserver<Boolean> observer = new TestObserver<>();
        favoritesCache.writeToFavorites(data).subscribe(observer);
    }

    @Test
    public void testReadFromFavoritesCache() {
        TestObserver<List<Datum>> observer = new TestObserver<>();
        favoritesCache.readFromFavorites().subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertValueCount(1);
        //забираем последний элемент и сравниваем с ранее добавленным
        assertEquals(observer.values().get(0).get(observer.values().get(0).size()-1).getId(), "someID");
    }

    @Test
    public void testDeleteFromFavoritesCache() {
        Datum data = new Datum();
        data.setId("someID");

        TestObserver<Boolean> observer = new TestObserver<>();
        favoritesCache.removeFromFavorites(data).subscribe(observer);
    }


    private MockResponse createUserResponse(String login, String url, String userpic) {
        String body = "{\"data\":" +
                "        [{\"id\": \"1703692968194859066_265526058\"," +
                "           \"user\": " +
                "           {" +
                "               \"id\": \"265526058\", " +
                "               \"profile_picture\": \""+url+"\", " +
                "               \"username\": \""+login+"\"" +
                "           }," +
                "             \"images\":" +
                "               { " +
                "                 \"standard_resolution\":" +
                "                   {\n" +
                "                        \"width\":640, \"height\":640, " +
                "                       \"url\":\n" +
                "                       \""+userpic+"\"\n" +
                "               }   " +
                "               }" +
                "          }]" +
                "      }";
        return new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(body);
    }
}
