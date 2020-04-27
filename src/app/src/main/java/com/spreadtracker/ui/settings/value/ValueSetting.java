package com.spreadtracker.ui.settings.value;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.settings.IconSetting;
import com.spreadtracker.ui.settings.io.PrefSaver;
import com.spreadtracker.ui.settings.io.SettingsStore;

import java.util.Objects;

public abstract class ValueSetting<T> extends IconSetting {

    public interface ValueSerializer<T> {
        T readValue ();
        void writeValue (T value);
    }

    private T mValue;
    private @StringRes int mTitleResId;
    private ValueSerializer<T> mSerializer;

    public ValueSetting (@StringRes int titleRes, @NonNull String key, T defaultValue) {
        mTitleResId = titleRes;
        mSerializer = new PrefSaver<>(key, defaultValue);
    }

    public ValueSetting (@StringRes int titleRes, @NonNull ValueSerializer<T> serializer) {
        mSerializer = serializer;
    }

    protected void writeValue (T value) {
        mSerializer.writeValue(value);
    }

    protected T readValue () {
        return mSerializer.readValue();
    }

    public T getValue () {
        return mValue;
    }

    // Set the value of this setting and update any relevant UI
    public void setValue (T value) {
        mValue = value;
        notifyDirty(!Objects.equals(getValue(), readValue()));
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);
        if (titleView != null && mTitleResId != 0) titleView.setText(mTitleResId);
        return root;
    }

    @Override
    public void restoreState() {
        setValue(readValue());
    }

    @Override
    public void saveState() {
        writeValue(getValue());
        notifyDirty(false); // Once we have saved our state, we are by definition no longer dirty
    }
}
