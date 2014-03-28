package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class RestoListActivity extends SingleFragmentActivity
    implements RestoListFragment.Callbacks, RestoFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new RestoListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    public void onRestoSelected(Resto resto) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            // start an instance of RestoPagerActivity
            Intent i = new Intent(this, RestoPagerActivity.class);
            i.putExtra(RestoFragment.EXTRA_RESTO_ID, resto.getId());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = RestoFragment.newInstance(resto.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            } 

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    public void onRestoUpdated(Resto resto) {
        FragmentManager fm = getSupportFragmentManager();
        RestoListFragment listFragment = (RestoListFragment)
                fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }
}
