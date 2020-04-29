package com.spreadtracker.ui.fragment.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;
import com.spreadtracker.ui.fragment.profile.ProfileFragmentViewModel;

public abstract class SettingsFragment extends NavigationFragment<MainActivity, ProfileFragmentViewModel> {
    protected abstract @StringRes int getTitle ();

    @NonNull
    @Override
    protected NavigationBuilder buildNavigation(@NonNull Context context) {
        return new NavigationBuilder(this, context)
                .setTitle(getTitle());
    }

    @Nullable
    @Override
    protected Class<ProfileFragmentViewModel> getViewModelClass() {
        return ProfileFragmentViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root.getId() == R.id.fragment_settings_settingsHolder) createSettingsHierarchy((ViewGroup)root);
        else createSettingsHierarchy((ViewGroup) root.findViewById(R.id.fragment_settings_settingsHolder));
    }

    protected abstract void createSettingsHierarchy(ViewGroup container);
}
