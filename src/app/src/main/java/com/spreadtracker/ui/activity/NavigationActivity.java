package com.spreadtracker.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

/**
 * Represents an abstract activity that has access to a {@link androidx.navigation.NavController}
 */
public abstract class NavigationActivity extends AppCompatActivity {
    private NavController mNavigation;

    public NavController getNav() {
        if (mNavigation != null) return mNavigation;
        NavHostFragment navFragment = getNavHost();
        mNavigation = navFragment.getNavController();
        return mNavigation;
    }

    @NonNull
    protected abstract NavHostFragment getNavHost ();
}
