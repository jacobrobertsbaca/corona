package com.spreadtracker.ui.fragment.settings.general;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

public class General_PhysicalActivitySettingsFragment extends SettingsFragment {
    public final static String SETTINGS_GENERAL_PHYSICALACTIVTY = GeneralSettingsFragment.SETTINGS_GENERAL_ROOT + "physicalactivity";

    @Override
    protected int getTitle() {
        return R.string.settings_general_physicalactivity_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(SETTINGS_GENERAL_PHYSICALACTIVTY,
                        new RadioSetting(getString(R.string.settings_general_physicalactivity_active)),
                        new RadioSetting(getString(R.string.settings_general_physicalactivity_moderate)),
                        new RadioSetting(getString(R.string.settings_general_physicalactivity_inactive))))
                .build();
    }
}
