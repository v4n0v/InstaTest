package com.example.v4n0v.instatest.di.modules;

import com.example.v4n0v.instatest.mvp.model.api.retrofit.ApiService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by v4n0v on 28.04.18.
 */
@Module
public class ApiModule {

    @Provides
    public ApiService api(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    public Retrofit retrofit(@Named("endpoint") String endpoint, @Named("gson") GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();

    }

    @Provides
    @Named("endpoint")
    public String endpoint(){
        return "https://api.instagram.com/";
    }

    @Provides
    @Named("gson")
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Provides
    public Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

}
