package com.dev.maks.weathercontrol.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dev.maks.weathercontrol.R;

import butterknife.BindView;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    public abstract void initRecycler();
}
