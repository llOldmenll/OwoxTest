package com.oldmen.owoxtest.presentation.mvp.main;

import android.annotation.SuppressLint;
import android.test.suitebuilder.annotation.Suppress;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oldmen.owoxtest.domain.models.ImageInfo;
import com.oldmen.owoxtest.network.CallbackWrapper;
import com.oldmen.owoxtest.network.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @SuppressLint("CheckResult")
    public synchronized void loadImages(final int page) {

        System.out.println("-------------------- " + page);

        RetrofitClient.getApiService().getAllPhotos(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<List<ImageInfo>>>(getViewState()) {
                    @Override
                    protected void onSuccess(Response response, int imgsPerPage, int imgsTotal) {
                        if (response.body() != null) {
                            List<ImageInfo> imagesInfo = (List<ImageInfo>) response.body();
                            if (page == 1)
                                getViewState().refreshRecycler(imagesInfo, imgsPerPage, imgsTotal);
                            else
                                getViewState().appendRecycler(imagesInfo);
                        }
                    }
                });
    }

    public void searchImages(String searchQuery, int page) {

    }
}
