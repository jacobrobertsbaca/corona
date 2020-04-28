package com.spreadtracker.ui.fragment.profile;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;

public class ProfileFragment extends SettingsFragment {

    @Override
    protected int getTitle() { return R.string.profile_title; }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new NavigationSetting(R.string.settings_general_title, R.id.action_profileFragment_to_generalSettingsFragment),
                new NavigationSetting(R.string.settings_medicalhistory_title, R.id.action_profileFragment_to_medicalHistorySettingsFragment),
                new NavigationSetting(R.string.settings_testdata_title, R.id.action_profileFragment_to_testDataFragment)
        ).build();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }
}
