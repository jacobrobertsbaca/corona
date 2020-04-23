package com.spreadtracker.ui.settings.value;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.settings.IconSetting;
import com.spreadtracker.ui.settings.SettingsStore;

public abstract class ValueSetting<T> extends IconSetting {
    private T mDefaultValue;
    private @NonNull String mStorageKey;
    private @StringRes int mTitleResId;

    public ValueSetting (@NonNull String storageKey, @StringRes int titleRes, T defaultValue) {
        mTitleResId = titleRes;
        mStorageKey = storageKey;
        mDefaultValue = defaultValue;
    }

    protected T readValue () { return SettingsStore.getInstance(getContext()).readValue(mStorageKey, mDefaultValue); }
    protected void writeValue (T value) { SettingsStore.getInstance(getContext()).writeValue(mStorageKey, value); }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);
        titleView.setText(mTitleResId);
        return root;
    }
}
