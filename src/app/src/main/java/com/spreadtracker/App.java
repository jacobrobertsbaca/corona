package com.spreadtracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.spreadtracker.contactstracing.Calculator;
import com.spreadtracker.contactstracing.Database;
import com.spreadtracker.contactstracing.Person;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * The application class for the spread tracker app.
 */
public class App extends Application {
    /**
     * A globally accessible instance of the {@link App} class.
     */
    private static WeakReference<App> instance;
    public static App getInstance() {
        return instance.get();
    }

    public static Context getContext () {
        return instance.get();
    }

    private static Database database;
    private static Calculator calculator;
    private static Person randomPerson;
    public static Database getDatabase() { return database; }
    public static Calculator getCalculator() {
        return calculator;
    }
    public static Person getRandomPerson() {return randomPerson;}

    /**
     * Gets the infected percentage of the randomly selected user.
     */
    public static double getPercentage () {
        final long now = 51L * 3600 * 1000 * 24 * 365;
        double avgPercentage = App.getCalculator().getAvgPercentage(now);
//        for testing^
        return App.getCalculator().getInfectedPercentage(App.getRandomPerson().getId(), now);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);
        createDatabase();
    }

    private void createDatabase() {
        File databaseFile = new File(getFilesDir(), "tracker.sqlite");
        database = new Database(databaseFile);
        calculator = new Calculator(database);
        randomPerson = database.getRandomPerson();
    }

    // Taken from:
    // https://stackoverflow.com/a/45364096/10149816
    public static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) return getActivity(((ContextWrapper)context).getBaseContext());
        return null;
    }
}
