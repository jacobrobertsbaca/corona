package com.spreadtracker.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.spreadtracker.ui.fragment.EmptyFragment;
import com.spreadtracker.ui.fragment.home.OverlayFragment;

/**
 * A {@link FragmentStatePagerAdapter} that controls the layout of the pages used to swipe between
 * the map screens and overlay screen (which shows the percentage chance of having contracted the coronavirus.)
 */
public class OverlayPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * The number of screens that can be scrolled between vertically.
     */
    private static final int ITEM_COUNT = 2;

    public static final int PAGE_MAP = 1;
    public static final int PAGE_OVERLAY = 0;

    public OverlayPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem (int position) {
        switch (position) {
            case 0:
                return OverlayFragment.create();
            case 1:
                return EmptyFragment.create();
            default:
                throw new IllegalArgumentException("Invalid position.");
        }
    }

    @Override
    public int getCount () { return ITEM_COUNT; }
}
