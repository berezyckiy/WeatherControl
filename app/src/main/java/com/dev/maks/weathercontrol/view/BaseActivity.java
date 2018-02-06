package com.dev.maks.weathercontrol.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dev.maks.weathercontrol.R;

public abstract class BaseActivity extends AppCompatActivity {

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public abstract void initRecycler();
}
