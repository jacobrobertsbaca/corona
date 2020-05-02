package com.spreadtracker.ui.settings.value;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.settings.SettingsNode;

import java.util.ArrayList;

/**
 * An implementation of {@link CheckmarkSetting} that, when clicked,
 * disables all other sibling CheckmarkSettings
 */
public class SelfishCheckmarkSetting extends CheckmarkSetting {
    public SelfishCheckmarkSetting(@StringRes int titleRes, @NonNull String key, boolean defaultValue) {
        super(titleRes, key, defaultValue);
    }

    public SelfishCheckmarkSetting(@StringRes int titleRes, @NonNull String key) {
        this(titleRes, key, false);
    }

    public SelfishCheckmarkSetting(@StringRes int titleRes, ValueSerializer<Boolean> serializer) {
        super(titleRes, serializer);
    }

    @Override
    protected void onSettingClicked() {
        super.onSettingClicked();
        if (getValue()) {
            // When this setting is clicked,
            // disable all sibling Checkmark Settings
            ArrayList<SettingsNode> siblings = getParent().getChildren();
            for (SettingsNode sibling : siblings) {
                if (sibling != this && sibling instanceof CheckmarkSetting) {
                    ((CheckmarkSetting) sibling).setValue(false);
                }
            }
        }
    }
}
