package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.net.wifi.aware.PublishConfig;
import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

public class MedicalHistory_DiabetesFragment extends SettingsFragment {

    public static final String DIABETES = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT + "diabetes";
    public static final String DIABETES_TYPE1 = "type1";
    public static final String DIABETES_TYPE2 = "type2";
    public static final String DIABETES_GESTATIONAL = "gestational";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_diabetes_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(DIABETES, true,
                    new RadioSetting(DIABETES_TYPE1, R.string.settings_medicalhistory_diabetes_type1),
                    new RadioSetting(DIABETES_TYPE2, R.string.settings_medicalhistory_diabetes_type2),
                    new RadioSetting(DIABETES_GESTATIONAL, R.string.settings_medicalhistory_diabetes_gestational))
        ).build();
    }
}
