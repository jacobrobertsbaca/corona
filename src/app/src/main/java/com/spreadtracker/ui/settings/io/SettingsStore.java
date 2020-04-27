package com.spreadtracker.ui.settings.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.spreadtracker.App;
import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class used to store settings values across the app.
 */
public class SettingsStore {
    private static final String PREFERENCES_KEY = "com.spreadtracker.preferences";

    private static SettingsStore _instance;
    public static SettingsStore getInstance (@NonNull Context mCtx){
        if (_instance != null) return _instance;
        _instance = new SettingsStore(mCtx);
        return _instance;
    }

    public static SettingsStore getInstance() {
        return getInstance(App.getContext());
    }

    private Context mCtx; // A context to be used wherever necessary
    private SharedPreferences mPreferences; // Shared preferences instance to be used when reading/writing keys
    private SparseArray<Test> mTestDataMap; // Set of test data objects representing the test data stored in the user's preferences

    private SettingsStore (@NonNull Context ctx) {
        mCtx = ctx;
        mPreferences = mCtx.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        mTestDataMap = readTestMap();
    }

    private SparseArray<Test> readTestMap() {
        SparseArray<Test> testMap = new SparseArray<>();
        Set<String> testStrings = mPreferences.getStringSet(Test.PREF_TESTS_SET, new HashSet<String>());
        for (String s : testStrings) {
            // Try to deserialize into test instance
            Test test = Test.fromString(s);
            if (test == null) continue; // If deserialization failed, skip
            testMap.append(test.getTestId(), test);
        }
        return testMap;
    }

    private void saveTestMap () {
        List<Test> tests = ArrayUtils.getValues(mTestDataMap);
        Set<String> testSet = new HashSet<>();
        for (Test test : tests) {
            testSet.add(test.toString());
        }
        mPreferences.getStringSet(Test.PREF_TESTS_SET, testSet);
    }

    /**
     * Stores a test into the preferences.
     */
    public void createTest (Test test) {
        int index = ArrayUtils.getFirstAvailableIndex(mTestDataMap);
        mTestDataMap.append(index, test);
        saveTestMap();
    }

    /**
     * Returns a list of {@link Test} objects in reverse-chronological order
     */
    public ArrayList<Test> getTests () {
        List<Test> tests = ArrayUtils.getValues(mTestDataMap);
        Test[] testArray = new Test[tests.size()];
        Arrays.sort(testArray, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                return -Long.compare(o1.getDate(), o2.getDate());
            }
        });
        for (int i = 0; i < testArray.length; i++) {
            tests.set(i, testArray[i]);
        }
        return getTests();
    }

    /**
     * Returns the most recent test stored in memory, or null if there are no stored tests.
     */
    public Test getLatestTest () {
        List<Test> tests = ArrayUtils.getValues(mTestDataMap);

        if (tests.size() == 0) return null;
        if (tests.size() == 1) return tests.get(0);

        Test latestTest = tests.get(0);
        long maxTime = latestTest.getDate();
        for (int i = 1; i < tests.size(); i++) {
            Test test = tests.get(i);
            if (test.getDate() > maxTime) {
                maxTime = test.getDate();
                latestTest = test;
            }
        }

        return latestTest;
    }

    /**
     * Saves the list of tests to memory.
     */
    public void saveTests () {
        saveTestMap();
    }

    /**
     * Removes any test objects
     */
    public void removeTest (Test test) {

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

        editor.apply();
    }

    public boolean readBool (String key, boolean defaultValue) { return mPreferences.getBoolean(key, defaultValue); }
    public float readFloat (String key, float defaultValue) { return mPreferences.getFloat(key, defaultValue); }
    public int readInt (String key, int defaultValue) { return mPreferences.getInt(key, defaultValue); }
    public long readLong (String key, long defaultValue) { return mPreferences.getLong(key, defaultValue); }
    public String readString (String key, String defaultValue) { return mPreferences.getString(key, defaultValue); }
    public Set<String> readStringSet (String key, Set<String> defaultValue) { return mPreferences.getStringSet(key, defaultValue); }

    @SuppressWarnings("unchecked")
    public <T> T readValue (String key, T defaultValue) {
        Map<String, ?> pMap = mPreferences.getAll();
        if (pMap.containsKey(key)) return (T) pMap.get(key); // Will throw an exception if you ask for an invalid data type
        else return defaultValue;
    }
}