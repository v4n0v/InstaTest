package com.example.v4n0v.instatest.mvp.model.api.okhttp;


import com.example.v4n0v.instatest.data.AppDataMap;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.example.v4n0v.instatest.data.AppDataMap.CALLBACKURL;
import static com.example.v4n0v.instatest.data.AppDataMap.INSTA_SECRET;
import static com.example.v4n0v.instatest.data.AppDataMap.TOKEN;

public class ApiOk {

    String authURLString= AppDataMap.AUTHURL
            +"?client_id="
            + AppDataMap.INSTA_ID
            +"&amp;amp;redirect_uri="
            + CALLBACKURL+
            "&amp;amp;response_type=code&amp;amp;display=touch&amp;amp;scope=likes+comments+relationships";
    String tokenURLString= AppDataMap.TOKENURL
            +"?client_id="
            + AppDataMap.INSTA_ID
            +"&amp;amp;client_secret="
            +INSTA_SECRET+
            "&amp;amp;redirect_uri="
            +CALLBACKURL
            +"&amp;amp;grant_type=authorization_code";
    String tokenURL = "https://api.instagram.com/oauth/authorize/?client_id=3228490d13164566b76d7c0bfe02118d&redirect_uri=https://vk.com/v4n0v&response_type=token";
    String url = "https://api.instagram.com/v1/users/self/media/recent/?access_token="+TOKEN;

    public String getResponse( ) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url(url)
                    .build();

            return client.newCall(req).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
