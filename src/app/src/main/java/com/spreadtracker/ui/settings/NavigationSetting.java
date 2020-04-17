package com.spreadtracker.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A setting that navigates to a particular page.
 */
public class NavigationSetting extends Setting {
    public NavigationSetting(@NonNull SettingsBlock block, @NonNull Context ctx) {
        super(block, ctx);
    }

    @Override
    protected View createView(LayoutInflater inflater) {
        return null;
    }

    @Nullable
    @Override
    public SettingsError saveState() {
        return null;
    }
}
