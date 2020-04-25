package com.spreadtracker.ui.settings.value;

import androidx.annotation.NonNull;

public class IntSetting extends ValueSetting<Integer> {
    public IntSetting(@NonNull String storageKey, int titleRes, Integer defaultValue) {
        super(storageKey, titleRes, defaultValue);
    }


}
