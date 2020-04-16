package com.spreadtracker.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

public abstract class ViewModelLessNavigationFragment<TActivity extends FragmentActivity>
    extends NavigationFragment<TActivity, ViewModel> {

    @Override
    protected final Class<ViewModel> getViewModelClass() {
        return null;
    }
}
