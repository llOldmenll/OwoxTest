package com.oldmen.owoxtest.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.oldmen.owoxtest.domain.models.ImageUnsplash;

import java.util.List;

import io.reactivex.Flowable;

import static com.oldmen.owoxtest.utils.Constants.IMAGESUNSPLASH_TABLE_NAME;

@Dao
public interface ImagesUnsplashDao {

    @Insert
    void insertAll(List<ImageUnsplash> images);

    @Query("SELECT * FROM " + IMAGESUNSPLASH_TABLE_NAME)
    Flowable<List<ImageUnsplash>> getAll();

    @Query("DELETE FROM " + IMAGESUNSPLASH_TABLE_NAME)
    void drop();
}