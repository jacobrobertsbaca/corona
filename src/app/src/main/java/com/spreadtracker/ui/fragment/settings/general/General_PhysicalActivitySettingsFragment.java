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

public class General_PhysicalActivitySettingsFragment extends SettingsFragment {
    public final static String SETTINGS_GENERAL_PHYSICALACTIVTY = GeneralSettingsFragment.SETTINGS_GENERAL_ROOT + "physicalactivity";

    public static final String ACTIVITY_ACTIVE = "active";
    public static final String ACTIVITY_MODERATE = "moderate";
    public static final String ACTIVITY_INACTIVE = "inactive";

    @Override
    protected int getTitle() {
        return R.string.settings_general_physicalactivity_title;
    }

    @Override
    protected void createSettingsHierarchy(ViewGroup container) {
        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, container,
                new RadioSettings(SETTINGS_GENERAL_PHYSICALACTIVTY,
                        new RadioSetting(ACTIVITY_ACTIVE, getString(R.string.settings_general_physicalactivity_active)),
                        new RadioSetting(ACTIVITY_MODERATE, getString(R.string.settings_general_physicalactivity_moderate)),
                        new RadioSetting(ACTIVITY_INACTIVE, getString(R.string.settings_general_physicalactivity_inactive))))
                .build();
    }

    public static int getSeverity(@NonNull Context context,
                                  @NonNull SettingsStore store,
                                  @NonNull ArrayList<String> ailments,
                                  @NonNull Set<String> advice) {
        switch (store.readString(SETTINGS_GENERAL_PHYSICALACTIVTY, null)) {
            case ACTIVITY_INACTIVE:
                ailments.add(context.getString(R.string.settings_general_physicalactivity_title));
                return ISusceptibilityProvider.MODERATE;
            default:
                return ISusceptibilityProvider.MILD;
        }
    }
}
