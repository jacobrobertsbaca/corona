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

    public NavigationSettingsPage(@NonNull NavigationFragment fragment,
                                  @NonNull ViewGroup parentView,
                                  SettingsNode... children) {
        super(parentView, children);
        mFragment = fragment;

        // Set up the navigation how we would like it to be
        NavigationBuilder newNav = mFragment.getNavigation();
        newNav.setRightButtonVisibility(View.GONE);
        newNav.setRightButtonDrawable(R.drawable.ic_checkmark);
        newNav.setRightButtonCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save children element's state
                if (canSave()) {
                    saveState();
                    NavigationBuilder nav = mFragment.getNavigation();
                    nav.setRightButtonVisibility(View.GONE);
                    mFragment.updateNavigation(nav);
                }
            }
        });
        mFragment.updateNavigation(newNav);
    }

    @Override
    public void setDirty(boolean dirty) {
        // When a child sets a dirty state, we will show or hide the save button
        NavigationBuilder newNav = mFragment.getNavigation();
        newNav.setRightButtonVisibility(dirty ? View.VISIBLE : View.GONE);
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
