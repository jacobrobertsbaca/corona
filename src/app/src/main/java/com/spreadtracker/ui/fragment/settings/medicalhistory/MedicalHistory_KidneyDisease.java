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
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

import java.util.ArrayList;
import java.util.Set;

public class MedicalHistory_KidneyDisease extends SettingsFragment {

    public static final String KIDNEYDISEASE = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT + "kidneydisease";
    public static final String WITHDIALYSIS = "withdialysis";
    public static final String WITHOUTDIALYSIS = "withoutdyalysis";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_kidneydisease_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(KIDNEYDISEASE, true,
                        new RadioSetting(WITHDIALYSIS, R.string.settings_medicalhistory_kidneydisease_withdialysis),
                        new RadioSetting(WITHOUTDIALYSIS, R.string.settings_medicalhistory_kidneydisease_withoutdialysis)))
                .build();
    }

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        if (store.containsKey(KIDNEYDISEASE)) {
            ailments.add(context.getString(R.string.settings_medicalhistory_kidneydisease_title));
            return ISusceptibilityProvider.MODERATE;

        } else return ISusceptibilityProvider.MILD;
    }
}
