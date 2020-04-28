package com.spreadtracker.ui.settings;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;

public class NavigationSettingsPage extends SettingsPage {

    private NavigationFragment mFragment;
    private Runnable mOnSave;

    public NavigationSettingsPage(@NonNull NavigationFragment fragment,
                                  @NonNull ViewGroup parentView,
                                  @Nullable Runnable onSave,
                                  SettingsNode... children) {
        super(parentView, children);
        mFragment = fragment;
        mOnSave = onSave;

        // Set up the navigation how we would like it to be
        NavigationBuilder newNav = mFragment.getNavigation();
        newNav.setRightTextVisibility(View.GONE);
        newNav.setRightText(R.string.toolbar_done);
        newNav.setRightButtonCallback(v -> {
            // Save children element's state
            if (canSave()) {
                saveState();
                NavigationBuilder nav = mFragment.getNavigation();
                nav.setRightTextVisibility(View.GONE);
                mFragment.updateNavigation(nav);
                if (mOnSave != null) mOnSave.run();
            }
        });
        mFragment.updateNavigation(newNav);
    }

    public NavigationSettingsPage(@NonNull NavigationFragment fragment,
                                  @NonNull ViewGroup parentView,
                                  SettingsNode... children) {
        this(fragment, parentView, null, children);
    }

    @Override
    public void notifyChildrenDirty(boolean dirty) {
        // When a child sets a dirty state, we will show or hide the save button
        NavigationBuilder newNav = mFragment.getNavigation();
        newNav.setRightTextVisibility(dirty ? View.VISIBLE : View.GONE);
        mFragment.updateNavigation(newNav);
    }

    @NonNull
    @Override
    public NavigationSettingsPage build() {
        super.build();
        return this;
    }

    @NonNull
    public NavigationFragment getFragment () {
        return mFragment;
    }

    public NavController getNavController () {
        if (!(mFragment.getActivity() instanceof MainActivity)) return null;
        return ((MainActivity)mFragment.getActivity()).getNav();
    }
}
