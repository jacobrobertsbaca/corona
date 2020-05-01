package com.spreadtracker.ui.fragment.settings.medicalhistory;

import android.view.ViewGroup;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.value.CheckmarkSetting;
import com.spreadtracker.ui.settings.value.SelfishCheckmarkSetting;

public class MedicalHistory_LungDiseaseFragment extends SettingsFragment {

    private static String ROOT = MedicalHistorySettingsFragment.SETTINGS_MEDICALHISTORY_ROOT + "lungdisease.";
    public static String LUNG_COPD = ROOT + "copd";
    public static String LUNG_EMPHYSEMA = ROOT + "emphysema";
    public static String LUNG_CHRONICBRONCHITIS = ROOT + "chronicbronchitis";
    public static String LUNG_IDIOPATHICPULMONARYFIBROOSIS = ROOT + "idiopathicpulmonaryfibrosis";
    public static String LUNG_CYSTICFIBROSIS = ROOT + "cysticfibrosis";
    public static String LUNG_UNLISTED = ROOT + "unlisted";

    @Override
    protected int getTitle() {
        return R.string.settings_medicalhistory_lungdisease_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new CheckmarkSetting(R.string.settings_medicalhistory_lungdisease_copd, LUNG_COPD),
                new CheckmarkSetting(R.string.settings_medicalhistory_lungdisease_emphysema, LUNG_EMPHYSEMA),
                new CheckmarkSetting(R.string.settings_medicalhistory_lungdisease_chronicbronchitis, LUNG_CHRONICBRONCHITIS),
                new CheckmarkSetting(R.string.settings_medicalhistory_lungdisease_idiopathicpulmonaryfibrosis, LUNG_IDIOPATHICPULMONARYFIBROOSIS),
                new CheckmarkSetting(R.string.settings_medicalhistory_lungdisease_cysticfibrosis, LUNG_CYSTICFIBROSIS),
                new SelfishCheckmarkSetting(R.string.settings_medicalhistory_unlisted, LUNG_UNLISTED))
                .build();
    }
}
