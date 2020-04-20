package com.spreadtracker.ui.settings;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spreadtracker.R;

import java.util.ArrayList;

public abstract class DividerSetting extends SettingsNode {
    private View mTopDivider, mBottomDivider;

    protected final void setDividers (View root) {
        mTopDivider = root.findViewById(R.id.view_settings_divider_top);
        mBottomDivider = root.findViewById(R.id.view_settings_divider_bottom);

        ArrayList<SettingsNode> siblings = getParent().getChildren();
        int index = siblings.indexOf(this);
        if (index < siblings.size() - 1 && siblings.get(index + 1) instanceof DividerSetting)
            mBottomDivider.setVisibility(View.GONE);
    }
}
