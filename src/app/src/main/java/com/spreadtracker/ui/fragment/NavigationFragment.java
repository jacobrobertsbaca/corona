package com.spreadtracker.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.spreadtracker.R;

public abstract class NavigationFragment<TActivity extends FragmentActivity, TViewModel extends ViewModel>
        extends ViewModelFragment<TActivity, TViewModel> {
    private NavigationBuilder mNavigationBuilder;
    private ViewGroup mToolbar;
    protected View childRoot;

    private TextView mTitleView, mLeftTextView, mRightTextView;
    private ImageView mLeftButtonView, mRightButtonView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create a linear layout to house the toolbar and the content
        LinearLayout parent = new LinearLayout(inflater.getContext());
        parent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        parent.setOrientation(LinearLayout.VERTICAL);

        // Create toolbar
        mToolbar = (ViewGroup) inflater.inflate(R.layout.view_navigation_toolbar, parent);
        mTitleView = mToolbar.findViewById(R.id.navigation_toolbar_title);
        mLeftButtonView = mToolbar.findViewById(R.id.navigation_toolbar_button_left);
        mRightButtonView = mToolbar.findViewById(R.id.navigation_toolbar_button_right);
        mLeftTextView = mToolbar.findViewById(R.id.navigation_toolbar_text_left);
        mRightTextView = mToolbar.findViewById(R.id.navigation_toolbar_text_right);

        // Create child content
        childRoot = inflater.inflate(getLayout(), parent);
        root = parent;

        // Set up navigation for the first time
        updateNavigation(buildNavigation(inflater.getContext()));

        inOnCreateView(container, savedInstanceState);

        return parent;
    }

    public void updateNavigation (@NonNull NavigationBuilder builder) {
        mNavigationBuilder = builder;

        root.setBackgroundColor(builder.getBackgroundColor());
        mTitleView.setText(builder.getTitle());
        mTitleView.setVisibility(builder.getTitleVisibility());

        if (builder.getLeftButtonDrawable() != 0)
            mLeftButtonView.setImageResource(builder.getLeftButtonDrawable());
        else mLeftButtonView.setImageResource(android.R.color.transparent);
        if (builder.getRightButtonDrawable() != 0)
            mRightButtonView.setImageResource(builder.getRightButtonDrawable());
        else mRightButtonView.setImageResource(android.R.color.transparent);
        mLeftButtonView.setOnClickListener(builder.getLeftButtonCallback());
        mRightButtonView.setOnClickListener(builder.getRightButtonCallback());

        mRightButtonView.setVisibility(builder.getRightButtonVisibility());
        mLeftButtonView.setVisibility(builder.getLeftButtonVisibility());

        mRightTextView.setVisibility(builder.getRightTextVisibility());
        mRightTextView.setOnClickListener(builder.getRightButtonCallback());
        mLeftTextView.setVisibility(builder.getLeftTextVisibility());
        mLeftTextView.setOnClickListener(builder.getLeftButtonCallback());
    }

    @NonNull
    public NavigationBuilder getNavigation () { return mNavigationBuilder; }

    @NonNull
    protected abstract NavigationBuilder buildNavigation(@NonNull Context context);
}
