package com.oldmen.owoxtest.presentation.mvp.main.grid;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;
import com.oldmen.owoxtest.data.network.CallbackWrapper;
import com.oldmen.owoxtest.data.network.RetrofitClient;
import com.oldmen.owoxtest.data.repositories.SharedPrefHelper;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.mvp.base.BasePresenter;
import com.oldmen.owoxtest.utils.CustomApplication;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@InjectViewState
public class GridPresenter extends MvpPresenter<GridView> implements BasePresenter {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        CustomApplication.getDbInstance().getImagesDao().getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(imageUnsplashes -> {
                    saveCurrentPage(getCurrentPage() + 1);
                    getViewState().updateRecycler(imageUnsplashes);
                })
                .subscribe();
    }

    @Override
    @SuppressLint("CheckResult")
    public void loadImagesWithSearch(Gson gson) {
        int currentPage = getCurrentPage();
        RetrofitClient.getApiService(gson).getSearchPhotos(currentPage + 1, getSearchQuery())
                .doOnNext(listResponse -> {
                    List<ImageUnsplash> imagesInfo = listResponse.body();
                    if (imagesInfo != null) {
                        if (currentPage == 0) refreshImagesTable(imagesInfo);
                        else appendImagesTable(imagesInfo);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<List<ImageUnsplash>>>(getViewState()) {
                    @Override
                    protected void onSuccess(Response response, int imgsPerPage, int imgsTotal) {
                        if (currentPage == 0)
                            savePagesNumber((int) Math.ceil((double) imgsTotal / (double) imgsPerPage));
                    }
                });
    }

    @Override
    @SuppressLint("CheckResult")
    public void loadImagesDefault(Gson gson) {
        int currentPage = getCurrentPage();
        RetrofitClient.getApiService(gson).getAllPhotos(currentPage + 1)
                .doOnNext(listResponse -> {
                    List<ImageUnsplash> imagesInfo = listResponse.body();
                    if (imagesInfo != null) {
                        if (currentPage == 0) refreshImagesTable(imagesInfo);
                        else appendImagesTable(imagesInfo);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<List<ImageUnsplash>>>(getViewState()) {
                    @Override
                    protected void onSuccess(Response response, int imgsPerPage, int imgsTotal) {
                        if (currentPage == 0)
                            savePagesNumber((int) Math.ceil((double) imgsTotal / (double) imgsPerPage));
                    }
                });
    }

    public void startPager(int position, ImageView imgView) {
        saveCurrentPosition(position);
        getViewState().startPager(position, imgView);
    }

    public void saveSearchQuery(String query) {
        SharedPrefHelper.saveSearchQuery(query.trim(),
                new WeakReference<>(CustomApplication.getAppContext()));
    }

}
