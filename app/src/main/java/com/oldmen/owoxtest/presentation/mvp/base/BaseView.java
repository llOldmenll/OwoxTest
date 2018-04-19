package com.oldmen.owoxtest.presentation.mvp.base;

import android.content.DialogInterface;

public interface BaseView {

    void showNoInternetMsg(DialogInterface.OnClickListener listener);

    void showErrorMsg(String msg);

}
