package com.spreadtracker.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spreadtracker.R;

/**
 * Represents a fragment that has an empty layout without any ui elements.
 * <p>Use as a placeholder when you require a fragment (i.e. for a ViewPager) but do not want the fragment to contain any functionality or ui.</p>
 */
public class EmptyFragment extends Fragment {
    /**
     * Creates and returns a new instance of {@link EmptyFragment}
     * @return A new {@link EmptyFragment} object
     */
    public static EmptyFragment create() {
        return new EmptyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }
}
