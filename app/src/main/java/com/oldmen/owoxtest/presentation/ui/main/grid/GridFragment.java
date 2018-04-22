package com.oldmen.owoxtest.presentation.ui.main.grid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oldmen.owoxtest.R;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.mvp.main.grid.GridPresenter;
import com.oldmen.owoxtest.presentation.mvp.main.grid.GridView;
import com.oldmen.owoxtest.presentation.ui.main.pager.PagerFragment;
import com.oldmen.owoxtest.utils.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.oldmen.owoxtest.utils.Constants.EXTRA_IMAGES_INFO_LIST;

public class GridFragment extends MvpAppCompatFragment implements GridView {
    @InjectPresenter
    GridPresenter mPresenter;
    @BindView(R.id.recycler_grid)
    RecyclerView mRecycler;
    @BindView(R.id.search_grid)
    SearchView mSearch;
    @BindView(R.id.appbar_main)
    AppBarLayout mAppbar;

    private Unbinder unbinder;
    private GridAdapter mAdapter;
    private boolean mIsDownloading;
    private ArrayList<ImageUnsplash> mImagesInfo = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecycler();
        prepareTransitions();
        postponeEnterTransition();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
        mAppbar.requestFocus();

    }

    @Override
    public void onResume() {
        super.onResume();
        mSearch.clearFocus();
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(getGridLayoutManager());
        mAdapter = new GridAdapter(this, mPresenter, mImagesInfo);
        mRecycler.addItemDecoration(new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_item_divider_size)));
        mRecycler.setAdapter(mAdapter);

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int mCurrentPage = mPresenter.getCurrentPage();
                GridLayoutManager layoutManager = ((GridLayoutManager) recyclerView.getLayoutManager());
                if (layoutManager.findLastVisibleItemPosition() >= (mAdapter.getItemCount() - 6)
                        && mCurrentPage <= mPresenter.getPagesNumber() && !mIsDownloading) {
                    mIsDownloading = true;
                    mPresenter.loadImages();
                }
            }
        });
    }

    private void initSearch() {
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.saveSearchQuery(query);
                mPresenter.saveCurrentPage(0);
                mPresenter.loadImages();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private GridLayoutManager getGridLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int index = position % 5;
                switch (index) {
                    case 0:
                    case 1:
                    case 2:
                        return 2;
                    case 3:
                    case 4:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        return gridLayoutManager;
    }

    private void scrollToPosition() {
//        mRecycler.scrollToPosition(mPresenter.getCurrentPosition());
        mRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                int position = mPresenter.getCurrentPosition();
                mRecycler.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = mRecycler.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(position);

                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, true, true)) {
                    layoutManager.scrollToPosition(position);
//                    mRecycler.post(() -> layoutManager.scrollToPosition(position));
                }
            }
        });
    }

    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));
        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        RecyclerView.ViewHolder selectedVH =
                                mRecycler.findViewHolderForAdapterPosition(mPresenter.getCurrentPosition());
                        if (selectedVH == null || selectedVH.itemView == null) return;
                        sharedElements.put(names.get(0),
                                selectedVH.itemView.findViewById(R.id.image_view_grid_item));
                    }
                });
    }

    @Override
    public void updateRecycler(List<ImageUnsplash> images) {
        mAdapter.update(images);
        mIsDownloading = false;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void startPager(int position, ImageView imgView) {
        if (getExitTransition() != null)
            ((TransitionSet) getExitTransition()).excludeTarget(imgView, true);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_IMAGES_INFO_LIST, mImagesInfo);
        PagerFragment fragment = PagerFragment.newInstance(bundle);

        if (getFragmentManager() != null)
            getFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .addSharedElement(imgView, imgView.getTransitionName())
                    .replace(R.id.fragment_container_main, fragment, PagerFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
