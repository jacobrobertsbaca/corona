package com.spreadtracker.ui.settings.navigation;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.spreadtracker.R;
import com.spreadtracker.ui.fragment.NavigationFragment;
import com.spreadtracker.ui.settings.IconSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;
import com.spreadtracker.ui.settings.SettingsNode;

/**
 * Represents a stateless setting that, when clicked,
 * navigates to a new destination in the hierarchy.
 * Note that this setting must be placed undo a {@link NavigationSettingsPage} hierarchy to function properly.
 */
public class NavigationSetting extends IconSetting {

    public interface NavigationDisplayCallback {
        void updateText(NavigationDisplayer displayer);
    }

    public static abstract class NavigationDisplayer {
        private NavigationDisplayCallback mCallback;
        public final void setNavigationDisplayCallback(NavigationDisplayCallback callback) { mCallback = callback; }
        protected final NavigationDisplayCallback getNavigationDisplayCallback () { return mCallback; }
        public abstract String getDisplayText();
    }

    private @StringRes int mTitleResId;
    private @IdRes int mDestinationId;
    private @DrawableRes int mIconDrawable = R.drawable.ic_next;
    private NavigationDisplayer mDisplayer;
    private NavigationDisplayCallback mDisplayCallback;

    public NavigationSetting(@StringRes int titleResId, @IdRes int destinationId) {
        mTitleResId = titleResId;
        mDestinationId = destinationId;
        mDisplayCallback = displayer -> {
            if (textView != null) textView.setText(displayer.getDisplayText());
        };
    }

    public NavigationSetting (@StringRes int titleResId, @IdRes int destinationId, @DrawableRes int iconResId) {
        this(titleResId, destinationId);
        mIconDrawable = iconResId;
    }

    public NavigationSetting setDisplayer (NavigationDisplayer displayer) {
        mDisplayer = displayer;
        displayer.setNavigationDisplayCallback(mDisplayCallback);
        return this;
    };

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        View root = super.inflateLayout(inflater);

        // Set correct title and icon
        titleView.setText(mTitleResId);
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

        // Get display text, if any
        if (mDisplayer != null) textView.setText(mDisplayer.getDisplayText());
        else textView.setText("");

        return root;
    }
}
