package com.example.v4n0v.instatest.mvp.model.api.retrofit;

import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

        @GET("v1/users/self/media/recent/?")
        Observable<Instagram> getData(@Query("access_token") String token);


}
