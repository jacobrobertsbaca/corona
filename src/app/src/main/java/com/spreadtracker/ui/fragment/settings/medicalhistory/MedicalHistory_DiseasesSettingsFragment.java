package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;

public class MedicalHistory_DiseasesSettingsFragment extends SettingsFragment {

    public static final String SETTINGS_MEDICALHISTORY_DISEASES = "settings.medicalhistory.diseases.";
    public static final String SETTINGS_MEDICALHISTORY_DISEASES_DIABETES = SETTINGS_MEDICALHISTORY_DISEASES + "diabetes";
    public static final String SETTINGS_MEDICALHISTORY_DISEASES_ASTHMA = SETTINGS_MEDICALHISTORY_DISEASES + "asthma";
    public static final String SETTINGS_MEDICALHISTORY_DISEASES_HEARTDISEASE = SETTINGS_MEDICALHISTORY_DISEASES + "heartdisease";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_diseases_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new CheckmarkSetting(SETTINGS_MEDICALHISTORY_DISEASES_DIABETES, R.string.settings_medicalhistory_diseases_diabetes),
                new CheckmarkSetting(SETTINGS_MEDICALHISTORY_DISEASES_ASTHMA, R.string.settings_medicalhistory_diseases_asthma),
                new CheckmarkSetting(SETTINGS_MEDICALHISTORY_DISEASES_HEARTDISEASE, R.string.settings_medicalhistory_diseases_heartdisease)
        ).build();
    }
}