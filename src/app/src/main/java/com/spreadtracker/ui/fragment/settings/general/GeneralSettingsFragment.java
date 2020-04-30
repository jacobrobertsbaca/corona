package com.spreadtracker.ui.fragment.settings.general;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.LabelSettings;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.DatePickerSetting;
import com.spreadtracker.ui.settings.value.IntegerPickerSetting;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

public class GeneralSettingsFragment extends SettingsFragment {

    public final static String SETTINGS_GENERAL_ROOT = "settings.general.";
    public final static String SETTINGS_GENERAL_BIRTHDAY = SETTINGS_GENERAL_ROOT + "birthdayDate";
    public final static String SETTINGS_GENERAL_GENDER = SETTINGS_GENERAL_ROOT + "gender";

    public final static String GENDER_AMBIGUOUS = "ambiguous";
    public final static String GENDER_MALE = "male";
    public final static String GENDER_FEMALE = "female";

    public final static String SETTINGS_GENERAL_HEIGHT = SETTINGS_GENERAL_ROOT + "height";

    @Override
    protected int getTitle() {
        return R.string.settings_general_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        IntegerPickerSetting heightPicker = new IntegerPickerSetting(R.string.settings_general_height_title,
                SETTINGS_GENERAL_HEIGHT,
                48, 120, 1,
                value -> {
                    long footComponent = value / 12;
                    long inchesComponent = value % 12;
                    return getString(R.string.settings_general_height_formatter, footComponent, inchesComponent);
                },
                getString(R.string.settings_general_height_dialog_title));

        IntegerPickerSetting weightPicker = new IntegerPickerSetting(R.string.settings_general_weight_title,
                SETTINGS_GENERAL_HEIGHT,
                50, 500, 5,
                value -> getString(R.string.settings_general_weight_formatter, value),
                getString(R.string.settings_general_height_dialog_title));

        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new DatePickerSetting(R.string.settings_general_birthday, R.string.settings_error_date, SETTINGS_GENERAL_BIRTHDAY),
                heightPicker,
                weightPicker,
                new NavigationSetting(R.string.settings_general_physicalactivity_title, R.id.action_generalSettingsFragment_to_general_PhysicalActivitySettingsFragment),
                new LabelSettings(R.string.settings_general_gender_title,
                        new RadioSettings(SETTINGS_GENERAL_GENDER,
                                new RadioSetting(GENDER_MALE, getString(R.string.settings_general_gender_male)),
                                new RadioSetting(GENDER_FEMALE, getString(R.string.settings_general_gender_female)),
                                new RadioSetting(GENDER_AMBIGUOUS, getString(R.string.settings_general_gender_ambiguous))))
        ).build();
    }
}
