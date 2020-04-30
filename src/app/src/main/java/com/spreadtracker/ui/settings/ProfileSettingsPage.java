package com.spreadtracker.ui.settings;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.spreadtracker.ui.settings.value.ValueSetting;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * A settings page for the fragments in {@link com.spreadtracker.ui.activity.ProfileActivity}
 */
public class ProfileSettingsPage extends SettingsPage {

    private ArrayList<ValueSetting> mValueSettings = new ArrayList<>();

    public ProfileSettingsPage(@NonNull ViewGroup parentView, SettingsNode... children) {
        super(parentView, children);

        // Let's descend the hierarchy from this node and gather all value settings
        // We will use these to ensure that each setting has a value, and only allow
        // the user to continue to the next page when all values are filled
        Stack<SettingsNode> nodes = new Stack<>();
        nodes.push(this);
        while (!nodes.isEmpty()) {
            SettingsNode node = nodes.pop();
            if (node instanceof ValueSetting) mValueSettings.add((ValueSetting) node);
            for (SettingsNode childNode : node.getChildren())
                nodes.push(childNode);
        }
    }

    /**
     * Returns true if all the descendant {@link ValueSetting} objects of this page
     * have a value, and false otherwise.
     */
    public final boolean hasAllValues () {
        for (ValueSetting setting : mValueSettings) {
            if (!setting.hasValue()) return false;
        }
        return true;
    }
}
