package com.spreadtracker.ui.settings;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Represents an object that can produce a layout from xml.
 */
public interface ILayoutFactory {
    /**
     * Call to inflate a layout from xml.
     * @param inflater The inflater to be used to inflate this object's layout.
     * @return A view representing the inflated xml layout.
     */
    @NonNull View inflateLayout (@NonNull LayoutInflater inflater);
}
