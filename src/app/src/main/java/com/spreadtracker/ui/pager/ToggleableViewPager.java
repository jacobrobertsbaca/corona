package com.spreadtracker.ui.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * A ViewPager for which swiping can be toggled on and off.
 */
public class ToggleableViewPager extends ViewPager {

    /**
     * If true, the user can swipe between pages, otherwise, they cannot.
     */
    public boolean isPagingEnabled = true;

    public ToggleableViewPager(@NonNull Context context) {
        super(context);
    }

    public ToggleableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }
}
