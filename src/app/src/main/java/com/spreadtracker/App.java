package com.spreadtracker;

import android.app.Application;
import android.content.Context;

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

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);
    }
}
