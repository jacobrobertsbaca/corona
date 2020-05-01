package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.susceptibility.ISusceptibilityProvider;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.io.SettingsStore;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.SelfishCheckmarkSetting;

import java.util.ArrayList;
import java.util.Set;

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

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        String[] diseaseKeys = {
            IMMUNE_CANCERTREATMENT,
            IMMUNE_TRANSPLANTATION,
            IMMUNE_HIV,
            IMMUNE_CORTICOSTEROIDS,
            IMMUNE_UNLISTED = ROOT,
        };

        if (store.readAnyBool(diseaseKeys)) {
            ailments.add(context.getString(R.string.settings_medicalhistory_immunesystem_title));
            return ISusceptibilityProvider.MODERATE;
        }
        return ISusceptibilityProvider.MILD;
    }
}
