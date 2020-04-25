package com.spreadtracker.ui.settings.value;

import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.spreadtracker.ui.settings.SettingsNode;
import com.spreadtracker.ui.settings.SettingsStore;

public class RadioSettings extends SettingsNode {

    private String mCurrentlySelected;

    private String mStorageKey;
    private RadioSetting[] mRadioSettings;

    public RadioSettings (@NonNull String storageKey, RadioSetting... settings) {
        super(settings);
        mStorageKey = storageKey;
        mRadioSettings = settings;
        for (RadioSetting setting : settings) {
            setting.setParent(this);
        }
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setRootView(layout);

        for (RadioSetting setting : mRadioSettings) {
            View v = setting.inflateLayout(inflater);
            layout.addView(v);
            setting.setActive(false);
        }

        return layout;
    }

    public void onChildChanged (RadioSetting setting) {
        for (RadioSetting s : mRadioSettings) {
            if (s != setting)
                s.setActive(false);
        }
        mCurrentlySelected = setting.getName();
        notifyDirty(mCurrentlySelected != null &&
                !mCurrentlySelected.equals(SettingsStore.getInstance(getContext()).readString(mStorageKey, null)));
    }

    @Override
    public boolean canSave() {
        return true; // We can always save a radio settings group
    }

    @Override
    public void saveState() {
        for (RadioSetting setting : mRadioSettings) {
            if (setting.isActive()) {
                SettingsStore.getInstance(getContext()).writeValue(mStorageKey, setting.getName());
                break;
            }
        }
    }

    @Override
    public void restoreState() {
        String activeName = SettingsStore.getInstance(getContext()).readString(mStorageKey, null);
        if (activeName == null || activeName.isEmpty()) return;
        for (RadioSetting setting : mRadioSettings) {
            if (setting.getName().equals(activeName)) {
                setting.setActive(true);
            } else setting.setActive(false);
        }
    }
}
