package com.spreadtracker.ui.fragment.home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.adapter.OverlayPagerAdapter;
import com.spreadtracker.ui.fragment.ViewModelFragment;
import com.spreadtracker.ui.fragment.info.InfoFragment;
import com.spreadtracker.ui.util.OverlayViewPager;

/**
 * Represents the home fragment--the "home screen" page of the app.
 * Contains the heatmap and the draggable percentage overlay.
 */
public class HomeFragment extends ViewModelFragment<MainActivity, HomeFragmentViewModel> {

    public static HomeFragment create() {
        return new HomeFragment();
    }

    private ImageView mInfoButton;
    private ImageView mProfileButton;
    private OverlayViewPager mViewPager;

    private ArgbEvaluator mColorInterpolator;

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInfoButton = root.findViewById(R.id.fragment_home_infoButton);
        mProfileButton = root.findViewById(R.id.fragment_home_profileButton);

        // Open the info screen when the info button is clicked
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() == 0) {
                    // When we open the info screen from the overlay percentage screen,
                    // we want the info screen to have the same background color as the overlay percentage
                    // to avoid confusion.
                    Bundle args = new Bundle();
                    args.putBoolean(InfoFragment.ARGS_USE_INFECTED_COLOR, true);
                    activity.getNav().navigate(R.id.action_homeFragment_to_infoFragment, args);
                } else activity.getNav().navigate(R.id.action_homeFragment_to_infoFragment);
            }
        });

        // Open the profile screen when the info button is clicked
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getNav().navigate(R.id.action_homeFragment_to_profileFragment);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViewPager();
    }

    /**
     * Creates the pager adapter needed to create the draggable percentage overlay.
     */
    private void initializeViewPager () {
        final int mButtonColorLight = ContextCompat.getColor(activity, R.color.white);
        final int mButtonColorDark = ContextCompat.getColor(activity, R.color.grayDark);

        mViewPager = root.findViewById(R.id.fragment_home_viewPager);
        mViewPager.setAdapter(new OverlayPagerAdapter(getChildFragmentManager()));
        mViewPager.setCurrentItem(1); // Set's to second page so that the overlay is hidden by default
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    // As we drag the overlay down, the icons in the upper left and right hand corners of the screen
                    // should become white
                    if (mInfoButton == null || mProfileButton == null) return;
                    if (mColorInterpolator == null) mColorInterpolator = new ArgbEvaluator();
                    int interp = (int) mColorInterpolator.evaluate(1 - positionOffset, mButtonColorDark, mButtonColorLight);
                    mInfoButton.setColorFilter(interp);
                    mProfileButton.setColorFilter(interp);
                }
            }
            @Override public void onPageSelected(int position) {
                //
            }
            @Override public void onPageScrollStateChanged(int state) {}
        });
    }

    /*
     * For use with ViewModelFragment
     */
    @NonNull
    @Override
    protected Class<HomeFragmentViewModel> getViewModelClass() { return HomeFragmentViewModel.class; }

    @Override
    protected int getLayout() { return R.layout.fragment_home; }
}
