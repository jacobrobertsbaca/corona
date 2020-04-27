package com.spreadtracker.ui.fragment.settings.general;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.DatePickerSetting;

public class GeneralSettingsFragment extends SettingsFragment {

    public final static String SETTINGS_GENERAL_ROOT = "settings.general.";
    public final static String SETTINGS_GENERAL_BIRTHDAY = SETTINGS_GENERAL_ROOT + "birthday";

    @Override
    protected int getTitle() {
        return R.string.settings_general_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new DatePickerSetting(R.string.settings_general_birthday, R.string.settings_general_birthday_error, SETTINGS_GENERAL_BIRTHDAY),
                new NavigationSetting(R.string.settings_general_physicalactivity_title, R.id.action_generalSettingsFragment_to_general_PhysicalActivitySettingsFragment)
        ).build();
    }
}
