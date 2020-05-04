package com.spreadtracker.contactstracing;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper class designed to hold various elements of the contacts-tracing system.
 */
public class ContactTracer {

    /**
     * An interface representing a listener that will be notified when the currently selected
     * random user from the database in this class changes.
     */
    public interface OnPersonChangedListener { void onPersonChanged(Person newPerson); }

    private Set<OnPersonChangedListener> mPersonChangedListeners = new HashSet<>();

    private Person mRandomPerson;
    private Database mDatabase;
    private Calculator mCalculator;
    private Context mCtx;

    public Database getDatabase() { return mDatabase; }
    public Calculator getCalculator() { return mCalculator; }
    public Person getRandomPerson() {return getRandomPerson(false);}

    /**
     * Gets the current random person from the database.
     * @param assignNew If true, will get a new random person from the database and cache it
     *                  such that future calls to this method that pass this parameter as false
     *                  will return the new random person.
     */
    public Person getRandomPerson (boolean assignNew) {
        if (assignNew) setRandomPerson(mDatabase.getRandomPerson());
        return mRandomPerson;
    }

    private void setRandomPerson (Person person) {
        if (person != mRandomPerson) {
            mRandomPerson = person;
            for (OnPersonChangedListener listener : mPersonChangedListeners)
                if (listener != null) listener.onPersonChanged(person);
        }
    }

    public ContactTracer(Context context) {
        mCtx = context;
        createDatabase();
    }

    /**
     * Calculates the infected percentage of the current randomly selected user
     * from the database.
     */
    public double getRandomPersonPercentage () {
        final long now = 51L * 3600 * 1000 * 24 * 365;
        return mCalculator.getInfectedPercentage(mRandomPerson.getId(), now);
    }

    /**
     * Adds a {@link OnPersonChangedListener} to the list of listeners that are notified when the
     * random person gotten from the database changes.
     * @param listener A listener that listens for changes in the database
     * @param notifyOnSubscribe If true, will notify the given listener of the current random person before control is relinquished from this method.
     */
    public void addOnPersonChangedListener (OnPersonChangedListener listener, boolean notifyOnSubscribe) {
        if (mPersonChangedListeners.add(listener) && notifyOnSubscribe)
            listener.onPersonChanged(mRandomPerson);
    }

    public void addOnPersonChangedListener (OnPersonChangedListener listener) {addOnPersonChangedListener(listener, false);}
    public boolean removeOnPersonChangedListener (OnPersonChangedListener listener) {return mPersonChangedListeners.remove(listener); }

    private void createDatabase() {
        File databaseFile = new File(mCtx.getFilesDir(), "tracker.sqlite");
        mDatabase = new Database(databaseFile);
        mCalculator = new Calculator(mDatabase);
        setRandomPerson(mDatabase.getRandomPerson());
    }
}
