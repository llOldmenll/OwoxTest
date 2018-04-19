package com.oldmen.owoxtest.presentation.mvp.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.oldmen.owoxtest.domain.models.ImageInfo;
import com.oldmen.owoxtest.presentation.mvp.base.BaseView;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView, BaseView {

    void appendRecycler(List<ImageInfo> imagesInfo);

    void refreshRecycler(List<ImageInfo> imagesInfo, int imgsPerPage, int imgsTotal);

}
