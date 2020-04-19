package com.spreadtracker.ui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.spreadtracker.ui.adapter.OverlayPagerAdapter;

/*
 * Taken from StackOverflow: https://stackoverflow.com/questions/13477820/android-vertical-viewpager
 * and modified to fit the purposes of the scrolling overlay on the home screen.
 * - Jacob
 */
public class OverlayViewPager extends ViewPager {

    /**
     * A percentage that determines how far (in percentage of the total height of the screen) from the bottom
     * of the screen that the user is allowed to scroll.
     */
    private final static double ALLOW_SCROLLING_THRESHOLD = 0.125;

    public OverlayViewPager(Context context) {
        super(context);
        init();
    }

    public OverlayViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        // Only allow swiping on the map screen (when the overlay is hidden)
        // when the user swipes from the very top of the page
        if (getCurrentItem() == OverlayPagerAdapter.PAGE_MAP && ((double) ev.getY() / getHeight()) > ALLOW_SCROLLING_THRESHOLD)
            return false;

        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Only allow swiping on the map screen (when the overlay is hidden)
        // when the user swipes from the very top of the page
        if (ev.getAction() == MotionEvent.ACTION_DOWN && getCurrentItem() == OverlayPagerAdapter.PAGE_MAP && ((double) ev.getY() / getHeight()) >ALLOW_SCROLLING_THRESHOLD)
            return false;
        return super.onTouchEvent(swapXY(ev));
    }
}