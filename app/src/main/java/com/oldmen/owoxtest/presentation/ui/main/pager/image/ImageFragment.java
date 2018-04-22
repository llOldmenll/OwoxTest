package com.oldmen.owoxtest.presentation.ui.main.pager.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oldmen.owoxtest.R;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;
import com.oldmen.owoxtest.utils.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.oldmen.owoxtest.utils.Constants.ARG_IMAGE_INFO;

public class ImageFragment extends Fragment {
    @BindView(R.id.image)
    ImageView mImage;

    private Unbinder unbinder;
    private ImageUnsplash mImgInfo;
    private ImagePagerListener mListener;

    public static ImageFragment newInstance(ImageUnsplash image) {
        ImageFragment fragment = new ImageFragment();
        Bundle argument = new Bundle();
        argument.putParcelable(ARG_IMAGE_INFO, image);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        getArgs();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initImageView();
    }

    private void getArgs() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_IMAGE_INFO))
            mImgInfo = arguments.getParcelable(ARG_IMAGE_INFO);
    }

    private void initImageView() {
        if (mImgInfo != null)
            mImage.setTransitionName(mImgInfo.getSmallImgUrl());
        GlideApp.with(this)
                .load(mImgInfo.getRegularImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable>
                            target, boolean isFirstResource) {
                        if (getParentFragment() != null)
                            getParentFragment().startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (getParentFragment() != null)
                            getParentFragment().startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(mImage);
        mImage.setOnClickListener(v -> {
            if (mListener != null) mListener.imageClicked();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof ImagePagerListener)
            mListener = (ImagePagerListener) getParentFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ImagePagerListener {
        void imageClicked();
    }
}

