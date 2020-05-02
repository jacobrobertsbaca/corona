package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.content.Context;
import android.net.wifi.aware.PublishConfig;
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

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        if (store.containsKey(DIABETES)) {
            ailments.add(context.getString(R.string.settings_medicalhistory_diabetes_title));
            return ISusceptibilityProvider.MODERATE;
        }
        return ISusceptibilityProvider.MILD;
    }
}
