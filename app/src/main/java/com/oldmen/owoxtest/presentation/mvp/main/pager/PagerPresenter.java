package com.oldmen.owoxtest.presentation.mvp.main.pager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oldmen.owoxtest.data.network.CallbackWrapper;
import com.oldmen.owoxtest.data.network.RetrofitClient;
import com.oldmen.owoxtest.data.repositories.SharedPrefHelper;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.mvp.base.BasePresenter;
import com.oldmen.owoxtest.utils.CustomApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


@InjectViewState
public class PagerPresenter extends MvpPresenter<PagerView> implements BasePresenter {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        CustomApplication.getDbInstance().getImagesDao().getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(imageUnsplashes -> {
                    if (imageUnsplashes.size() != 0)
                        getViewState().updatePager(imageUnsplashes);
                })
                .subscribe();
    }

    @Override
    @SuppressLint("CheckResult")
    public synchronized void loadImagesWithSearch(Gson gson) {
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
    public synchronized void loadImagesDefault(Gson gson) {
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

    public void changeFooterState() {
        WeakReference<Context> context = new WeakReference<>(CustomApplication.getAppContext());
        if (SharedPrefHelper.getFooterState(context)) {
            getViewState().hideFooter();
            SharedPrefHelper.saveFooterState(false, context);
        } else {
            getViewState().showFooter();
            SharedPrefHelper.saveFooterState(true, context);
        }
    }

    public void saveImage(Bitmap finalBitmap, String name) {
        Observable.fromCallable(() -> saveImageAsync(finalBitmap, name))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(isSaved -> {
                    if (isSaved) getViewState().showImageLoadingMsg("Image saved");
                    else getViewState().showImageLoadingMsg("Image wasn't saved");
                })
                .subscribe();
    }

    private boolean saveImageAsync(Bitmap finalBitmap, String name) {
        WeakReference<Context> context = new WeakReference<>(CustomApplication.getAppContext());

        File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/unsplash_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
            context.get().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(myDir)));
        }

        String imgName = name.trim() + ".jpg";
        File file = new File(myDir, imgName);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            context.get().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
