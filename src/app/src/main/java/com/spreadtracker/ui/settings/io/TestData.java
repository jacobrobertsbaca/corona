package com.spreadtracker.ui.settings.io;

import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;

import com.spreadtracker.contactstracing.Test;
import com.spreadtracker.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A proxy class that allows easy retrieval and modification of the list of tests stored in the user's preferences.
 */
public class TestData {
    public interface OnTestDataChangedListener {
        void OnTestDataChanged(TestData data);
    }

    private SettingsStore mStore;
    private ArrayList<Test> mTests; // Set of test data objects representing the test data stored in the user's preferences
    private Set<OnTestDataChangedListener> mChangeListeners = new HashSet<>(); // Subscribers that are notified of changes to this class

    public TestData (SettingsStore store) {
        mStore = store;
        readTests();
    }

    private void readTests() {
        ArrayList<Test> tests = new ArrayList<>();
        Set<String> testStrings = mStore.readStringSet(Test.PREF_TESTS_SET, new HashSet<String>());
        for (String s : testStrings) {
            // Try to deserialize into test instance
            Test test = Test.fromString(s);
            if (test == null) continue; // If deserialization failed, skip
            tests.add(test);
        }
        mTests = tests;
        sortTests();
        mTests = tests; // Redundant call, eh.
    }

    // Sort test list in reverse-chronological order
    private void sortTests () {
        Collections.sort(mTests, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                return -Long.compare(o1.getDate(), o2.getDate());
            }
        });
    }

    private void saveTests() {
        Set<String> testSet = new HashSet<>();
        for (Test test : mTests) {
            testSet.add(test.toString());
        }
        mStore.writeValue(Test.PREF_TESTS_SET, testSet);
        sortTests();
        notifyDataChanged();
    }

    private void notifyDataChanged () {
        for (OnTestDataChangedListener listener : mChangeListeners)
            listener.OnTestDataChanged(this);
    }

    /**
     * Gets the number of stored tests.
     */
    public int getCount () {
        return mTests.size();
    }

    /**
     * Saves the list of tests. Call after performing any modifications to individual test objects.
     */
    public void save () {
        saveTests();
    }

    /**
     * Adds a {@link Test} object to the list of stored tests on the user's phone and saves the change to the user's phone.
     */
    public void create (Test test) {
        Objects.requireNonNull(test);
        mTests.add(test);
        sortTests();
        saveTests();
    }

    /**
     * Returns the most recent test stored on the user's phone.
     */
    public Test getLatest () {
        return mTests.size() > 0 ? mTests.get(0) : null;
    }

    /**
     * Gets a test by its index. Indices are valid from 0...{@link TestData#getCount()}-1
     */
    public Test get (int index) {
        return mTests.get(index);
    }

    /**
     * Deletes a test by its index. Indices are valid from 0...{@link TestData#getCount()}-1
     */
    public void delete (int index) {
        mTests.remove(index);
        saveTests();
    }

    public void addChangeListener (OnTestDataChangedListener listener) {
        mChangeListeners.add(listener);
    }

    public boolean removeChangeListener (OnTestDataChangedListener listener) {
        return mChangeListeners.remove(listener);
    }
}
