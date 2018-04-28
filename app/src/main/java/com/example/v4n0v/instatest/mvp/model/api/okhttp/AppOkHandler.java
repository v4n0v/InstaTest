package com.example.v4n0v.instatest.mvp.model.api.okhttp;

import io.reactivex.Observable;

/**
 * Created by v4n0v on 28.04.18.
 */

public class AppOkHandler {
    ApiOk core = new ApiOk();
    public Observable<String> getSelf(){
       return  Observable.fromCallable(()->{
         return core.getResponse();
       });
    }
}
