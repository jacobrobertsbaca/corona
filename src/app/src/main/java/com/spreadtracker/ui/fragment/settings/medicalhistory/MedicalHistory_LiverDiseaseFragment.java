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

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        String[] diseasesKeys = {
                LIVER_CIRRHOSIS,
                LIVER_UNLISTED
        };

        if (store.readAnyBool(diseasesKeys)) {
            ailments.add(context.getString(R.string.settings_medicalhistory_liverdisease_title));
            return ISusceptibilityProvider.MODERATE;
        }
        return ISusceptibilityProvider.MILD;
    }
}
