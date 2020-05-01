package com.spreadtracker.ui.fragment.settings.general;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.susceptibility.ISusceptibilityProvider;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.io.SettingsStore;
import com.spreadtracker.ui.settings.navigation.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.DatePickerSetting;
import com.spreadtracker.ui.settings.value.IntegerPickerSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class GeneralSettingsFragment extends SettingsFragment {

    public final static String SETTINGS_GENERAL_ROOT = "settings.general.";
    public final static String SETTINGS_GENERAL_BIRTHDAY = SETTINGS_GENERAL_ROOT + "birthdayDate";

    public final static String SETTINGS_GENERAL_HEIGHT = SETTINGS_GENERAL_ROOT + "height";
    public final static String SETTINGS_GENERAL_WEIGHT = SETTINGS_GENERAL_ROOT + "weight";

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
                SETTINGS_GENERAL_WEIGHT,
                50, 500, 5,
                value -> getString(R.string.settings_general_weight_formatter, value),
                getString(R.string.settings_general_weight_dialog_title));

        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new DatePickerSetting(R.string.settings_general_birthday, R.string.settings_error_date, SETTINGS_GENERAL_BIRTHDAY),
                heightPicker,
                weightPicker,
                new NavigationSetting(R.string.settings_general_physicalactivity_title, R.id.action_generalSettingsFragment_to_general_PhysicalActivitySettingsFragment),
                new NavigationSetting(R.string.settings_general_gender_title, R.id.action_generalSettingsFragment_to_general_SexFragment),
                new NavigationSetting(R.string.settings_general_residence_title, R.id.action_generalSettingsFragment_to_general_ResidenceFragment)
        ).build();
    }

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        final int defaultFemaleHeight = 64; // https://ourworldindata.org/human-height
        final int defaultMaleHeight = 70;
        final int defaultAmbiguousHeight = 67;

        long birthday = store.readLong(SETTINGS_GENERAL_BIRTHDAY, new Date().getTime());
        int height, weight; // Height in feet, weight in lbs, age in years
        long age;

        if (store.containsKey(SETTINGS_GENERAL_HEIGHT))
            height = store.readInt(SETTINGS_GENERAL_HEIGHT, defaultAmbiguousHeight);
        else {
            switch (store.readString(General_SexFragment.SETTINGS_GENERAL_GENDER, null)) {
                case General_SexFragment.GENDER_MALE:
                    height = defaultMaleHeight;
                    break;
                case General_SexFragment.GENDER_FEMALE:
                    height = defaultFemaleHeight;
                    break;
                default:
                    height = defaultAmbiguousHeight;
                    break;
            }
        }


        final int defaultWeight = 140; // https://bmcpublichealth.biomedcentral.com/articles/10.1186/1471-2458-12-439
        weight = store.readInt(SETTINGS_GENERAL_WEIGHT, defaultWeight);

        final long millisPerYear = (long) 3.154e10;
        age = (new Date().getTime() - birthday) / millisPerYear;

        int bmiSeverity, ageSeverity;

        // Age calculations for severity
        // https://www.worldometers.info/coronavirus/coronavirus-age-sex-demographics/
        if (age >= 75) ageSeverity = ISusceptibilityProvider.SEVERE;
        else if (age >= 45) ageSeverity = ISusceptibilityProvider.MODERATE;
        else ageSeverity = ISusceptibilityProvider.MILD;
        if (age >= 45) ailments.add(context.getString(R.string.settings_general_age));

        // BMI calculation
        // https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
        double bmi = 703 * weight / Math.pow(height, 2);
        if (bmi >= 40) {
            bmiSeverity = ISusceptibilityProvider.SEVERE;
            ailments.add(context.getString(R.string.settings_general_bmi));
        }
        else bmiSeverity = ISusceptibilityProvider.MILD;

        return ageSeverity | bmiSeverity;
    }
}
