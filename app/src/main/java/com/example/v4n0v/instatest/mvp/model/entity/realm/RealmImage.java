package com.example.v4n0v.instatest.mvp.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Объект изображения Realm
 */

public class RealmImage extends RealmObject {
    @PrimaryKey
    String url;
    String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
