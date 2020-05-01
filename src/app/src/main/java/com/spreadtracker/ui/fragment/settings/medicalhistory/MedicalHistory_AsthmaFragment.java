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

public class MedicalHistory_AsthmaFragment extends SettingsFragment {

    public final static String ASTHMA = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT + "asthma";
    public final static String ASTHMA_MILD = "mild";
    public final static String ASTHMA_MODERATE = "moderate";
    public final static String ASTHMA_SEVERE = "severe";


    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_asthma_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage page = new NavigationSettingsPage(this, container,
                new RadioSettings(ASTHMA, true,
                    new RadioSetting(ASTHMA_MILD, R.string.settings_medicalhistory_asthma_mild),
                    new RadioSetting(ASTHMA_MODERATE, R.string.settings_medicalhistory_asthma_moderate),
                    new RadioSetting(ASTHMA_SEVERE, R.string.settings_medicalhistory_asthma_severe)))
                .build();
    }

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        switch (store.readString(ASTHMA, null)) {
            case ASTHMA_MILD:
            case ASTHMA_MODERATE:
                ailments.add(context.getString(R.string.settings_medicalhistory_asthma_title));
                return ISusceptibilityProvider.MODERATE;
            case ASTHMA_SEVERE:
                ailments.add(context.getString(R.string.settings_medicalhistory_asthma_title));
                return ISusceptibilityProvider.SEVERE;
            default: return ISusceptibilityProvider.MILD;
        }
    }
}
