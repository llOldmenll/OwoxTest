package com.oldmen.owoxtest.presentation.ui.pager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.oldmen.owoxtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerActivity extends MvpAppCompatActivity {

    @BindView(R.id.image_full_size)
    ImageView mImageFullSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
    }
}
