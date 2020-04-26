package com.spreadtracker.ui.settings;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.NavigationFragment;

/**
 * Represents a stateless setting that, when clicked,
 * navigates to a new destination in the hierarchy.
 * Note that this setting must be placed undo a {@link NavigationSettingsPage} hierarchy to function.
 */
public class NavigationSetting extends IconSetting {

    private @StringRes int mTitleResId;
    private @IdRes int mDestinationId;
    private @DrawableRes int mIconDrawable = R.drawable.ic_next;

    public NavigationSetting(@StringRes int titleResId, @IdRes int destinationId) {
        mTitleResId = titleResId;
        mDestinationId = destinationId;
    }

    public NavigationSetting (@StringRes int titleResId, @IdRes int destinationId, @DrawableRes int iconResId) {
        this(titleResId, destinationId);
        mIconDrawable = iconResId;
    }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);

        // Set correct title and icon
        titleView.setText(mTitleResId);
        textView.setText("");
        iconView.setImageResource(mIconDrawable);

        // Set click listener to navigate to the destination page
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsNode node = getRootNode();
                if (node instanceof NavigationSettingsPage) {
                    NavigationSettingsPage navSettings = (NavigationSettingsPage) node;
                    navSettings.getNavController().navigate(mDestinationId);
                }
            }
        });

        return root;
    }
}
