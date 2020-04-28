package com.spreadtracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

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

    // Taken from:
    // https://stackoverflow.com/a/45364096/10149816
    public static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) return getActivity(((ContextWrapper)context).getBaseContext());
        return null;
    }
}
