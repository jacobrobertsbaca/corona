package com.spreadtracker.ui.settings.value;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.R;

public class CheckmarkSetting extends ValueSetting<Boolean> {
    public CheckmarkSetting(@StringRes int titleRes, @NonNull String key, boolean defaultValue) {
        super(titleRes, key, defaultValue);
    }

    public CheckmarkSetting(@StringRes int titleRes, @NonNull String key) {
        this(titleRes, key, false);
    }

    public CheckmarkSetting(@StringRes int titleRes, ValueSerializer<Boolean> serializer) {
        super(titleRes, serializer);
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);
        iconView.setImageResource(R.drawable.ic_checkmark);
        iconView.setVisibility(View.GONE);

        // Set an on click listener for this view
        // so that when we click the boolean setting, we change state
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingClicked();
            }
        });

        return root;
    }

    protected void onSettingClicked () {
        setValue(!getValue());
    }

    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
        iconView.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
