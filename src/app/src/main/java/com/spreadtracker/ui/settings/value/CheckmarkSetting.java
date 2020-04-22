package com.spreadtracker.ui.settings.value;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.spreadtracker.R;

public class CheckmarkSetting extends ValueSetting<Boolean> {

    private boolean mCurrent;

    public CheckmarkSetting(@NonNull String storageKey, int titleRes, Boolean defaultValue) {
        super(storageKey, titleRes, defaultValue);
    }

    public CheckmarkSetting(@NonNull String storageKey, int titleRes) {
        super(storageKey, titleRes, false);
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);
        iconView.setImageResource(R.drawable.ic_checkmark);
        iconView.setVisibility(View.GONE);

        // Set an on click listener for this view
        // so that when we click the boolean setting, we change
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(!mCurrent);
                notifyDirty(mCurrent != readValue());
            }
        });

        return root;
    }

    @Override
    public boolean canSave() {
        // Should always be able to save a boolean setting
        // No concept of "invalid input" here
        return true;
    }

    @Override
    public void saveState() {
        writeValue(mCurrent);
    }

    @Override
    public void restoreState() {
        setState(readValue());
    }

    private void setState (boolean enabled) {
        iconView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        mCurrent = enabled;
    }
}
