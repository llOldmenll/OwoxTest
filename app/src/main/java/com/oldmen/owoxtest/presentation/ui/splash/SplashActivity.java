package com.oldmen.owoxtest.presentation.ui.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oldmen.owoxtest.R;
import com.oldmen.owoxtest.presentation.mvp.splash.SplashPresenter;
import com.oldmen.owoxtest.presentation.mvp.splash.SplashView;
import com.oldmen.owoxtest.presentation.ui.main.MainActivity;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {
    @InjectPresenter
    SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPresenter.loadImages();
    }

    @Override
    public void showNoInternetMsg(DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getString(R.string.no_internet_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok), listener);
        alertDialog.show();
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
