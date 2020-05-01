package com.spreadtracker.ui.fragment.settings.general;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.susceptibility.ISusceptibilityProvider;
import com.spreadtracker.ui.fragment.settings.SettingsFragment;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.io.SettingsStore;
import com.spreadtracker.ui.settings.value.RadioSetting;
import com.spreadtracker.ui.settings.value.RadioSettings;

import java.util.ArrayList;
import java.util.Set;

public class General_SexFragment extends SettingsFragment {

    public final static String SETTINGS_GENERAL_GENDER = GeneralSettingsFragment.SETTINGS_GENERAL_ROOT + "gender";
    public final static String GENDER_AMBIGUOUS = "ambiguous";
    public final static String GENDER_MALE = "male";
    public final static String GENDER_FEMALE = "female";

    @Override
    protected int getTitle() {
        return R.string.settings_general_gender_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(SETTINGS_GENERAL_GENDER,
                        new RadioSetting(GENDER_MALE, getString(R.string.settings_general_gender_male)),
                        new RadioSetting(GENDER_FEMALE, getString(R.string.settings_general_gender_female)),
                        new RadioSetting(GENDER_AMBIGUOUS, getString(R.string.settings_general_gender_ambiguous))))
                .build();
    }

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice) {
        switch (store.readString(SETTINGS_GENERAL_GENDER, "")) {
            case GENDER_MALE:
                ailments.add(context.getString(R.string.settings_general_gender_title));
                return ISusceptibilityProvider.MODERATE;
            default: return ISusceptibilityProvider.MILD;
        }
    }
}
