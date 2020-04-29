package com.spreadtracker.ui.fragment.tutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.spreadtracker.ui.util.ILayoutFactory;

/**
 * A page on the tutorial screen. Represents a default implementation,
 * but feel free to extend as is necessary to create new pages.
 *
 * Each page contains an integer ID representing the layout that it should inflate,
 * and a method to do so. Consider this to be a "mini-fragment".
 */
public class TutorialPage implements ILayoutFactory {
    private final @LayoutRes int mLayoutRes;
    private Context mContext;

    public TutorialPage (@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
    }

    public void setContext (Context context) {mContext = context;}
    public Context getContext() { return mContext; }

    @NonNull
    @Override
    public View inflateLayout(@NonNull LayoutInflater inflater) {
        if (mLayoutRes == 0) {
            View empty = new View(mContext);
            empty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return empty;
        }

        return inflater.inflate(mLayoutRes, null, false);
    }
}
