package com.spreadtracker.ui.fragment.profile;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.ViewModelFragment;

public class ProfileFragment extends ViewModelFragment<MainActivity, ProfileFragmentViewModel> {
    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @NonNull @Override protected Class<ProfileFragmentViewModel> getViewModelClass() { return ProfileFragmentViewModel.class; }
    @Override protected int getLayout() { return R.layout.fragment_profile; }
}
