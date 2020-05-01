package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.SelfishCheckmarkSetting;

public class MedicalHistory_LiverDiseaseFragment extends SettingsFragment {

    private static String ROOT = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT + "liverdisease.";
    public static String LIVER_CIRRHOSIS = ROOT + "cirrhosis";
    public static String LIVER_UNLISTED = ROOT + "unlisted";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_liverdisease_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new CheckmarkSetting(R.string.settings_medicalhistory_liverdisease_cirrhosis, LIVER_CIRRHOSIS),
                new SelfishCheckmarkSetting(R.string.settings_medicalhistory_unlisted, LIVER_UNLISTED))
                .build();
    }
}
