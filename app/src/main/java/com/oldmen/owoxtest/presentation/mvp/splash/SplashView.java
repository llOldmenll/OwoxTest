package com.oldmen.owoxtest.presentation.mvp.splash;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.oldmen.owoxtest.presentation.mvp.base.BaseView;

@StateStrategyType(SkipStrategy.class)
public interface SplashView extends MvpView, BaseView {

    void startMainActivity();

}
