package com.spreadtracker.ui.activity.main;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.spreadtracker.R;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.math.MathUtils;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ArgbEvaluator mColorInterpolator;
    private ImageView mInfoButton;
    private ImageView mProfileButton;

    /**
     * Interpolate the color of the info and profile buttons.
     * {@code progress} is a value between 0 and 1 representing how much to interpolate between {@code start} and {@code end}.
     * Performs an RGB linear interpolation.
     */
    public void interpolateButtonColor (int start, int end, float progress) {
        if (mInfoButton == null || mProfileButton == null) return;
        if (mColorInterpolator == null) mColorInterpolator = new ArgbEvaluator();
        float i = MathUtils.clamp(progress, 0 , 1);
        int interp = (int) mColorInterpolator.evaluate(progress, start, end);
        mInfoButton.setColorFilter(interp);
        mProfileButton.setColorFilter(interp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInfoButton = findViewById(R.id.activity_main_infoButton);
        mProfileButton = findViewById(R.id.activity_main_profileButton);
    }
}
