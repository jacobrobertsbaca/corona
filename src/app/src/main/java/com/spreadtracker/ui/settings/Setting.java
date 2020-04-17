package com.spreadtracker.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class Setting {
    private Context mCtx;
    private View mSettingView;
    private SettingsBlock mSettingsBlock;

    public Setting(@NonNull SettingsBlock block, @NonNull Context ctx) {
        mSettingsBlock = block;
        mCtx = ctx;
    }

    public final View getView () {
        if (mSettingView != null) return mSettingView;
        mSettingView = createView(LayoutInflater.from(mCtx));
        return mSettingView;
    }
    protected abstract View createView (LayoutInflater inflater);
    @Nullable public abstract SettingsError saveState ();

    protected final void setDirty () {
        mSettingsBlock.setDirty();
    }
}
