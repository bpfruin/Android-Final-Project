package com.bignerdranch.android.proFavRestos;

import android.support.v4.app.Fragment;

public class RestoActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
       Long restoId = (Long)getIntent()
            .getSerializableExtra(RestoFragment.EXTRA_RESTO_ID);
        return RestoFragment.newInstance(restoId);
    }
}
