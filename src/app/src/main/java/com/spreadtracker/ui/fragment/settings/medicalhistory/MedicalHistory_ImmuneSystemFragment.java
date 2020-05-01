package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.SelfishCheckmarkSetting;

public class MedicalHistory_ImmuneSystemFragment extends SettingsFragment {
    private static String ROOT = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT + "immunesystem.";
    public static String IMMUNE_CANCERTREATMENT = ROOT + "cancertreatment";
    public static String IMMUNE_TRANSPLANTATION = ROOT + "transplantation";
    public static String IMMUNE_HIV = ROOT + "hiv";
    public static String IMMUNE_CORTICOSTEROIDS = ROOT + "corticosteroids";
    public static String IMMUNE_UNLISTED = ROOT + "unlisted";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_immunesystem_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new CheckmarkSetting(R.string.settings_medicalhistory_immunesystem_cancertreatment, IMMUNE_CANCERTREATMENT),
                new CheckmarkSetting(R.string.settings_medicalhistory_immunesystem_transplantation, IMMUNE_TRANSPLANTATION),
                new CheckmarkSetting(R.string.settings_medicalhistory_immunesystem_hiv, IMMUNE_HIV),
                new CheckmarkSetting(R.string.settings_medicalhistory_immunesystem_corticosteroids, IMMUNE_CORTICOSTEROIDS),
                new SelfishCheckmarkSetting(R.string.settings_medicalhistory_unlisted, IMMUNE_UNLISTED))
                .build();
    }
}
