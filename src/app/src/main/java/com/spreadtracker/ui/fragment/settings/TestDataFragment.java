package com.spreadtracker.ui.fragment.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;
import com.spreadtracker.ui.fragment.ViewModelFragment;
import com.spreadtracker.ui.fragment.profile.ProfileFragmentViewModel;

public class TestDataFragment extends NavigationFragment<MainActivity, ProfileFragmentViewModel> {
    @NonNull
    @Override
    protected NavigationBuilder buildNavigation(@NonNull Context context) {
        return new NavigationBuilder(this, context)
                .setTitle(R.string.settings_testdata_title)
                .setRightButtonDrawable(R.drawable.ic_plus)
                .setRightButtonVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    protected Class<ProfileFragmentViewModel> getViewModelClass() {
        return ProfileFragmentViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_test_data;
    }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }
}
