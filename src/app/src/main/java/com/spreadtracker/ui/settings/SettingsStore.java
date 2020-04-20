package com.spreadtracker.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class used to store settings values across the app.
 */
public class SettingsStore {
    private static final String PREFERENCES_KEY = "com.spreadtracker.preferences";

    private static SettingsStore _instance;
    private static SettingsStore getInstance (@NonNull Context mCtx){
        if (_instance != null) return _instance;
        _instance = new SettingsStore(mCtx);
        return _instance;
    }

    private boolean mInitialized;
    private Context mCtx;
    private SharedPreferences mPreferences;
    private HashMap<String, Object> mValueMap = new HashMap<>();

    private SettingsStore (@NonNull Context ctx) {
        mCtx = ctx;
        mPreferences = mCtx.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        Map<String, ?> preferenceMap = mPreferences.getAll();
        for (Map.Entry<String, ?> entry : preferenceMap.entrySet())
            mValueMap.put(entry.getKey(), entry.getValue());
    }

    /**
     * Writes the given key-value pair to the preferences map.
     * @param key The key to identify the written value..
     * @param o The object to write. This must be a {@link Boolean}, {@link Float}, {@link Integer}, {@link Long}, {@link String}, or {@link Set<String>},
     *          otherwise an exception will be thrown.
     */
    public void writeValue (String key, Object o) {
        SharedPreferences.Editor editor = mPreferences.edit();

        if (o instanceof Boolean)
            editor.putBoolean(key, (Boolean) o);
        else if (o instanceof Float)
            editor.putFloat(key, (Float) o);
        else if (o instanceof Integer)
            editor.putInt(key, (Integer) o);
        else if (o instanceof Long)
            editor.putLong(key, (Long) o);
        else if (o instanceof String)
            editor.putString(key, (String) o);
        else if (o instanceof Set)
            editor.putStringSet(key, (Set<String>) o);
        else throw new IllegalArgumentException("Tried to write an invalid backing type to the SettingsStore");

        mValueMap.put(key, o);

        editor.apply();
    }

    /**
     * Gets a generic key from the settings store, or a default value if none was found.
     * @param key The string identifying the key to be obtained.
     * @param defaultValue The default value to be returned if no key was found.
     * @param <T> The type of object to be returned. Will throw an exception if the given value is an unsupported type,
     *           or if the desired value is not of the given type.
     * @return The generic value from the given key.
     */
    public <T> T readValue(String key, T defaultValue) {
        if (!mValueMap.containsKey(key)) return defaultValue;
        return (T) mValueMap.get(key);
    }
}