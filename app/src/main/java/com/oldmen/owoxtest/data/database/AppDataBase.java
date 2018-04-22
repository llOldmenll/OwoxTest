package com.oldmen.owoxtest.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.oldmen.owoxtest.domain.models.ImageUnsplash;

@Database(entities = {ImageUnsplash.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ImagesUnsplashDao getImagesDao();

}
