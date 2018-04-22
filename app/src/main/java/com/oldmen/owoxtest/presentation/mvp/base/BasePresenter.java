package com.oldmen.owoxtest.presentation.mvp.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oldmen.owoxtest.data.database.ImagesUnsplashDao;
import com.oldmen.owoxtest.data.network.ImageInfoDeserializer;
import com.oldmen.owoxtest.data.repositories.SharedPrefHelper;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.utils.CustomApplication;

import java.lang.ref.WeakReference;
import java.util.List;

public interface BasePresenter {

    void loadImagesWithSearch(Gson gson);

    void loadImagesDefault(Gson gson);

    default void saveCurrentPosition(int position) {
        SharedPrefHelper.saveCurrentPosition(position,
                new WeakReference<>(CustomApplication.getAppContext()));
    }

    default int getCurrentPosition() {
        return SharedPrefHelper.getCurrentPosition(
                new WeakReference<>(CustomApplication.getAppContext()));
    }

    default void saveCurrentPage(int page) {
        SharedPrefHelper.saveCurrentPage(page,
                new WeakReference<>(CustomApplication.getAppContext()));
    }

    default int getCurrentPage() {
        return SharedPrefHelper.getCurrentPage(
                new WeakReference<>(CustomApplication.getAppContext()));
    }

    default void savePagesNumber(int pagesNumber) {
        SharedPrefHelper.savePagesNumber(pagesNumber,
                new WeakReference<>(CustomApplication.getAppContext()));
    }

    default int getPagesNumber() {
        return SharedPrefHelper.getPagesNumber(
                new WeakReference<>(CustomApplication.getAppContext()));
    }

    default void loadImages() {
        Gson gson = new GsonBuilder().registerTypeAdapter(
                new TypeToken<List<ImageUnsplash>>() {}.getType(),
                new ImageInfoDeserializer()).create();

        if (getSearchQuery().isEmpty())
            loadImagesDefault(gson);
        else
            loadImagesWithSearch(gson);
    }

    default void appendImagesTable(List<ImageUnsplash> images) {
        CustomApplication.getDbInstance().getImagesDao().insertAll(images);
    }

    default void refreshImagesTable(List<ImageUnsplash> images) {
        ImagesUnsplashDao dao = CustomApplication.getDbInstance().getImagesDao();
        dao.drop();
        dao.insertAll(images);
    }

    default String getSearchQuery(){
        return SharedPrefHelper.getSearchQuery(
                new WeakReference<>(CustomApplication.getAppContext()));
    }
}
