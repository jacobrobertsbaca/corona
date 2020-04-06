package com.spreadtracker.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

/**
 * Base class that provides a layer of abstraction over common elements in the app.
 * @param <TActivity> The type of the activity that this fragment runs on. More specifically, the type of the activity returned by {@link #getActivity()}
 */
public abstract class BaseFragment<TActivity extends FragmentActivity> extends Fragment {
    /**
     * A reference to the activity that this fragment is running on.
     */
    protected TActivity activity;

    /**
     * The root layout view of this fragment that is inflated in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    protected View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getLayout(), container, false);
        inOnCreateView(container, savedInstanceState);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (TActivity) requireActivity();
    }

    /**
     * Gets the layout resource that representing this fragment's layout. Override in a derived class to specify which layout should be used for this fragment.
     * @return The ID of this fragment's layout resource.
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * Override in a derived class to specify behaviour that should be invoked after this fragment's layout has been created and {@link #root} has been assigned to.
     * @param container If non-null, represents the view that this fragment's layout is a child of.
     * @param savedInstanceState If non-null, this fragment is being reconstructed from a previous state as given here.
     */
    protected abstract void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
