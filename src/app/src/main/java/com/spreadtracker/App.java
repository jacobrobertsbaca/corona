package com.spreadtracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.spreadtracker.contactstracing.Calculator;
import com.spreadtracker.contactstracing.ContactTracer;
import com.spreadtracker.contactstracing.Database;
import com.spreadtracker.contactstracing.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
    private ContactTracer mTracer;

    public ContactTracer getContactTracer () { return mTracer; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);

        // Write the preloaded database to the database path
        // This data contains the randomly generated names of people in the database
        File dataDatabaseFile = new File(getFilesDir(), ContactTracer.DATABASE_PATH);
        try {
            if (dataDatabaseFile.createNewFile()) {
                InputStream bakedDatabaseResource = getResources().openRawResource(R.raw.tracker);
                FileOutputStream output = new FileOutputStream(dataDatabaseFile);

                //transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length = bakedDatabaseResource.read(buffer);

                while (length > 0) {
                    output.write(buffer, 0, length);
                    length = bakedDatabaseResource.read(buffer);
                }

                output.flush();
                output.close();
                bakedDatabaseResource.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTracer = new ContactTracer(this); // Create new ContactTracer object to wrap model calculations
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
