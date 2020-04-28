package com.spreadtracker.ui.settings.io;

import androidx.annotation.NonNull;

import com.spreadtracker.ui.settings.value.ValueSetting;

public class PrefSaver<T> implements ValueSetting.ValueSerializer<T> {
    private String mKey;
    private T mDefaultValue;

    public PrefSaver(@NonNull String key, T defaultValue) {
        mKey = key;
        mDefaultValue = defaultValue;
    }

    @Override
    public T readValue() {
        return SettingsStore.getInstance().readValue(mKey, mDefaultValue);
    }

    @Override
    public void writeValue(T value) {
        SettingsStore.getInstance().writeValue(mKey, value);
    }
}
