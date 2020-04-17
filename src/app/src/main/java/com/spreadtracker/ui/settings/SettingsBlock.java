package com.spreadtracker.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;
import com.spreadtracker.ui.util.ToastError;

import java.util.ArrayList;

public class SettingsBlock {
    private NavigationFragment mFragment;
    private Context mCtx;
    private boolean mDirty;

    protected ViewGroup mContainer;
    protected LinearLayout mRoot;

    protected ArrayList<Setting> mSettings;

    public SettingsBlock (@NonNull NavigationFragment fragment, @NonNull ViewGroup container) {
        mFragment = fragment;
        mCtx = container.getContext();

        NavigationBuilder currentNavigation = mFragment.getNavigation();
        currentNavigation.setRightButtonDrawable(R.drawable.ic_checkmark);
        currentNavigation.setRightButtonVisibility(View.GONE);
        currentNavigation.setRightButtonCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
        mFragment.updateNavigation(currentNavigation);

        // Set up UI
        mContainer = container;
        mRoot = new LinearLayout(mCtx);
        mRoot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRoot.setOrientation(LinearLayout.VERTICAL);


    }

    public void setDirty() {
        NavigationBuilder currentNavigation = mFragment.getNavigation();
        currentNavigation.setRightButtonVisibility(View.VISIBLE);
    }

    private void saveSettings() {
        for (Setting s : mSettings) {
            SettingsError err = s.saveState();
            if (err != null) {
                ToastError.error(mCtx, err.getErrorMessage(), Toast.LENGTH_LONG);
                break;
            }
        }

        NavigationBuilder currentNavigation = mFragment.getNavigation();
        currentNavigation.setRightButtonVisibility(View.GONE);
        mFragment.updateNavigation(currentNavigation);
    }

    public void registerSetting (Setting setting) {
        View settingsView = setting.createView(LayoutInflater.from(mCtx));
        mRoot.addView(settingsView);
        mSettings.add(setting);
    }

    public boolean unregisterSetting (Setting setting) {
        for (int i = 0; i < mSettings.size(); i++) {
            if (mSettings.get(i).equals(setting)) {
                mRoot.removeViewAt(i);
                mSettings.remove(i);
                return true;
            }
        }
        return false;
    }
}
