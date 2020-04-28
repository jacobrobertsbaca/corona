package com.spreadtracker.ui.settings.value;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.spreadtracker.App;

public class TextSetting extends ValueSetting<String> {

    private final TextWatcher mWatcher = new TextWatcher() {
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override public void afterTextChanged(Editable s) {
            mUpdateUi = false;
            setValue(s.toString());
            mUpdateUi = true;
        }
    };

    private boolean mUpdateUi = true; // Not really sure how to get around this sentinel

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

        editText.addTextChangedListener(mWatcher);

        getRootView().setOnClickListener(v -> {
            if(editText.requestFocus()) {
                InputMethodManager imm = (InputMethodManager) App.getActivity(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                else editText.clearFocus();
            }
        });

        return root;
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (mUpdateUi) updateUi(value);
    }

    private void updateUi(String value) {
        editText.removeTextChangedListener(mWatcher);
        editText.setText(value);
        editText.addTextChangedListener(mWatcher);
    }
}
