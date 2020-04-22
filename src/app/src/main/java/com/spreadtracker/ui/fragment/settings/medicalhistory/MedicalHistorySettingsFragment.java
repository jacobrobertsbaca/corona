package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;

public class MedicalHistorySettingsFragment extends SettingsFragment {
    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new NavigationSetting(R.string.settings_medicalhistory_diseases_title, R.id.action_medicalHistorySettingsFragment_to_medicalHistory_DiseasesSettingsFragment)
        ).build();
    }
}
