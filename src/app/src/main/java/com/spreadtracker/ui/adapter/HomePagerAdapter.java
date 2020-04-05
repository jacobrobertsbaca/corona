package com.spreadtracker.ui.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.home.HomeFragment;
import com.spreadtracker.ui.fragment.info.InfoFragment;

/**
 * A {@link FragmentStatePagerAdapter} that controls sliding between the info screen
 * and the map screen in {@link MainActivity}
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private final static int ITEM_COUNT = 2;

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.create();
            case 1:
                return HomeFragment.create();
            default: throw new IllegalArgumentException("No such position");
        }
    }

    @Override
    public int getCount() { return ITEM_COUNT; }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getFragment(int position) {
        return registeredFragments.get(position);
    }
}
