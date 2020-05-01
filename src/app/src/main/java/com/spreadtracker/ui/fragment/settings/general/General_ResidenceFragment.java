package com.spreadtracker.ui.fragment.settings.general;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

public class General_ResidenceFragment extends SettingsFragment {

    public static String SETTINGS_GENERAL_RESIDENCE = GeneralSettingsFragment.SETTINGS_GENERAL_ROOT + "residence";
    public static String RESIDENCE_HOUSE = "house";
    public static String RESIDENCE_APARTMENT = "apartment";
    public static String RESIDENCE_ASSISTEDLIVING = "assistedliving";

    @Override
    protected int getTitle() {
        return R.string.settings_general_residence_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(SETTINGS_GENERAL_RESIDENCE,
                        new RadioSetting(RESIDENCE_HOUSE, R.string.settings_general_residence_house),
                        new RadioSetting(RESIDENCE_APARTMENT, R.string.settings_general_residence_apartment),
                        new RadioSetting(RESIDENCE_ASSISTEDLIVING, R.string.settings_general_residence_assistedliving)))
                .build();
    }
}
