package com.bignerdranch.android.proFavRestos;

import java.util.ArrayList;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;

public class RestoPagerActivity extends FragmentActivity implements RestoFragment.Callbacks {
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Resto> restos = RestoDatabase.get(this).getRestos();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return restos.size();
            }
            @Override
            public Fragment getItem(int pos) {
                long restoId =  restos.get(pos).getId();
                return RestoFragment.newInstance(restoId);
            }
        }); 

        Long restoId = (Long)getIntent().getSerializableExtra(RestoFragment.EXTRA_RESTO_ID);
        for (int i = 0; i < restos.size(); i++) {
            if (restos.get(i).getId() == restoId) {
                mViewPager.setCurrentItem(i);
                break;
            } 
        }
    }

    public void onRestoUpdated(Resto resto) {
        // do nothing        
    }
}
