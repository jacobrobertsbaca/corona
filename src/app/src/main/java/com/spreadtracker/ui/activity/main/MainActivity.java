package com.spreadtracker.ui.activity.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.spreadtracker.R;

public class MainActivity extends AppCompatActivity {

    private NavController mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public NavController getNav() {
        if (mNavigation != null) return mNavigation;
        NavHostFragment navFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_navHostFragment);
        mNavigation = navFragment.getNavController();
        return mNavigation;
    }
}
