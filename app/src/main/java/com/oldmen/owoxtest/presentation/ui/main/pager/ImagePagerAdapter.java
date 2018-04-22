package com.oldmen.owoxtest.presentation.ui.main.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.presentation.ui.main.pager.image.ImageFragment;

import java.util.List;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {
    private List<ImageUnsplash> mImages;

    ImagePagerAdapter(Fragment fragment, List<ImageUnsplash> images) {
        super(fragment.getChildFragmentManager());
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(mImages.get(position));
    }

    void update(List<ImageUnsplash> images) {
        mImages.clear();
        mImages.addAll(images);
        notifyDataSetChanged();
    }
}
