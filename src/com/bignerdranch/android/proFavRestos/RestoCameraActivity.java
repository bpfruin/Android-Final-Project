package com.bignerdranch.android.proFavRestos;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.Window;
import android.view.WindowManager;

public class RestoCameraActivity extends SingleFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected Fragment createFragment() {
        return new RestoCameraFragment();
    }
}

