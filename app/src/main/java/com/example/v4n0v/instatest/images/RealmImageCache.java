package com.example.v4n0v.instatest.images;

import android.graphics.Bitmap;

import com.example.v4n0v.instatest.App;
import com.example.v4n0v.instatest.mvp.model.entity.realm.RealmImage;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Кеширование изображений при помощи realm
 */

public class RealmImageCache implements ImageCache{

    private  final String IMAGE_FOLDER_NAME = "image";

    @Override
    public void saveImage(String url, Bitmap bitmap) {
        if (!getImageDir().exists() && !getImageDir().mkdirs())
        {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }

        final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
        final File imageFile = new File(getImageDir(), MD5(url) + fileFormat);
        FileOutputStream fos;

        try
        {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(fileFormat.equals("jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        }
        catch (Exception e)
        {
            Timber.d("Failed to save image");

        }

        Realm.getDefaultInstance().executeTransactionAsync(realm ->
        {
            RealmImage cachedImage = new RealmImage();
            cachedImage.setUrl(url);
            cachedImage.setPath(imageFile.toString());
            realm.copyToRealm(cachedImage);
        });

//        RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", city).findFirst();
//        if (realmImage != null) {
//            realm.executeTransaction(realm1 ->
//                    realmImage.setPath(file.getAbsolutePath())
//            );
//        } else {
//            realm.executeTransaction(realm1 -> {
//                RealmImage newRealmImage = realm.createObject(RealmImage.class, city);
//                newRealmImage.setPath(file.getAbsolutePath());
//            });
//        }
        Timber.d("realm saved image: " + imageFile.getAbsolutePath());
    }

    @Override
    public File loadImage(String url) {
        RealmImage cachedImage = Realm.getDefaultInstance().where(RealmImage.class).equalTo("url", url).findFirst();
        if (cachedImage != null)
        {
            Timber.d("realm loaded image: " + cachedImage.getPath());

            return new File(cachedImage.getPath());
        }
        return null;
    }

    public  File getImageDir()
    {
        return new File(App.getInstance().getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }
    public  String MD5(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
