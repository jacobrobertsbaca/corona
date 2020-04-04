package com.spreadtracker.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * An abstraction over a fragment that references a view model of type {@link TViewModel}
 * @param <TActivity> The type of activity that this fragment runs on. More specifically, the type returned by {@link #getActivity()}
 * @param <TViewModel> The type of view model that this fragment references.
 */
public abstract class ViewModelFragment<TActivity extends FragmentActivity,
        TViewModel extends ViewModel> extends BaseFragment<TActivity> {

    protected TViewModel viewModel;

    /**
     * Override in a derived class to get the class object representing {@link TViewModel}
     * @return A class object representing the class of {@link TViewModel}
     */
    // Note to self: fuck java
    @NonNull
    protected abstract Class<TViewModel> getViewModelClass ();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(getViewModelClass());
    }
}
