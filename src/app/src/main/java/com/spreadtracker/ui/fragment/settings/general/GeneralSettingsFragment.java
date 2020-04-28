package com.spreadtracker.ui.fragment.settings.general;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.LabelSettings;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.DatePickerSetting;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

public class GeneralSettingsFragment extends SettingsFragment {

    public final static String SETTINGS_GENERAL_ROOT = "settings.general.";
    public final static String SETTINGS_GENERAL_BIRTHDAY = SETTINGS_GENERAL_ROOT + "birthdayDate";
    public final static String SETTINGS_GENERAL_GENDER = SETTINGS_GENERAL_ROOT + "gender";

    public final static String GENDER_AMBIGUOUS = "ambiguous";
    public final static String GENDER_MALE = "male";
    public final static String GENDER_FEMALE = "female";

    @Override
    protected int getTitle() {
        return R.string.settings_general_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new DatePickerSetting(R.string.settings_general_birthday, R.string.settings_error_date, SETTINGS_GENERAL_BIRTHDAY),
                new NavigationSetting(R.string.settings_general_physicalactivity_title, R.id.action_generalSettingsFragment_to_general_PhysicalActivitySettingsFragment),
                new LabelSettings(R.string.settings_general_gender_title,
                        new RadioSettings(SETTINGS_GENERAL_GENDER,
                                new RadioSetting(GENDER_MALE, getString(R.string.settings_general_gender_male)),
                                new RadioSetting(GENDER_FEMALE, getString(R.string.settings_general_gender_female)),
                                new RadioSetting(GENDER_AMBIGUOUS, getString(R.string.settings_general_gender_ambiguous))))
        ).build();
    }
}
