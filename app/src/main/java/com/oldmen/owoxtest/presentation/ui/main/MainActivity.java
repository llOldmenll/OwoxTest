package com.oldmen.owoxtest.presentation.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.oldmen.owoxtest.R;
import com.oldmen.owoxtest.presentation.ui.main.grid.GridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_main, new GridFragment(), GridFragment.class.getSimpleName())
                .commit();
    }

}
