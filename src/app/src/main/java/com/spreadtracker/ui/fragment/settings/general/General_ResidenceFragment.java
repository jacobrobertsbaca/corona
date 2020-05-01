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

public class General_ResidenceFragment extends SettingsFragment {

    public final static String SETTINGS_GENERAL_RESIDENCE = GeneralSettingsFragment.SETTINGS_GENERAL_ROOT + "residence";
    public final static String RESIDENCE_HOUSE = "house";
    public final static String RESIDENCE_APARTMENT = "apartment";
    public final static String RESIDENCE_ASSISTEDLIVING = "assistedliving";

    @Override
    protected int getTitle() {
        return R.string.settings_general_residence_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(SETTINGS_GENERAL_RESIDENCE,
                        new RadioSetting(RESIDENCE_HOUSE, R.string.settings_general_residence_house),
                        new RadioSetting(RESIDENCE_APARTMENT, R.string.settings_general_residence_apartment),
                        new RadioSetting(RESIDENCE_ASSISTEDLIVING, R.string.settings_general_residence_assistedliving)))
                .build();
    }

    public static int getSeverity (@NonNull Context context,
                                   @NonNull SettingsStore store,
                                   @NonNull ArrayList<String> ailments,
                                   @NonNull Set<String> advice)  {
        switch (store.readString(SETTINGS_GENERAL_RESIDENCE, "")) {
            case RESIDENCE_APARTMENT:
            case RESIDENCE_ASSISTEDLIVING:
                ailments.add(context.getString(R.string.settings_general_residence_title));
                return ISusceptibilityProvider.MODERATE;
            default:
                return ISusceptibilityProvider.MILD;
        }
    }
}
