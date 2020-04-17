package com.spreadtracker.ui.settings;

import androidx.annotation.NonNull;

public class SettingsError {
    private String mErrorMessage;

    public SettingsError(@NonNull String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
