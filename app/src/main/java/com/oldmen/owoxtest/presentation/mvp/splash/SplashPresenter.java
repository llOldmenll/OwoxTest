package com.oldmen.owoxtest.presentation.mvp.splash;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;
import com.oldmen.owoxtest.data.network.CallbackWrapper;
import com.oldmen.owoxtest.data.network.RetrofitClient;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.mvp.base.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> implements BasePresenter {

    @Override
    public void loadImagesWithSearch(Gson gson) {

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
                        getViewState().startMainActivity();
                    }
                });
    }

}
