package com.spreadtracker.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.R;

import java.util.ArrayList;

/**
 * A setting that stores no state, but has a title, an icon, and some text
 * associated with it.
 */
public class IconSetting extends SettingsNode {

    private View mTopDivider, mBottomDivider;
    protected ImageView iconView;
    protected TextView titleView, textView;

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.view_setting_icon, getParentView(), false);
        setRootView(root);

        mTopDivider = root.findViewById(R.id.view_setting_icon_divider_top);
        mBottomDivider = root.findViewById(R.id.view_setting_icon_divider_bottom);

        // Disable dividers selectively
        ArrayList<SettingsNode> siblings = getParent().getChildren();
        int myIndex = siblings.indexOf(this);
        if (myIndex > 0) {
            SettingsNode lastSibling = siblings.get(myIndex - 1);
            if (lastSibling instanceof IconSetting) {
                IconSetting lastIcon = (IconSetting) lastSibling;
                lastIcon.mBottomDivider.setVisibility(View.GONE);
            }
        }

        iconView = root.findViewById(R.id.view_setting_icon_image);
        titleView = root.findViewById(R.id.view_setting_icon_title);
        textView = root.findViewById(R.id.view_setting_icon_text);

        titleView.setText("");
        textView.setText("");

        return root;
    }

    protected void setIcon (@DrawableRes int resId) {
        iconView.setImageResource(resId);
    }

    protected void setTitleText (@StringRes int resId) {
        titleView.setText(resId);
    }

    protected void setTitleText (String text) {
        titleView.setText(text);
    }

    protected void setText (@StringRes int resId) {
        textView.setText(resId);
    }

    protected void setText (String text) {
        textView.setText(text);
    }
}
