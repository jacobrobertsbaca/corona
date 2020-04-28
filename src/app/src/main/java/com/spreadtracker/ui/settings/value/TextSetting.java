package com.spreadtracker.ui.settings.value;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.spreadtracker.R;

public class TextSetting extends ValueSetting<String> {
    public TextSetting(int titleRes, @NonNull String key, String defaultValue) {
        super(titleRes, key, defaultValue);
    }

    public TextSetting (int titleRes, @NonNull String key) {
        this(titleRes, key, null);
    }

    public TextSetting(int titleRes, @NonNull ValueSerializer<String> serializer) {
        super(titleRes, serializer);
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);

        textView.setVisibility(View.GONE);
        iconView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);

        editText.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { setValue(s.toString()); }
        });

        return root;
    }
}
