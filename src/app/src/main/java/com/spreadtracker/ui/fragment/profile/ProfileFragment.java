package com.spreadtracker.ui.fragment.profile;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;

public class ProfileFragment extends SettingsFragment {

    @Override
    protected int getTitle() { return R.string.fragment_profile_title; }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new NavigationSetting(R.string.fragment_settings_general_title, R.id.action_profileFragment_to_generalSettingsFragment),
                new NavigationSetting(R.string.fragment_settings_medicalhistory_title, R.id.action_profileFragment_to_medicalHistorySettingsFragment))
                .build();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }
}