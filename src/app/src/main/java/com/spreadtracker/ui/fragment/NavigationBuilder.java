package com.spreadtracker.ui.fragment;

import android.content.Context;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.spreadtracker.R;

public class NavigationBuilder {
    private NavigationFragment mFragment;
    private Context mContext;

    private String mTitle;
    private int mTitleVisibility = View.VISIBLE;
    private @ColorInt int mBackgroundColor;

    /* Buttons */
    private int mLeftButtonVisibility = View.VISIBLE;
    private @DrawableRes int mLeftButtonDrawable = R.drawable.ic_close;
    private View.OnClickListener mLeftButtonCallback = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavigationBuilder.this.mFragment.activity.onBackPressed();
        }
    };
    private int mRightButtonVisibility = View.GONE;
    private View.OnClickListener mRightButtonCallback = null;
    private @DrawableRes int mRightButtonDrawable;

    /* Text buttons */
    private int leftTextVisibility = View.GONE;
    private String leftText;
    private int rightTextVisibility = View.GONE;
    private String rightText;

    public NavigationBuilder(@NonNull NavigationFragment fragment, @NonNull Context ctx) {
        mFragment = fragment;
        mContext = ctx;
        mBackgroundColor = ContextCompat.getColor(mContext, R.color.overlayHealthy);
    }

    public NavigationBuilder setTitle (@StringRes int stringRes) {
        mTitle = mFragment.getString(stringRes);
        return this;
    }

    public NavigationBuilder setTitle (String title) {
        mTitle = title;
        return this;
    }

    public NavigationBuilder setTitleVisibility (int visibility) {
        mTitleVisibility = visibility;
        return this;
    }

    public NavigationBuilder setLeftButtonVisibility (int visibility) {
        mLeftButtonVisibility = visibility;
        return this;
    }

    public NavigationBuilder setLeftButtonDrawable (@DrawableRes int drawableRes) {
        mLeftButtonDrawable = drawableRes;
        return this;
    }

    public NavigationBuilder setLeftButtonCallback (View.OnClickListener callback) {
        mLeftButtonCallback = callback;
        return this;
    }

    public NavigationBuilder setRightButtonVisibility (int visibility) {
        mRightButtonVisibility = visibility;
        return this;
    }

    public NavigationBuilder setRightButtonDrawable (@DrawableRes int drawableRes) {
        mRightButtonDrawable = drawableRes;
        return this;
    }

    public NavigationBuilder setRightButtonCallback (View.OnClickListener callback) {
        mRightButtonCallback = callback;
        return this;
    }

    public NavigationBuilder setBackgroundColorRes (@ColorRes int colorRes) {
        mBackgroundColor = ContextCompat.getColor(mContext, colorRes);
        return this;
    }

    public NavigationBuilder setBackgroundColor (@ColorInt int color) {
        mBackgroundColor = color;
        return this;
    }

    public NavigationBuilder setLeftTextVisibility(int leftTextVisibility) {
        this.leftTextVisibility = leftTextVisibility;
        return this;
    }

    public NavigationBuilder setLeftText(String leftText) {
        this.leftText = leftText;
        return this;
    }

    public NavigationBuilder setLeftText (@StringRes int resId) {
        this.leftText = mContext.getString(resId);
        return this;
    }

    public NavigationBuilder setRightTextVisibility(int rightTextVisibility) {
        this.rightTextVisibility = rightTextVisibility;
        return this;
    }

    public NavigationBuilder setRightText(String rightText) {
        this.rightText = rightText;
        return this;
    }

    public NavigationBuilder setRightText(@StringRes int resId) {
        this.rightText = mContext.getString(resId);
        return this;
    }

    public String getTitle () { return mTitle; }
    public int getTitleVisibility () { return mTitleVisibility; }
    public int getLeftButtonVisibility () { return mLeftButtonVisibility; }
    public @DrawableRes int getLeftButtonDrawable () { return mLeftButtonDrawable; }
    public View.OnClickListener getLeftButtonCallback () { return mLeftButtonCallback; }
    public int getRightButtonVisibility () { return mRightButtonVisibility; }
    public @DrawableRes int getRightButtonDrawable () { return mRightButtonDrawable; }
    public View.OnClickListener getRightButtonCallback () { return mRightButtonCallback; }
    public @ColorInt int getBackgroundColor () { return mBackgroundColor; }
    public String getRightText() { return rightText; }
    public int getRightTextVisibility() { return rightTextVisibility; }
    public String getLeftText() { return leftText; }
    public int getLeftTextVisibility() { return leftTextVisibility; }

}
