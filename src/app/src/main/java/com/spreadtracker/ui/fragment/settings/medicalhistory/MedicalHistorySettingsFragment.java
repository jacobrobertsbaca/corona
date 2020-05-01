package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;

public class MedicalHistorySettingsFragment extends SettingsFragment {
    public static final String SETTINGS_MEDICALHISTORY_ROOT = "settings.medicalhistory";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new NavigationSetting(R.string.settings_medicalhistory_heart_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_HeartDiseaseFragment),
                new NavigationSetting(R.string.settings_medicalhistory_asthma_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_AsthmaFragment),
                new NavigationSetting(R.string.settings_medicalhistory_diabetes_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_DiabetesFragment),
                new NavigationSetting(R.string.settings_medicalhistory_kidneydisease_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_KidneyDisease),
                new NavigationSetting(R.string.settings_medicalhistory_lungdisease_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_LungDiseaseFragment),
                new NavigationSetting(R.string.settings_medicalhistory_immunesystem_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_ImmuneSystemFragment),
                new NavigationSetting(R.string.settings_medicalhistory_liverdisease_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_LiverDiseaseFragment)
        ).build();
    }
}
