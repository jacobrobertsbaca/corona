package com.spreadtracker.ui.activity.main;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.spreadtracker.R;
import com.spreadtracker.ui.adapter.HomePagerAdapter;
import com.spreadtracker.ui.fragment.info.InfoFragment;
import com.spreadtracker.ui.pager.ToggleableViewPager;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.math.MathUtils;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final static int PAGE_INFO = 0;
    private final static int PAGE_HOME = 1;

    private ToggleableViewPager mViewPager;
    private HomePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewPager();
    }

    private void initializeViewPager () {
        // Set up main view pager
        mViewPager = findViewById(R.id.activity_main_viewPager);
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(1); // Go to map screen first
        mViewPager.isPagingEnabled = false; // Prevent paging by default
    }

    public void infoPage (@LayoutRes int infoLayout) {
        goToPage(PAGE_INFO);
        InfoFragment infoFragment = (InfoFragment) mPagerAdapter.getFragment(PAGE_INFO);
        infoFragment.inflateInfo(infoLayout);
    }

    public void homePage () {
        goToPage(PAGE_HOME);
    }

    private void goToPage(int page) {
        switch (page) {
            // On the info page, you should be able to swipe back to the home page
            case PAGE_INFO:
                mViewPager.setCurrentItem(PAGE_INFO);
                mViewPager.isPagingEnabled = true;
                break;

            // On the home page, you shouldn't be able to swipe back to the info page,
            // because you are interacting with the map
            case PAGE_HOME:
                mViewPager.setCurrentItem(PAGE_HOME);
                mViewPager.isPagingEnabled = false;
                break;

            default:
                throw new IllegalArgumentException("No such page!");
        }
    }
}
