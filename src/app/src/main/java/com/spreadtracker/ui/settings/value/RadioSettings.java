package com.spreadtracker.ui.settings.value;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class RadioSettings extends ValueSetting<String> {

    private RadioSetting[] mRadioSettings;

    public RadioSettings (@NonNull String key, RadioSetting... settings) {
        super(0, key, null); // No checkmark will be selected by default
        setupChildren(settings);
    }

    public RadioSettings(@NonNull ValueSerializer<String> serializer, RadioSetting... settings) {
        super(0, serializer);
        setupChildren(settings);
    }

    private void setupChildren (RadioSetting... settings) {
        mRadioSettings = settings;

        // Add children to this parent setting node
        for (RadioSetting setting : settings)
            addChild(setting);
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
        }

        return layout;
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);

        // Find the setting whose name is value, and set it to be active
        // For all other settings, set inactive
        for (RadioSetting setting : mRadioSettings)
            setting.setActive(value != null && value.equals(setting.getName()));
    }

    @Override
    public boolean hasValue() {
        return getValue() != null && !getValue().isEmpty();
    }
}
