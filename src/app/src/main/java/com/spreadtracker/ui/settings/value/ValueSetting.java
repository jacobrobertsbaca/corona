package com.spreadtracker.ui.settings.value;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.settings.IconSetting;
import com.spreadtracker.ui.settings.io.PrefSaver;
import com.spreadtracker.ui.settings.io.SettingsStore;

import java.util.Objects;

public abstract class ValueSetting<T> extends IconSetting {

    public interface ValueSerializer<T> {
        T readValue ();                 // Reads a value from a location it was previously stored to
        void writeValue (T value);      // Writes a value to a location to a location
    }

    public interface ValueValidator<T> {
        boolean validate (T value);     // Validates a given value. True if valid, false if invalid.
    }

    private T mValue;
    private @StringRes int mTitleResId;
    private ValueSerializer<T> mSerializer;
    private ValueValidator<T> mValidator;

    public ValueSetting (@StringRes int titleRes, @NonNull String key, T defaultValue) {
        mTitleResId = titleRes;
        mSerializer = new PrefSaver<>(key, defaultValue);
    }

    public ValueSetting (@StringRes int titleRes, @NonNull ValueSerializer<T> serializer) {
        mTitleResId = titleRes;
        mSerializer = serializer;
    }

    public ValueSetting<T> setValidator (ValueValidator<T> validator) {
        mValidator = validator;
        return this;
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

    @Override
    public boolean canSave() {
        boolean canSave = super.canSave();
        if (canSave && mValidator != null) return mValidator.validate(getValue());
        return canSave;
    }
}
