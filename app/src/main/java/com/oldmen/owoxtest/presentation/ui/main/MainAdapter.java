package com.oldmen.owoxtest.presentation.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.oldmen.owoxtest.R;
import com.oldmen.owoxtest.domain.models.ImageInfo;
import com.oldmen.owoxtest.presentation.mvp.main.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private Context mContext;
    private MainPresenter mPresenter;
    private List<ImageInfo> mImagesInfo;


    public MainAdapter(Context context, MainPresenter presenter, List<ImageInfo> imagesInfo) {
        mContext = context;
        mPresenter = presenter;
        mImagesInfo = imagesInfo;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        holder.bindView(mImagesInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return mImagesInfo.size();
    }

    void append(List<ImageInfo> imagesInfo) {
        int startPosition = mImagesInfo.size() - 1;
        mImagesInfo.addAll(imagesInfo);
        notifyItemRangeChanged(startPosition, imagesInfo.size());
    }

    void refresh(List<ImageInfo> imagesInfo) {
        mImagesInfo.clear();
        mImagesInfo.addAll(imagesInfo);
        notifyDataSetChanged();
    }

    class MainHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_item)
        ImageView mImageView;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(ImageInfo imgInfo) {
            Glide.with(mContext)
                    .load(imgInfo.getImgUrls().getThumbnail()).into(mImageView);
        }
    }

}
