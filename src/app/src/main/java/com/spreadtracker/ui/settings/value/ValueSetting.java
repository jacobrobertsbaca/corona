package com.spreadtracker.ui.settings.value;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.ui.settings.IconSetting;
import com.spreadtracker.ui.settings.io.PrefSaver;

import java.util.Objects;

/**
 * Represents a setting that stores, retrieves, and writes a value to a specified location
 * and represents that value in its UI.
 * @param <T> The type of the value that is read and written from some location. This need not
 *           be the same as the type of the value that is shown to the user through the user interface.
 *           For instance, a date picker setting may have a {@link Long} as its type parameter to represent
 *           a given time in memory, but in reality show a String containing the formatted date in the user interface.
 */
public abstract class ValueSetting<T> extends IconSetting {

    /**
     * A class used to customize the serialization, i.e. the reading and
     * writing of values to a specified location.
     */
    public interface ValueSerializer<T> {
        T readValue ();                 // Reads a value from a location it was previously stored to
        void writeValue (T value);      // Writes a value to a location to a location
    }

    /**
     * A class used to customize the validation of the input values of this setting.
     */
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

    /**
     * Writes a value to the location specified by the supplied {@link ValueSerializer}
     * @param value The value to write
     */
    protected final void writeValue (T value) {
        mSerializer.writeValue(value);
    }

    /**
     * Reads a value from the location specified by the supplied {@link ValueSerializer}
     * @return The retrieved value
     */
    protected final T readValue () {
        return mSerializer.readValue();
    }

    /**
     * Gets the current value that this {@link ValueSetting} contains.
     * This need not be the same as the value returned by {@link ValueSetting#readValue()}
     * @return The current value
     */
    public T getValue () {
        return mValue;
    }

    /**
     * Sets the current value that this {@link ValueSetting} contains, and updates any relevant
     * UI to indicate that a new value has been set.
     * This is not the same as {@link ValueSetting#writeValue(Object)}
     * @param value A new value for this setting to contain
     */
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

    /**
     * Returns {@code true} if this setting has a value,
     * and false if otherwise.
     *
     * <p>This method should return false if the value given by
     * {@link ValueSetting#getValue()} represents a null or unassigned value,
     * and false if it does not.</p>
     */
    public boolean hasValue () { return getValue() != null; }

    public String getTitle () {return getContext().getString(mTitleResId);}
}
