package com.spreadtracker.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

/**
 * The master parent node for a page that contains settings.
 * Contains a hierarchy of settings.
 */
public class SettingsPage extends SettingsNode {

    private ViewGroup mContainerView;

    public SettingsPage(@NonNull ViewGroup parentView, SettingsNode... children) {
        super(parentView.getContext(), children);
        mContainerView = parentView;
    }

    @NonNull
    public SettingsPage build() {
        if (getRootView() != null) {
            mContainerView.removeView(getRootView());
            setRootView(null);
        }
        inflateLayout(LayoutInflater.from(getContext()));

        // restore states
        restoreState();

        return this;
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        setRootView(layout);

        // build children layouts
        for (SettingsNode child : getChildren()) {
            View nodeView = child.inflateLayout(inflater);
            layout.addView(nodeView);
        }

        mContainerView.addView(layout);
        return layout;
    }
}
