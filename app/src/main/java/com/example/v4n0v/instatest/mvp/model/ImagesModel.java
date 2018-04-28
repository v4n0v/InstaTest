package com.example.v4n0v.instatest.mvp.model;

import com.example.v4n0v.instatest.mvp.model.entity.json.Datum;
import com.example.v4n0v.instatest.mvp.model.entity.json.Images;
import com.example.v4n0v.instatest.mvp.model.entity.json.Instagram;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by v4n0v on 28.04.18.
 */

public class ImagesModel {
    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    private List<Images> images;

    public ImagesModel() {
        images=new ArrayList<>();
    }

  public Observable<Boolean> saveImages(Instagram inst) {

        return Observable.fromCallable(()->{
            for (Datum data:inst.getData()){
                images.add(data.getImages());
            }
           return true;
        });
    }
}
