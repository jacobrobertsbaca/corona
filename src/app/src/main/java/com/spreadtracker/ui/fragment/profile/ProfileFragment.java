package com.spreadtracker.ui.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.NavigationBuilder;
import com.spreadtracker.ui.fragment.NavigationFragment;
import com.spreadtracker.ui.settings.NavigationSetting;
import com.spreadtracker.ui.settings.NavigationSettingsPage;

public class ProfileFragment extends NavigationFragment<MainActivity, ProfileFragmentViewModel> {
    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup settingsRoot = root.findViewById(R.id.fragment_profile_settingsHolder);
//        NavigationSettingsPage navSettings = new NavigationSettingsPage(this, settingsRoot,
//                new NavigationSetting());
    }

    @NonNull @Override protected Class<ProfileFragmentViewModel> getViewModelClass() { return ProfileFragmentViewModel.class; }
    @Override protected int getLayout() { return R.layout.fragment_profile; }

    @NonNull
    @Override
    protected NavigationBuilder buildNavigation(@NonNull Context context) {
        return new NavigationBuilder(this, context)
                .setTitle(R.string.fragment_profile_title);
    }
}
