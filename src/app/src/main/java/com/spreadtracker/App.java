package com.spreadtracker;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Build;

/**
 * The application class for the spread tracker app.
 */
public class App extends Application {
    /**
     * A globally accessible instance of the {@link App} class.
     */
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
