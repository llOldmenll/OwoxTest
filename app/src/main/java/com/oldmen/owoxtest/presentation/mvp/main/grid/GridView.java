package com.oldmen.owoxtest.presentation.mvp.main.grid;

import android.widget.ImageView;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.mvp.base.BaseView;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface GridView extends MvpView, BaseView {

    void updateRecycler(List<ImageUnsplash> images);

    void startPager(int position, ImageView imgView);

}
