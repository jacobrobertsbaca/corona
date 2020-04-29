package com.spreadtracker.ui.fragment.settings.tests;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.MainActivity;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;
import com.spreadtracker.ui.fragment.profile.ProfileFragmentViewModel;
import com.spreadtracker.ui.settings.SettingsPage;
import com.spreadtracker.ui.settings.TestDataListSetting;

public class TestDataFragment extends NavigationFragment<MainActivity, ProfileFragmentViewModel> {
    @NonNull
    @Override
    protected NavigationBuilder buildNavigation(@NonNull Context context) {
        return new NavigationBuilder(this, context)
                .setTitle(R.string.settings_testdata_title)
                .setRightButtonDrawable(R.drawable.ic_plus)
                .setRightButtonVisibility(View.VISIBLE)
                .setRightButtonCallback(v -> {
                    // When top-right button is clicked,
                    // navigate to page to create new setting
                    activity.getNav().navigate(R.id.action_testDataFragment_to_editTestDataFragment);
                });
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
        LinearLayout l = root.findViewById(R.id.fragment_test_data_root);
        SettingsPage settings = new SettingsPage(l,
                new TestDataListSetting((MainActivity) getActivity()))
                .build();
    }
}
