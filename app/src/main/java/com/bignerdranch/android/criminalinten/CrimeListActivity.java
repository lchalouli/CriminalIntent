package com.bignerdranch.android.criminalinten;

import android.support.v4.app.Fragment;

/**
 * Created by lchalouli on 02/08/2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
