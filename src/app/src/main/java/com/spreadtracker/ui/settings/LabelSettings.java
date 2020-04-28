package com.spreadtracker.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.spreadtracker.App;
import com.spreadtracker.R;

public class LabelSettings extends SettingsNode {

    private boolean mShowDividers = false;
    private String mTitleText;

    public LabelSettings(@StringRes int titleResId, boolean showDividers, SettingsNode... children) {
        super(children);
        setup(App.getContext().getString(titleResId), showDividers);
    }

    public LabelSettings(@StringRes int titleResId, SettingsNode... children) {
        this(titleResId, false, children);
    }

    public LabelSettings(String title, boolean showDividers, SettingsNode... children) {
        super(children);
        setup(title, showDividers);
    }

    public LabelSettings(String title, SettingsNode... children) {
        this(title, false, children);
    }

    private void setup (String title, boolean showDividers) {
        mTitleText = title;
        mShowDividers = showDividers;
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.view_setting_labelsettings, getParentView(), false);

        LinearLayout contentHolder = root.findViewById(R.id.view_setting_labelsettings_contentholder);
        TextView titleView = root.findViewById(R.id.view_setting_labelsettings_title);
        ImageView dividerTop = root.findViewById(R.id.view_setting_labelsettings_divider_top);
        ImageView dividerBottom = root.findViewById(R.id.view_setting_labelsettings_divider_bottom);

        dividerTop.setVisibility(mShowDividers ? View.VISIBLE : View.GONE);
        dividerBottom.setVisibility(mShowDividers ? View.VISIBLE : View.GONE);
        titleView.setText(mTitleText);

        for (SettingsNode child : getChildren()) {
            View nodeView = child.inflateLayout(inflater);
            contentHolder.addView(nodeView);
        }

        return root;
    }
}
