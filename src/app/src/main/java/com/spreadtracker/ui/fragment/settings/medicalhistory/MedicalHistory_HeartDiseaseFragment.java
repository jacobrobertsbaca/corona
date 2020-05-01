package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.SelfishCheckmarkSetting;

public class MedicalHistory_HeartDiseaseFragment extends SettingsFragment {
    private static final String ROOT = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT +  "heart.";
    public static final String HEART_HEARTFAILURE = ROOT + "heartfailure";
    public static final String HEART_CORONARYARTERYDISEASE = ROOT + "coronaryarterydisease";
    public static final String HEART_CONGENITALHEARTDISEASE = ROOT + "congenitalheartdisease";
    public static final String HEART_CARDIOMYOPATHY = ROOT + "cardiomyopathies";
    public static final String HEART_PULMONARYHYPERTENSION = ROOT + "pulmonaryhypertension";
    public static final String HEART_UNLISTED = ROOT + "unlisted";


    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_heart_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new CheckmarkSetting(R.string.settings_medicalhistory_heart_heartfailure, HEART_HEARTFAILURE),
                new CheckmarkSetting(R.string.settings_medicalhistory_heart_coronaryarterydisease, HEART_CORONARYARTERYDISEASE),
                new CheckmarkSetting(R.string.settings_medicalhistory_heart_congenitalheartdisease, HEART_CONGENITALHEARTDISEASE),
                new CheckmarkSetting(R.string.settings_medicalhistory_heart_cardiomyopathy, HEART_CARDIOMYOPATHY),
                new CheckmarkSetting(R.string.settings_medicalhistory_heart_pulmonaryhypertension, HEART_PULMONARYHYPERTENSION),
                new SelfishCheckmarkSetting(R.string.settings_medicalhistory_unlisted, HEART_UNLISTED)
        ).build();
    }
}
