package com.oldmen.owoxtest.presentation.mvp.main.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.mvp.base.BaseView;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface PagerView extends MvpView, BaseView {

    void updatePager(List<ImageUnsplash> images);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideFooter();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showFooter();

    void showImageLoadingMsg(String msg);
}
