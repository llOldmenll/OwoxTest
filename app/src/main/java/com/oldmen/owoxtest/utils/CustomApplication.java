package com.oldmen.owoxtest.utils;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.oldmen.owoxtest.data.database.AppDataBase;
import com.oldmen.owoxtest.data.repositories.SharedPrefHelper;

import io.fabric.sdk.android.Fabric;
import java.lang.ref.WeakReference;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.oldmen.owoxtest.utils.Constants.DATA_BASE_NAME;

public class CustomApplication extends Application {

    public volatile static CustomApplication instance;
    private static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, DATA_BASE_NAME)
                .build();
        dropData();
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static AppDataBase getDbInstance() {
        return dataBase;
    }

    private void dropData() {
        SharedPrefHelper.removeAll(new WeakReference<>(this));
        Completable.fromAction(() -> dataBase.getImagesDao().drop())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
