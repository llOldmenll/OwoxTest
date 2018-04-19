package com.oldmen.owoxtest.presentation.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oldmen.owoxtest.R;
import com.oldmen.owoxtest.domain.models.ImageInfo;
import com.oldmen.owoxtest.presentation.mvp.main.MainPresenter;
import com.oldmen.owoxtest.presentation.mvp.main.MainView;
import com.oldmen.owoxtest.utils.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @InjectPresenter
    MainPresenter mPresenter;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.recycler_main)
    RecyclerView mRecycler;

    private MainAdapter mAdapter;
    private int mPagesCount;
    private int mCurrentPage;
    private boolean mIsDownloading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecycler();
        mPresenter.loadImages(1);
    }

    private void initRecycler() {
        mAdapter = new MainAdapter(this, mPresenter, new ArrayList<ImageInfo>());
        mRecycler.setLayoutManager(getGridLayoutManager());
        mRecycler.addItemDecoration(new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_item_divider_size)));
        mRecycler.setAdapter(mAdapter);

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = ((GridLayoutManager) recyclerView.getLayoutManager());
                if (layoutManager.findLastVisibleItemPosition() == (mAdapter.getItemCount() - 1)
                        && mCurrentPage < mPagesCount && !mIsDownloading) {
                    mIsDownloading = true;
                    mPresenter.loadImages(mCurrentPage + 1);
                }
            }
        });
    }

    private GridLayoutManager getGridLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this, 6);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int index = position % 5;
                switch (index) {
                    case 0:
                        return 2;
                    case 1:
                        return 2;
                    case 2:
                        return 2;
                    case 3:
                        return 3;
                    case 4:
                        return 3;
                    default:
                        return 1;
                }
            }
        });

        return gridLayoutManager;
    }

    @Override
    public void showNoInternetMsg(DialogInterface.OnClickListener listener) {
        mIsDownloading = false;
    }

    @Override
    public void showErrorMsg(String msg) {
        mIsDownloading = false;
    }

    @Override
    public void appendRecycler(List<ImageInfo> imagesInfo) {
        mCurrentPage++;
        mAdapter.append(imagesInfo);
        mIsDownloading = false;
    }

    @Override
    public void refreshRecycler(List<ImageInfo> imagesInfo, int imgsPerPage, int imgsTotal) {
        mAdapter.refresh(imagesInfo);
        mPagesCount = (int) Math.ceil((double) imgsTotal / (double) imgsPerPage);
        mCurrentPage = 1;
        mIsDownloading = false;
    }
}
