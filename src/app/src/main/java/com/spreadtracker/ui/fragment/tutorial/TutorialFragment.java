package com.spreadtracker.ui.fragment.tutorial;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.adapter.TutorialPagerAdapter;
import com.spreadtracker.ui.fragment.BaseFragment;

public class TutorialFragment extends BaseFragment<MainActivity> {

    @Override
    protected int getLayout() { return R.layout.fragment_tutorial; }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Set up view pager
        ViewPager viewPager = root.findViewById(R.id.fragment_tutorial_viewpager);
        TutorialPagerAdapter adapter = new TutorialPagerAdapter(App.getContext());
        viewPager.setAdapter(adapter);

        // Add tutorial pages
        // The last page should be an empty page that fades away into MainActivity's real layout when swiping to it
        adapter
                .addPage(new TutorialPage(R.layout.view_tutorial_a))
                .addPage(new TutorialPage(R.layout.view_tutorial_b))
                .addPage(new TutorialPage(0));

        // Setup tabs
        TabLayout tabLayout = root.findViewById(R.id.fragment_tutorial_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == adapter.getCount() - 2) {
                    // When scrolling to the last page, fade out
                    root.setAlpha(1 - positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                // When the last page is selected, disable the root view
                if (position == adapter.getCount() - 1) {
                    root.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
