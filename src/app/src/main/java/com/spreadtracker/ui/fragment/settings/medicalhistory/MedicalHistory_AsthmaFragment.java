package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

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
}
