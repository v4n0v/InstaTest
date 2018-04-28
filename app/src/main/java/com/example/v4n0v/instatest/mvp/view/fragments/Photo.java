package com.example.v4n0v.instatest.mvp.view.fragments;

/**
 * Created by v4n0v on 16.03.18.
 */

public class Photo {
    private String description;
    private boolean isFavorite;
    private int image;

    public Photo(String description, int image) {
        this.description = description;
        this.isFavorite = false;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
