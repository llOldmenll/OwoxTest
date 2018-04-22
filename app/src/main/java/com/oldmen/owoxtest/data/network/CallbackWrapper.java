package com.oldmen.owoxtest.data.network;

import com.oldmen.owoxtest.presentation.mvp.base.BaseView;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.HttpException;

public abstract class CallbackWrapper<T extends Response> extends DisposableObserver<T> {
    private WeakReference<BaseView> weakReference;

    public CallbackWrapper(BaseView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T t, int imgsPerPage, int imgsTotal);

    @Override
    public void onNext(T t) {
        int perPage = 0;
        int total = 0;
        if (t.headers().get("X-Per-Page") != null)
            perPage = Integer.parseInt(t.headers().get("X-Per-Page"));
        if (t.headers().get("X-Total") != null)
            total = Integer.parseInt(t.headers().get("X-Total"));
        onSuccess(t, perPage, total);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        BaseView view = weakReference.get();
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            view.showErrorMsg(getErrorMessage(responseBody));
        } else if (e instanceof IOException) {
            view.showNoInternetMsg(null);
        } else {
            view.showErrorMsg(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
