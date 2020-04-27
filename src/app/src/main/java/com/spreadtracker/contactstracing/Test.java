package com.spreadtracker.contactstracing;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Test {

    /*
     * Constants for the location of test data in the user's preferences
     */
    private static final String PREF_TESTS_ROOT = "testdata.";
    public static final String PREF_TESTS_SET = PREF_TESTS_ROOT +  "tests"; // Location of set of TestData objects in preferences

    private int testId; // An integer id that uniquely identifies this test from other tests when reading/writing this test to the user's phone
    private long personId;
    private String disease;
    private boolean positive;
    private long date;

    public Test(long personId, String disease, boolean positive, long date) {
        this.personId = personId;
        this.disease = disease;
        this.positive = positive;
        this.date = date;
    }

    public Test(int testId, long personId, String disease, boolean positive, long date) {
        this(personId, disease, positive, date);
        testId = testId;
    }

    // Needed for GSON serialization
    public Test() {}

    public int getTestId () {
        return testId;
    }

    public void setTestId (int id) {
        testId = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPerson(long personId) {
        this.personId = personId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setResult(boolean result) {
        this.positive = result;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    /**
     * Converts this test object to a string representing its contents.
     * @return A JSON-encoded string representing the contents of this object
     */
    @NonNull
    @Override
    public String toString() {
        return getSerializer().toJson(this);
    }

    /**
     * Returns a {@link Test} object represented by the given JSON string.
     */
    public static Test fromString (String value) {
        try {
            return getSerializer().fromJson(value, Test.class);
        } catch (RuntimeException e) {
            Log.e(Test.class.toString(), e.toString());
            return null;
        }
    }

    private static Gson getSerializer () {
        return new GsonBuilder()
                .serializeNulls()
                .create();
    }
}
