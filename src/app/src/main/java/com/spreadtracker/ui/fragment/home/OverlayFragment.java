package com.spreadtracker.ui.fragment.home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.spreadtracker.R;
import com.spreadtracker.ui.activity.main.MainActivity;
import com.spreadtracker.ui.fragment.ViewModelFragment;

/**
 * Represents the percentage overlay screen that shows the user the percentage
 * chance that they have contracted the coronavirus.
 */
public class OverlayFragment extends ViewModelFragment<MainActivity, HomeFragmentViewModel> {

    private TextView mInfectedPercentage;
    private TextView mInfectedSubtitle;

    private int mHealthyColor, mUnhealthyColor;

    private ArgbEvaluator mColorInterpolator;

    /**
     * Creates and returns a new {@link OverlayFragment} instance.
     * @return A new {@link OverlayFragment} object.
     */
    public static OverlayFragment create() {
        return new OverlayFragment();
    }

    @Override
    protected void inOnCreateView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mHealthyColor = ContextCompat.getColor(getContext(), R.color.overlayHealthy);
        mUnhealthyColor = ContextCompat.getColor(getContext(), R.color.overlayUnhealthy);

        mInfectedPercentage = root.findViewById(R.id.fragment_overlay_textPercentage);
        mInfectedSubtitle = root.findViewById(R.id.fragment_overlay_textPercentageSubtitle);

        viewModel.getInfectedPercentage().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                OverlayFragment.this.mInfectedPercentage.setText(getString(R.string.fragment_overlay_percentage, (int) (aDouble * 100)));
                setOverlayColor(aDouble);
            }
        });
    }

    /**
     * Sets and interpolates the color of the overlay according to the given infected percentage.
     * @param d The infected percentage
     */
    private void setOverlayColor(Double d) {
        if (mColorInterpolator == null) mColorInterpolator = new ArgbEvaluator();
        root.setBackgroundColor((int)mColorInterpolator.evaluate((float)(double)d, mHealthyColor, mUnhealthyColor));
    }

    /*
     * For use with ViewModelFragment
     */
    @NonNull
    @Override
    protected Class<HomeFragmentViewModel> getViewModelClass() { return HomeFragmentViewModel.class; }

    @Override
    protected int getLayout() { return R.layout.fragment_overlay; }
}
